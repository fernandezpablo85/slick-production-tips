package org.example

import scala.slick.driver.PostgresDriver.simple._
import Database.threadLocalSession

object UserDao extends DatabaseAccess {

  case class User(id: Option[Int], name: String, last: String)

  object Users extends Table[User]("users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def lastName = column[String]("last_name")

    def * = (id.? ~ name ~ lastName) <> (User, User.unapply _)

    // Shim needed to handle Postgress - Slick quirks with autoincrement columns.
    def insertProjection = (name ~ lastName) <> (
      {(first, last) => User(None, first, last)},
      {(user: User) => Some(user.name, user.last)}
    )
  }

  def insert(name: String, lastName: String) = databasePool withSession {
    Users.insertProjection.insert(User(None, name, lastName))
  }

  def findByLastName(last: String) = databasePool withSession {
    val query = for (u <- Users if u.lastName is last) yield (u)
    query.list
  }
}
