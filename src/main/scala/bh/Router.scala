package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpupickle.UpickleSupport._
import org.slf4j.LoggerFactory
import upickle.default._

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietNutrition = path(LongNumber / LongNumber) { (patientId, encounterId) =>
    get {
      logger.info(s"*** getDietNutrition: { patientId: $patientId encounterId: $encounterId }")
      onSuccess(store.findDietNutritionByPatientEncounterId(patientId, encounterId)) { dietNutritions =>
        logger.info(s"*** getDietNutrition: $dietNutritions")
        val json = write[List[DietNutrition]](dietNutritions)
        complete(OK -> json)
      }
    }
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") { getDietNutrition }
}

object Router {
  def apply(store: Store): Router = new Router(store)
}