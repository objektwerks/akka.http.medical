package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory
import upickle.default._

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietNutrition = path(LongNumber / LongNumber) { (patientId, encounterId) =>
    get {
      onSuccess(store.findDietNutritionByPatientEncounterId(patientId, encounterId)) {
        case Some(dietNutrition) =>
          logger.info(s"*** getDietNutrition: $dietNutrition")
          complete(OK -> write[DietNutrition](dietNutrition))
        case None =>
          logger.error(s"*** getDietNutrition: Failed { patientId: $patientId encounterId: $encounterId }")
          complete(NotFound)
      }
    }
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") { getDietNutrition }
}

object Router {
  def apply(store: Store): Router = new Router(store)
}