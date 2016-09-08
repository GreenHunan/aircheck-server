package dao

import model.{Count, Record, RegistrationInfo}
import org.scalatest.{FlatSpecLike, ShouldMatchers}


/**
  * Created by Scott on 9/6/16.
  */
class DAOmock extends DAO with FlatSpecLike with ShouldMatchers {
  def insert(r:Record):Unit = {
    r.density shouldBe > (0)
    r.latitude shouldBe > (-180.0)
    r.longitude shouldBe > (-180.0)
    r.latitude shouldBe < (180.0)
    r.longitude shouldBe < (180.0)
    r.user_id shouldBe > (0)
    r.time.toString.length shouldNot equal (0)
  }

  def insertUser(registrationInfo: RegistrationInfo): Unit = {
    registrationInfo.validate shouldEqual true
  }
  def getPasswdHash(user:String):Option[String] = Some("123456")
  def getCount:Count = Count(0,0)
  def getUserIDbyName(username:String):Int = 1
  def getRecordByUserID(id: Int):List[model.Record] = Nil
  def getRecordByUserID(id:Int, limit:Int):List[model.Record] = Nil
}
