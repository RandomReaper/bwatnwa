object test {
	println("hello")                          //> hello
	
	val a = Array.fill(12)(10)                //> a  : Array[Int] = Array(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10)
	println(a.mkString(" "))                  //> 10 10 10 10 10 10 10 10 10 10 10 10
	def toto(b:Array[Int]) = {
		b(0) = 33
	}                                         //> toto: (b: Array[Int])Unit
	
	toto(a)
	println(a.mkString(" "))                  //> 33 10 10 10 10 10 10 10 10 10 10 10
}