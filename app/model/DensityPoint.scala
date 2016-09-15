package model

/**
  * Created by Scott on 9/15/16.
  */
import play.api.libs.json._
case class DensityPoint(lng:Double, lat:Double, count:Double){

}
object DensityPoint{

  implicit val densityPointWrite = new Writes[DensityPoint]{
    def writes(densityPoint: DensityPoint) = Json.obj(
      "lng" -> densityPoint.lng,
      "lat" -> densityPoint.lat,
      "count" -> densityPoint.count
    )
  }

}
