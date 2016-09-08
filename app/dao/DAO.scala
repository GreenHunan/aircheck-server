package dao

import model.{Count, RegistrationInfo}
import javax.inject._

import com.google.inject.ImplementedBy
/**
  * Created by hadoop on 16-7-24.
  */

@ImplementedBy(classOf[MySQL])
trait DAO {
  def insert(r:model.Record):Unit
  def insertUser(registrationInfo: RegistrationInfo): Unit
  def getPasswdHash(user:String):Option[String]
  def getCount:Count
  def getUserIDbyName(username:String):Int
  def getRecordByUserID(id: Int):List[model.Record]
  def getRecordByUserID(id:Int, limit:Int):List[model.Record]
}
