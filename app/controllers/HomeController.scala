package controllers

import play.api._
import play.api.mvc._
import javax.inject._
import dao.DAO
import services.SessionManager


@Singleton
class HomeController @Inject() (dao:DAO, credManager:SessionManager) extends Controller {
  def index = Action { implicit request =>
    request.session.get("login_key").map{ cred =>
      credManager.getUsername(cred) match {
        case Some(x) => Ok(views.html.index_logined.render(x))
        case None =>
          val count = dao.getCount
          Ok(views.html.index(count.record_num,count.user_num))
      }
    }.getOrElse{
      val count = dao.getCount
      Ok(views.html.index(count.record_num,count.user_num))
    }
  }
  def about = Action{
    Ok(views.html.about())
  }

  def contact = Action{
    Ok(views.html.contact())
  }
}
