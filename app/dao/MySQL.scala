package dao
import javax.inject._

import model.{Count, Record, RegistrationInfo}
import play.api.db._

import scala.collection.mutable.ListBuffer
import play.api.Logger
/**
  * Created by hadoop on 16-7-24.
  */
@Singleton
class MySQL @Inject() (db: Database) extends DAO{
  def insert(r:Record): Unit ={
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("INSERT INTO record(density, longitude, latitude, user_id, time) VALUES( ?, ?, ?, ?, ?);")
      stmt.setFloat(1, r.density)
      stmt.setDouble(2, r.longitude)
      stmt.setDouble(3, r.latitude)
      stmt.setInt(4, r.user_id)
      stmt.setTimestamp(5, r.time)
      Logger.info(stmt.toString)
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


      val result2 = stmt.executeQuery("SELECT COUNT(*) FROM user")
      result2.next()
      val user_count = result2.getInt(1)

      Count(user_num = user_count, record_num = record_count)
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

  def getUserIDbyName(username:String):Int= {
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("SELECT id FROM user WHERE username = ? ")
      stmt.setString(1, username)
      val result = stmt.executeQuery()
      result.next()
      result.getInt(1)
    } finally{
      conn.close()
    }
  }

  def getRecordByUserID(id: Int):List[model.Record] = {
    val conn = db.getConnection()
    try{
      //should be in a descending order
      val stmt = conn.prepareStatement("SELECT * FROM record WHERE user_id = ? ORDER BY time DESC;")
      stmt.setInt(1,id)
      val results = stmt.executeQuery()
      val buf = new ListBuffer[Record]
      while(results.next()){
        val density:Float = results.getFloat(1)
        val longitude:Double = results.getDouble(2)
        val latitude: Double = results.getDouble(3)
        val user_id: Int = results.getInt(4)
        val time: java.sql.Timestamp = results.getTimestamp(5)
        val r = Record(density, longitude, latitude, user_id, Record.date_format format time)
        buf += r
      }
      buf.toList
    } finally { conn.close()}
  }

  def getRecordByUserID(id:Int, limit:Int):List[model.Record] = {
    val conn = db.getConnection()
    try{
      val stmt = conn.prepareStatement("SELECT * FROM record WHERE user_id = ? LIMIT ?;")

      stmt.setInt(1,id)
      stmt.setInt(2, limit)

      val results = stmt.executeQuery()
      val buf = new ListBuffer[Record]
      while(results.next()){
        val density:Float = results.getFloat(4)
        val longitude:Double = results.getDouble(2)
        val latitude: Double = results.getDouble(3)
        val user_id: Int = results.getInt(5)
        val time: java.sql.Timestamp = results.getTimestamp(5)
        val r = Record(density, longitude, latitude, user_id, Record.date_format format time)
        buf += r
      }
      buf.toList
    } finally { conn.close()}
  }
}
