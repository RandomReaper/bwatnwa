package org.pignat.bwatnwa

class TransformManager(listener:TransformListener) {
  
  object States extends Enumeration {
    type States = Value
    val Idle, Starting, Running, Stopping = Value
  }
  
  import States._
  
  def t_started() : Unit =
  {
    this.synchronized
    {
      require(m_state == Starting)
      
      m_state = Running
    }
  }

  def t_stopping() : Unit =
  {
    this.synchronized
    {
      require(m_state == Starting || m_state == Running | m_state ==Stopping)
      
      m_state = Stopping
    }
  }
  
  def t_done() : Unit =
  {
    this.synchronized
    {
      require(m_state == Starting || m_state == Running || m_state == Stopping)
      
      m_state = Idle
      
      listener.TransformDone()
    }
  }
  
  var transform:Transform = null
  
  def start(t:Transform) {
    this.synchronized
    {
      m_state match
      {
        case Idle =>

        case Starting =>
          while (m_state != Running) {
            Thread.sleep(1)
          }
          transform.stop(this)
          
        case Running =>
          transform.stop(this)
          
        case Stopping =>
        case _ =>
      }
    }      
      
    while (m_state != Idle) {
      Thread.sleep(1)
    }

    this.synchronized
    {
      transform = t
      m_state = Starting
    }

    transform.start(this)
  }
  
  def stop() : Unit =
  {
    this.synchronized
    {
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
  
    this.synchronized
    {
      m_state = Idle
    }
  }
  
  private var m_state = Idle
}