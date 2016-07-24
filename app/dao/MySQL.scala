package dao
import javax.inject._

import model.{Count, Record, RegistrationInfo}
import play.api.db._
/**
  * Created by hadoop on 16-7-24.
  */
@Singleton
class MySQL @Inject() (db: Database) extends DAO{
  def insert(r:Record): Unit ={
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("INSERT INTO record('density', 'longitude', 'latitude', 'device_id', 'user_id', 'unix_time') VALUES(?, ?, ?, ?, ?, ?);")
      stmt.setFloat(1, r.density)
      stmt.setDouble(2, r.longitude)
      stmt.setDouble(3, r.latitude)
      stmt.setInt(4, r.device_id)
      stmt.setInt(5, r.user_id)
      stmt.setLong(6, r.unix_time)
      stmt.execute()
    } finally{
      conn.close()
    }
  }

  def getPasswdHash(user:String): Option[String] ={
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("SELECT passwd FROM user WHERE username = ? ;")
      stmt.setString(1, user)
      val results = stmt.executeQuery()
      results.next() match{
        case true => Some(results.getString(1))
        case false => None
      }
    }finally {
      conn.close()
    }
  }

  def getCount:Count = {
    val conn = db.getConnection()
    val stmt = conn.createStatement()

    //Get count
    try{
      val result1 = stmt.executeQuery("SELECT COUNT(*) FROM record;")
      result1.next()
      val record_count = result1.getInt(1)

      val result2 = stmt.executeQuery("SELECT COUNT(*) FROM device;")
      result2.next()
      val device_count = result2.getInt(1)


      val result3 = stmt.executeQuery("SELECT COUNT(*) FROM user")
      result3.next()
      val user_count = result3.getInt(1)

      Count(user_num = user_count, record_num = record_count, device_num = device_count)
    } finally{
      conn.close()
    }
  }

  def insertUser(u:RegistrationInfo):Unit = {
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("INSERT INTO user(`username`, `passwd`, `e-mail`) VALUES(?, ?, ?);")
      stmt.setString(1, u.username)
      stmt.setString(2, u.password)
      stmt.setString(3, u.e_mail)
      stmt.execute()
    } finally {
      conn.close()
    }
  }
}
