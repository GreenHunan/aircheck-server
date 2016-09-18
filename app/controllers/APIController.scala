package controllers

/**
  * Created by Scott on 9/5/16.
  */
import dao.DAO
import play.api.mvc._
import play.api.libs.json._
import model.Record
import javax.inject._

import play.filters.csrf.CSRF
import play.filters.csrf.CSRF.Token
import services.SessionManager

@Singleton
class APIController @Inject() (dao:DAO, credManager:SessionManager)extends Controller{

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

  def token = Action{
    implicit request =>
      val Token(name, value) = CSRF.getToken.get
      Ok(s"$name=$value")
  }

  def getUserID = Action{
    implicit request =>
      request.session.get("login_key").map{
        cred =>
          credManager.getUsername(cred) match {
            case Some(x) =>
              val result = "name: " + dao.getUserIDbyName(x).toString
              Ok(result)
            case None =>
              Ok("no match")
          }
      } getOrElse Ok("no mathc")
  }
}
