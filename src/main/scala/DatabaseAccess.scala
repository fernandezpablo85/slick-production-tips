package org.example

import scala.slick.driver.PostgresDriver.simple._
import com.mchange.v2.c3p0.ComboPooledDataSource

trait DatabaseAccess {
  val Url = "jdbc:postgresql://localhost:5432/demo"
  val Driver = "org.postgresql.Driver"

  val database = Database.forURL(Url, driver = Driver)

  val databasePool = {
    val ds = new ComboPooledDataSource
    ds.setDriverClass(Driver)
    ds.setJdbcUrl(Url)
    ds.setMinPoolSize(20)
    ds.setAcquireIncrement(5)
    ds.setMaxPoolSize(100)
    Database.forDataSource(ds)
  }
}