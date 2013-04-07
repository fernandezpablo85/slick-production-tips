package org.example

import util._

object Main extends App {

  benchmark() {
    UserDao.insert("pablo", "fernandez")
    UserDao.insert("noob", "saibot")
    UserDao.insert("uma", "thurman")

    val users = UserDao.findByLastName("fernandez")
  }
}
