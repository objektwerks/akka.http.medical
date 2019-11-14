package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpupickle.UpickleSupport._
import org.slf4j.LoggerFactory
import upickle.default._

import scala.util.{Failure, Success}

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietNutritionById = path(LongNumber / LongNumber) { (patientId, encounterId) =>
    logger.info(s"*** getDietNutritionById: { patientId: $patientId encounterId: $encounterId }")
    onComplete(store.listDietNutritionById(patientId, encounterId)) {
      case Success(dietNutritions) =>
        logger.info(s"*** getDietNutritionById: $dietNutritions")
        complete(OK -> write[List[DietNutrition]](dietNutritions))
      case Failure(error) =>
        logger.error(s"*** getDietNutritionById: ${error.getMessage}")
        complete(BadRequest)
    }
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") {
    getDietNutritionById
  }
}

object Router {
  def apply(store: Store): Router = new Router(store)
}