package org.pignat.bwatnwa

class TransformManager(listener:TransformListener) {
  
  object States extends Enumeration {
    type States = Value
    val Idle, Starting, Running, Stopping = Value
  }
  
  import States._
  
  val masterLock = new Object
  val stateLock = new Object
  var transform:Transform = null
  private var m_state = Idle
  
  def t_started() : Unit =
  {
    stateLock.synchronized
    {
      //println(s"t_started m_state=$m_state")
      require(m_state == Starting || m_state == Stopping)
      m_state = Running
    }
  }

  def t_stopping() : Unit =
  {
    stateLock.synchronized
    {
      //println(s"t_stopping m_state=$m_state")
      require(m_state == Starting || m_state == Running || m_state == Stopping)     
      m_state = Stopping
    }
  }
  
  def t_done() : Unit =
  {
    stateLock.synchronized
    {
      //println(s"t_done m_state=$m_state")
      require(m_state == Starting || m_state == Running || m_state == Stopping)    
      m_state = Idle
      listener.TransformDone()
    }
  }
  
  def start(t:Transform) {
    masterLock.synchronized
    {
      stateLock.synchronized
      {
        //println(s"start m_state=$m_state")
        m_state match
        {
          case Idle =>
          case Starting | Running => transform.stop(this)
          case Stopping =>
          case _ =>          
        }
      }
        
      while (m_state != Idle) {
        Thread.sleep(1)
      }
  
      stateLock.synchronized
      {
        transform = t
        m_state = Starting
      }
  
      transform.start(this)
    }
  }
  
  def stop() : Unit =
  {
    masterLock.synchronized
    {
      stateLock.synchronized
      {
        //println(s"stop m_state=$m_state")

        m_state match
        {
          case Idle => return
          case Starting | Running => transform.stop(this)
          case Stopping =>
          case _ =>
        }
      }

      while (m_state != Idle) {
        Thread.sleep(1)
      }
    }
  }
}