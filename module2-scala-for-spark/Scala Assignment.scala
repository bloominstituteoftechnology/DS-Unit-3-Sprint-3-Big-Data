// Databricks notebook source
println("hello world!")
println(10)

// COMMAND ----------

val x = 10

// COMMAND ----------

"hello world".take(5)
"hello world".drop(5)

// COMMAND ----------

val b: Double = 10

// COMMAND ----------

10 / 4

// COMMAND ----------

10.0 / 4

// COMMAND ----------

def sumofsquares(x: Int, y: Int): Int = x * x + y * y

// COMMAND ----------

sumofsquares(4, 5)

// COMMAND ----------

def square(x: Int) = x * x

// COMMAND ----------

sq(7)

// COMMAND ----------

val addone: Int => Int = _ + 1
val weirdsum: (Int, Int) => Int = 12 * _ + 31 * _

// COMMAND ----------

addone(5)

// COMMAND ----------

weirdsum(3,2)

// COMMAND ----------

1 to 10
val r = 1 to 10

// COMMAND ----------

r.foreach(println)

// COMMAND ----------

(10 to 1 by -1) foreach println

// COMMAND ----------

val x = 5

// COMMAND ----------

if (x==4) println("yes")

// COMMAND ----------

if (x==3) println("yes") else println("no")

// COMMAND ----------

if (x==5) println("yes") else println("no")

// COMMAND ----------

Array("a",1,"b",2,"c",3,5,6,7)

// COMMAND ----------

val array = Array("a",1,"b",2,"c",3,5,6,7)

// COMMAND ----------

array(0)
array(1)

// COMMAND ----------

array(0)

// COMMAND ----------

println(array(1))

// COMMAND ----------

val divideints = (x: Int, y: Int) => (x/y, x%y)

// COMMAND ----------

divideints(5,2)

// COMMAND ----------

//access values of a tuple with ._n

divideints(5,2)._1

// COMMAND ----------


