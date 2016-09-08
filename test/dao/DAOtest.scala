package dao

/**
  * Created by Scott on 9/6/16.
  */

import model.Record
import org.scalatest.{FlatSpec, ShouldMatchers}
import org.scalatestplus.play._
import play.api.db.{Database, Databases}



class DAOtest extends FlatSpec with ShouldMatchers{
  val db:Database = Databases("com.mysql.jdbc.Driver","jdbc:mysql://localhost/aircheck",
    config = Map(
      "user" -> "root",
      "password" ->"123456")
  )
  val mysql:DAO = new MySQL(db)
  "MySQL" should "execute insert query" in{
    mysql.insert(Record(25.0F,179.9999999,123.444444,1,"2016-09-06 20:46:00"))
  }
}
