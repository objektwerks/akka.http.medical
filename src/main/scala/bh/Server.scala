package bh

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

object Server {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val conf = ConfigFactory.load("server.conf")
    implicit val system = ActorSystem.create(conf.getString("server.name"), conf.getConfig("akka"))
    implicit val materializer = ActorMaterializer()
    implicit val dispatcher = system.dispatcher

    val store = Store(conf)
    val router = Router(store)
    val host = Try(args(0)).getOrElse(conf.getString("server.host"))
    val port = Try(args(1).toInt).getOrElse(conf.getInt("server.port"))
    Http()
      .bindAndHandle(router.api, host, port)
      .map { server =>
        logger.info(s"*** Server host: ${server.localAddress.toString}")
      }

    sys.addShutdownHook {
      logger.info("*** Server shutting down...")
      system.terminate()
      Await.result(system.whenTerminated, 30.seconds)
      logger.info("*** Server shutdown.")
    }
    ()
  }
}