package bh

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._

class Router(store: Store) {
  val getDietNutrition = get {
    store.ping
    complete(OK)
  }
  val api = pathPrefix("api" / "v1" / "dietnutrition") {
    getDietNutrition
  }
  val routes = api
}

object Router {
  def apply(store: Store): Router = new Router(store)
}