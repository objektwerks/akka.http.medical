package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpupickle.UpickleSupport._
import org.slf4j.LoggerFactory
import upickle.default._

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietNutritionById = path(LongNumber / LongNumber) { (patientId, encounterId) =>
    get {
      logger.info(s"*** getDietNutrition: { patientId: $patientId encounterId: $encounterId }")
      onSuccess(store.listDietNutritionById(patientId, encounterId)) { dietNutritions =>
        logger.info(s"*** getDietNutrition: $dietNutritions")
        complete(OK -> write[List[DietNutrition]](dietNutritions))
      }
    }
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") { getDietNutritionById }
}

object Router {
  def apply(store: Store): Router = new Router(store)
}