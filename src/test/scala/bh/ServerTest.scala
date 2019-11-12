package bh

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.testkit.TestDuration
import com.typesafe.config.ConfigFactory
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
    .bindAndHandle(router.routes, host, port)
    .map { server =>
      logger.info(s"*** Server host: ${server.localAddress.toString}")
    }

  "DietNutritionService" should {
    "get" in {
      Get("/api/v1/dietnutrition") ~> router.routes ~> check {
        status shouldBe StatusCodes.OK
      }
    }
  }
}