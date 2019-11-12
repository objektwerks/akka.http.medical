package bh

import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import scalikejdbc.ConnectionPool

import scala.concurrent.Future

class Store(conf: Config) {
  private val logger = LoggerFactory.getLogger(getClass)
  private val driver = conf.getString("db.driver")
  private val url = conf.getString("db.url")
  private val user = conf.getString("db.user")
  private val password = conf.getString("db.password")

  Class.forName(driver)
  ConnectionPool.singleton(url, user, password)
  logger.info(s"*** Store: Connected to Oracle store ( $url ).")

  def select(patiendId: Int, encounterId: Int): Future[DietNutrition] = Future.successful(DietNutrition(patiendId, encounterId, "status", "diet"))
}

object Store {
  def apply(conf: Config): Store = new Store(conf)
}