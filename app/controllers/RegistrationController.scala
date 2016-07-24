package controllers

/**
  * Created by hadoop on 16-7-24.
  */
import play.api.mvc._
import javax.inject._

import play.api._
import play.api.data._
import play.api.data.Forms._
import dao.DAO
import model.RegistrationInfo
@Singleton
class RegistrationController @Inject() (implicit dao:DAO) extends Controller{
  val signupForm = Form(mapping (
    "username" -> nonEmptyText,
    "password" -> nonEmptyText,
    "e_mail" -> nonEmptyText
  )(RegistrationInfo.apply)(RegistrationInfo.unapply))
  def mainPage = Action{ implicit request =>
    Ok(views.html.registrate())
  }

  def postPage = Action{ implicit request =>
    val info = signupForm.bindFromRequest.get
    if(info.validate && !info.checkIfexist()){
      info.storeToDB()
      Redirect("/").withNewSession
    }else{
      Unauthorized("Oops, Your registration has failed")
    }
  }
}
