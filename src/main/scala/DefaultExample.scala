package org.example

import scala.slick.driver.PostgresDriver.simple._
import Database.threadLocalSession

object DefaultExample {

  val database = Database.forURL("jdbc:postgresql://localhost:5432/demo", driver = "org.postgresql.Driver")

  case class User(id: Option[Int], name: String, pass: String)
  object User {
    def dbCreate(name: String, pass: String) = User(None, name, pass)
    def dbRetrieve(user: User) = Some((user.name, user.pass))
  }

  object Users extends Table[User]("users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def pass = column[String]("pass")

    def * = name ~ pass <> (User.dbCreate _, User.dbRetrieve _)
  }

  def insert(name: String, pass: String) = {
    database withSession {
      Users.insert(User(None, name, pass))
    }
  }
}