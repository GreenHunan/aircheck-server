package controllers
import model.UserLogin
import play.api.mvc._
import play.api.data.Forms._
import play.api.data._
import javax.inject._

import dao.DAO
import services.SessionManager

import scala.util.Try
/**
  * Created by hadoop on 16-7-24.
  */
@Singleton
class AuthController @Inject() (db:DAO, credManager: SessionManager) extends Controller{

  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserLogin.apply)(UserLogin.unapply)
  )

  def login = Action{ implicit request =>

    Try{



      val login_cred = loginForm.bindFromRequest.get

      db.getPasswdHash(login_cred.username) match {
        case Some(passwd) if passwd == login_cred.password =>
          Redirect("/").withSession("login_key" -> credManager.genAndAdd(login_cred.username))
        case _ => Unauthorized("Oops, seems I cannot recongize you.")
      }


    } getOrElse Redirect("/") //if no password or illegal request, come back to home page
  }

  def logout = Action{ implicit request =>


    request.session.get("login_key") map { cred =>


      credManager remove cred
      Redirect("/").withNewSession


    } getOrElse Redirect("/")
  }
}
