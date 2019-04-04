// Databricks notebook source
println("hello")

// COMMAND ----------

val NUM_SAMPLES = math.pow(10, 7).toInt

// COMMAND ----------

sc

// COMMAND ----------

val count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1 // what gets returned
}.count()
println(s"Pi is roughly ${4.0 * count / NUM_SAMPLES}")
