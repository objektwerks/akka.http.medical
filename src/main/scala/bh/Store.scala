package bh

import akka.event.LoggingAdapter
import com.typesafe.config.Config
import scalikejdbc.ConnectionPool

class Store(conf: Config, logger: LoggingAdapter) {
  private val driver = conf.getString("db.driver")
  private val url = conf.getString("db.url")
  private val user = conf.getString("db.user")
  private val password = conf.getString("db.password")

  Class.forName(driver)
  ConnectionPool.singleton(url, user, password)
  logger.info("*** Oracle: Loaded driver and created connection.")

  def ping:Int = 1
}

object Store {
  def apply(conf: Config, logger: LoggingAdapter): Store = new Store(conf, logger)
}