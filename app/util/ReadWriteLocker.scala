package util

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
  * Created by Scott on 9/12/16.
  */
class ReadWriteLocker {
  private val rwlock = new ReentrantReadWriteLock()

  def readSync[T,R](func:T => R): T => R ={
    def new_func(para:T):R = {
      rwlock.readLock().lock()
      val result = func(para)
      rwlock.readLock().unlock()
      result
    }
    new_func
  }


  def writeSync[T,R](func:T => R): T => R ={
    def new_func(para:T):R = {
      rwlock.writeLock().lock()
      val result = func(para)
      rwlock.writeLock().unlock()
      result
    }
    new_func
  }
}
