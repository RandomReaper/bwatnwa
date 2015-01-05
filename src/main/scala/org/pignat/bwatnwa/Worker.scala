package org.pignat.bwatnwa

class Worker(master:GraphicalViewAsync) {
  
  protected var source:Array[Byte] = null
  protected var destination:Array[Byte] = null
    
  abstract class Sub() {
    protected var done = true
    protected var thread:Thread = null
    def terminate() : Unit
    def start(source:Array[Byte], destination:Array[Byte]) : Unit
    def stop() : Unit = {
      if (done) return
      if (thread != null)
      {
        terminate()
        thread.join()
      }
    }
  }
  
  var sub = new Sub() {
    var w = false
    done = false
    def start(source:Array[Byte], destination:Array[Byte]) : Unit = {
      w = true
      println("Sub.start")
      thread = new Thread(new Runnable() {
        def run() : Unit = {
          master.worker_started()
          val finish_time = System.currentTimeMillis() + 2*1000
          while (w)
          {
            for (i <- 0 until destination.length)
            {
              destination(i) = System.currentTimeMillis().toByte
              if (!w ||  System.currentTimeMillis() - finish_time > 0)
              {
                done = true
                master.worker_done()
                return
              }
            }
          }
        }
      })
      thread.start
    }
    
    def terminate() = {
      w = false
    }
  }
  
  def stop() : Unit = {
    sub.stop()
    println("Worker.stop")
  }
  
  def start(source:Array[Byte], destination:Array[Byte]) : Unit = {
    println("Worker.start")
    sub.start(source, destination)
  }
    
  def setSource(b:Array[Byte]) : Unit = {
    stop()
    
    source = b
    
    if (source != null && destination != null) {
      start(source, destination)
    }     
  }
    
  def setDestination(b:Array[Byte]) : Unit = {
    stop()
    
    destination = b
    
    if (source != null && destination != null) {
      start(source, destination)
    }     
  }
}