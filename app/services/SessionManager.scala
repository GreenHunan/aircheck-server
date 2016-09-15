package services

import java.util.concurrent.locks.ReentrantReadWriteLock

import com.google.inject.Singleton

import scala.collection.mutable

/**
  * Created by hadoop on 16-7-24.
  */
import scala.util.Random
import util.ReadWriteLocker
@Singleton
class SessionManager {
  private val rand = Random
  private val map = mutable.WeakHashMap[String, String]()
  private val locker = new ReadWriteLocker

  def getUsername(cred:String) = locker.readSync(map.get)(cred)
  //add synchronized for concurrency

  def add(cred:String, name:String):Unit = locker.writeSync{
      locker.writeSync(map.+=)(cred -> name)
  }

  def remove(cred:String): Unit ={
    getUsername(cred) match{
      case Some(x) => locker.writeSync(map.-=)(cred)
      case _ =>
    }
  }

  def genAndAdd(name:String):String = {
    val cred = rand.nextDouble().toString
    add(cred, name)
    //return the random credential
    cred
  }
}

