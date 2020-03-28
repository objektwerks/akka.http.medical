package medical

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory

import scala.io.StdIn
import scala.util.{Failure, Success, Try}

object Server {
  def main(args: Array[String]): Unit = {
    val conf = ConfigFactory.load("server.conf")
    implicit val system = ActorSystem.create(conf.getString("server.name"), conf)
    implicit val dispatcher = system.dispatcher
    val logger = system.log

    val store = Store(conf)
    val router = Router(store)
    val host = Try(args(0)).getOrElse(conf.getString("server.host"))
    val port = Try(args(1).toInt).getOrElse(conf.getInt("server.port"))
    val passphrase = conf.getString("server.passphrase")
    val sslContext = SSLContextFactory.newInstance(passphrase)
    val httpsContext = ConnectionContext.https(sslContext)
    val http = Http()
    http.setDefaultClientHttpsContext(httpsContext)
    val server = http
      .bindAndHandle(
        router.api,
        host,
        port,
        connectionContext = httpsContext
      )
    logger.info(s"*** Server started at https://$host:$port/\nPress RETURN to stop...")

    val client = Http()
    client.setDefaultClientHttpsContext(httpsContext)
    val service = conf.getString("server.service")
    val future = client.singleRequest(HttpRequest(uri = s"https://$host:$port$service"))
    future
      .onComplete {
        case Success(diet) => logger.info(s"*** Diet is: $diet")
        case Failure(error) => logger.error(s"*** Diet service failed: ${error.toString}")
      }

    StdIn.readLine()
    server
      .flatMap(_.unbind)
      .onComplete { _ =>
        system.terminate
        logger.info("Server stopped.")
      }
  }
}