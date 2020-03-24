package medical

import java.security.{KeyStore, SecureRandom}

import akka.actor.ActorSystem
import akka.http.scaladsl.{ConnectionContext, Http}
import com.typesafe.config.ConfigFactory
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

object Server {
  def main(args: Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val conf = ConfigFactory.load("server.conf")
    implicit val system = ActorSystem.create(conf.getString("server.name"), conf.getConfig("akka"))
    implicit val dispatcher = system.dispatcher

    val keystore = KeyStore.getInstance("PKCS12")
    val serverKey = getClass.getClassLoader.getResourceAsStream("/server.key")
    val password = conf.getString("passphrase").toCharArray
    keystore.load(serverKey, password)

    val keyManagerFactory = KeyManagerFactory.getInstance("SunX509")
    keyManagerFactory.init(keystore, password)

    val trustManagerFactory = TrustManagerFactory.getInstance("SunX509")
    trustManagerFactory.init(keystore)

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagerFactory.getKeyManagers, trustManagerFactory.getTrustManagers, new SecureRandom)

    val store = Store(conf)
    val router = Router(store)
    val host = Try(args(0)).getOrElse(conf.getString("server.host"))
    val port = Try(args(1).toInt).getOrElse(conf.getInt("server.port"))
    Http()
      .bindAndHandle(router.api, host, port, connectionContext = ConnectionContext.https(sslContext))
      .map { server =>
        logger.info(s"*** Server: ${server.localAddress.toString}")
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