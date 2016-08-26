package controllers

/**
  * Created by hadoop on 16-8-14.
  */
import play.api.mvc._
import javax.inject._

import dao.DAO
import services.SessionManager

@Singleton
class VisualizationController @Inject() (credManager:SessionManager, dao:DAO) extends Controller{
  def visualize = Action { implicit request =>
    request.session.get("login_key").map { cred =>
      credManager.getUsername(cred) match {
        case Some(x) => Ok(views.html.visualize(x, dao.getRecordByUserID(dao.getUserIDbyName(x), 100)))
        case None => Redirect("/")
      }
    } getOrElse {
      Redirect("/")
    }
  }
}
