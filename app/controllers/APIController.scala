package controllers

/**
  * Created by Scott on 9/5/16.
  */
import dao.DAO
import play.api.mvc._
import play.api.libs.json._
import model.Record
import javax.inject._

@Singleton
class APIController @Inject() (dao:DAO)extends Controller{

  import model.Record.jsonReads


  def insert = Action(BodyParsers.parse.json) { implicit request =>


    request.body.validate[Record] match{



      case s: JsSuccess[Record] =>

        val record = s.get
        dao insert record
        Ok("success")




      case e: JsError =>
        Ok("failure")
    }
  }
}
