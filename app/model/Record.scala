package model

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._
import play.api.libs.functional.syntax._
/**
  * Created by hadoop on 16-7-24.
  */
case class Record(density:Float,
                  longitude:Double, latitude: Double,
                  user_id: Int,
                  timeString: String){

  lazy val time = Record.format(timeString)
  def toDensityPoint = DensityPoint(lat = latitude, lng = longitude, count = density)
}

object Record{
  val date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  def format(time_string:String) = java.sql.Timestamp.valueOf(time_string)


  implicit val jsonWrites:Writes[Record] = (
    (JsPath \ "density").write[Float] and
      (JsPath \ "lng").write[Double] and
      (JsPath \ "lat").write[Double] and
      (JsPath \ "user_id").write[Int] and
      (JsPath \ "time").write[String]
    )(unlift(Record.unapply))


  implicit val jsonReads:Reads[Record] = (
    (JsPath \ "density").read[Float] and
      (JsPath \ "lng").read[Double] and
      (JsPath \ "lat").read[Double] and
      (JsPath \ "user_id").read[Int] and
      (JsPath \ "time").read[String]
    )(Record.apply _)
}

