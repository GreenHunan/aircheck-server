package services

import com.google.inject.Singleton

/**
  * Created by hadoop on 16-7-24.
  */
import scala.util.Random

@Singleton
class SessionManager {
  private val rand = scala.util.Random
  private var map = Map[String, String]()

  def getUsername(cred:String) = map.get(cred)

  def add(cred:String, name:String):Unit = {
    map = map + (cred -> name)
  }

  def remove(cred:String): Unit ={
    getUsername(cred) match{
      case Some(x) => map = map - cred
      case _ =>
    }
  }

  def genAndAdd(name:String):String = {
    val cred = rand.nextDouble().toString
    add(cred, name)
    cred
  }
}

