package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import org.slf4j.LoggerFactory
import upickle.default._

class Router(store: Store) {
  val logger = LoggerFactory.getLogger(getClass)

  val getDietNutrition = path(IntNumber / IntNumber) { (patientId, encounterId) =>
    get {
      onSuccess(store.select(patientId, encounterId)) { dietNutrition =>
        logger.info(s"*** Router: Selected DietNutrition ( $dietNutrition ).")
        complete(OK -> write[DietNutrition](dietNutrition))
      }
    }
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") {
    getDietNutrition
  }
  val routes = api
}

object Router {
  def apply(store: Store): Router = new Router(store)
}