package model

import dao.DAO

/**
  * Created by hadoop on 16-7-24.
  */
case class RegistrationInfo(username:String, password:String, e_mail:String){
  def storeToDB()(implicit db:DAO):Unit = {
    db.insertUser(this)
  }
  def checkIfexist()(implicit db:DAO):Boolean = {
    db.getPasswdHash(username) match {
      case Some(x) => true
      case None => false
    }
  }
  def validate = !username.contains("'") && !e_mail.contains("'") &&
    !(username.length < 5) && !(password.length < 5) &&
    e_mail.contains("@")
}
