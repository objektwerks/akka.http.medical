package medical

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.{RouteTestTimeout, ScalatestRouteTest}
import akka.testkit.TestDuration

import com.typesafe.config.ConfigFactory

import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.Await

class RouterTest extends AnyWordSpec with BeforeAndAfterAll with Matchers with ScalatestRouteTest  {
  val logger = LoggerFactory.getLogger(getClass)
  val conf = ConfigFactory.load("router.conf")
  val actorRefFactory = ActorSystem.create(conf.getString("server.name"), conf.getConfig("akka"))
  implicit val dispatcher = system.dispatcher
  implicit val timeout = RouteTestTimeout(10.seconds dilated)

  val store = Store(conf)
  val router = Router(store)
  val host = conf.getString("server.host")
  val port = conf.getInt("server.port")
  val server = Http()
    .newServerAt(host, port)
    .bindFlow(router.api)
    .map { server =>
      logger.info(s"*** Server started at: ${server.localAddress.toString}")
      server
    }

  override protected def afterAll(): Unit =
    server
      .flatMap(_.unbind())
      .onComplete { _ =>
        logger.info("*** Server shutting down...")
        actorRefFactory.terminate()
        Await.result(actorRefFactory.whenTerminated, 3.seconds)
        logger.info("*** Server shutdown.")
      }  

  import de.heikoseeberger.akkahttpupickle.{UpickleSupport => Upickle}
  import Upickle._
  import upickle.default._

  "getDietById" should {
    "return diet json" in {
      Get(conf.getString("rest.url")) ~> router.api ~> check {
        status shouldBe StatusCodes.OK
        val json = responseAs[String]
        logger.info(s"*** ServerTest: getDietById > $json")
        val diets = read[List[Diet]](json)
        diets.nonEmpty shouldBe true
        diets foreach { diet => assert(diet.isValid) }
      }
    }
  }
}