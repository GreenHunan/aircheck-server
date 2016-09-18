package model

/**
  * Created by Scott on 9/18/16.
  */
import play.api.libs.json._
import play.api.libs.functional.syntax._


case class StatusResponse(status: String, detail:String)

object StatusResponseObj{

  implicit val jsonWrites:Writes[StatusResponse] = (
    (JsPath \ "status").write[String] and
      (JsPath \ "detail").write[String]
    )(unlift(StatusResponse.unapply))
}