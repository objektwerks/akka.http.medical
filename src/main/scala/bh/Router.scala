package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpupickle.UpickleSupport._
import org.slf4j.LoggerFactory
import upickle.default._

import scala.util.{Failure, Success}

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietById = path(LongNumber / LongNumber) { (patientId, encounterId) =>
    logger.info(s"*** getDietById: { patientId: $patientId encounterId: $encounterId }")
    onComplete(store.listDietById(patientId, encounterId)) {
      case Success(diet) =>
        logger.info(s"*** getDietById: $diet")
        complete(OK -> write[List[Diet]](diet))
      case Failure(error) =>
        logger.error(s"*** getDietById: ${error.getMessage}")
        complete(BadRequest)
    }
  }
  val api = pathPrefix("api" / "v1" / "diet") {
    getDietById
  }
}

object Router {
  def apply(store: Store): Router = new Router(store)
}