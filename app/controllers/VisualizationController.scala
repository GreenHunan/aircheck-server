package controllers

/**
  * Created by hadoop on 16-8-14.
  */
import play.api.mvc._
import javax.inject._

import dao.DAO
import services.SessionManager
import play.api.libs.json._
@Singleton
class VisualizationController @Inject() (credManager:SessionManager, dao:DAO) extends Controller{
  def visualize = Action { implicit request =>
    request.session.get("login_key").map { cred =>
      credManager.getUsername(cred) match {
        case Some(x) =>
          val desPoints = dao.getRecordByUserID(dao.getUserIDbyName(x), 100).map(_.toDensityPoint)
          val count = desPoints.length.toDouble


          val avgLng = desPoints.map(_.lng).sum / count
          val avgLat = desPoints.map(_.lat).sum / count
          val js = Json.toJson(desPoints).toString()


          Ok(  views.html.visualize(x, js, avgLng, avgLat, 15)   )
        case None => Redirect("/")
      }
    } getOrElse {
      Redirect("/")
    }
  }
}
