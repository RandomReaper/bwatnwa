object test {
	(0 to 10).map(_.toByte).map(x=> f"$x%02x")//> res0: scala.collection.immutable.IndexedSeq[String] = Vector(00, 01, 02, 03, 
                                                  //| 04, 05, 06, 07, 08, 09, 0a)

}