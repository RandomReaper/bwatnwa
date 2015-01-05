package org.pignat.bwatnwa

abstract class Transform extends Runnable
{
  protected var thread:Thread = null;
  protected var stopFlag = false;

  def stop() : Unit =
  {
    this.synchronized
    {
      stopFlag = true
    }
  }
  
  protected def should_stop() : Boolean =
  {
    this.synchronized
    {
      return stopFlag
    }
  }  
  
  def start(manager:TransformManager) : Unit =
  {
    thread = new Thread(new Runnable
    {
      def run() : Unit = {
        manager.t_started()
        Transform.this.run()
        manager.t_done()
      }
    })
    thread.start()
  }

  def stop(manager:TransformManager) : Unit = {
    manager.t_stopping()
    stop()
  }
}
