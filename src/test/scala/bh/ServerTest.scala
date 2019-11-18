package bh

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.testkit.TestDuration
import com.typesafe.config.ConfigFactory
import de.heikoseeberger.akkahttpupickle.{UpickleSupport => Upickle}
import org.scalatest.{Matchers, WordSpec}
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.language.postfixOps

class ServerTest extends WordSpec with Matchers with ScalatestRouteTest  {
  val logger = LoggerFactory.getLogger(getClass)
  val conf = ConfigFactory.load("test.server.conf")
  val actorRefFactory = ActorSystem.create(conf.getString("server.name"), conf.getConfig("akka"))
  implicit val dispatcher = system.dispatcher
  implicit val timeout = RouteTestTimeout(10.seconds dilated)

  val store = Store(conf)
  val router = Router(store)
  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  Http()
    .bindAndHandle(router.api, host, port)
    .map { server =>
      logger.info(s"*** ServerTest host: ${server.localAddress.toString}")
    }

  import Upickle._
  import upickle.default._

  "getDietById" should {
    "listDietById" in {
      Get(conf.getString("rest.url")) ~> router.api ~> check {
        status shouldBe StatusCodes.OK
        val json = responseAs[String]
        logger.info(s"*** ServerTest json: $json")
        val dietNutritions = read[List[Diet]](json)
        dietNutritions foreach { dn => assert(dn.isValid) }
      }
    }
  }
}