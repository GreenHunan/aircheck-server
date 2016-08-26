package model

import java.text.SimpleDateFormat
import play.api.libs.json._
/**
  * Created by hadoop on 16-7-24.
  */
case class Record(density:Float,
                  longitude:Double, latitude: Double,
                  user_id: Int,
                  time: java.sql.Timestamp){


  def timeString = Record.date_format.format(time)
  def toJSON = Json.obj(
    "density" -> density,
    "longitude" -> longitude,
    "latitude" -> latitude,
    "user_id" -> user_id,
    "time" -> timeString
  )
}

object Record{
  private val date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def format(time_string:String) = java.sql.Timestamp.valueOf(time_string)
}

