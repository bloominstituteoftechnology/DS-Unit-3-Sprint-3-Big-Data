val N = 15
val M = 3

val r = scala.util.Random

val Matrix = scala.collection.immutable.Map[Int,scala.collection.immutable.IndexedSeq[Float]]

val toydat = Map(0 -> (for (i <- 0 to N) yield i * (N-i) * r.nextFloat() * r.nextInt()), 1 -> (for (i <- 0 to N) yield i * (N-i) * r.nextFloat() * r.nextInt()), 2 -> (for (i <- 0 to N) yield i * (N-i) * r.nextFloat() * r.nextInt()))

def showmat(X: Matrix): String = X match {
  case Map() => ""
  case _ => "hello"
}
