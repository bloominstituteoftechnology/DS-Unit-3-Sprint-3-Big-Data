// Databricks notebook source
// MAGIC %md
// MAGIC Calculate Pi

// COMMAND ----------

val NUM_SAMPLES = 10000000
val count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count()
println(s"Pi is roughly ${4.0 * count / NUM_SAMPLES}")

// COMMAND ----------

val myRange = spark.range(10000).toDF("number")

// COMMAND ----------

val divisBy2 = myRange.where("number % 2 = 0")

// COMMAND ----------

divisBy2.count()

// COMMAND ----------

// MAGIC %md
// MAGIC Flight data

// COMMAND ----------

// load into flightData2015
val flightData2015 = spark
  .read
  .option("inferSchema", "true")
  .option("header", "true")
  .csv("/databricks-datasets/definitive-guide/data/flight-data/csv/2015-summary.csv")

// COMMAND ----------

flightData2015.take(3)

// COMMAND ----------

flightData2015.sort("count").explain()

// COMMAND ----------

spark.conf.set("spark.sql.shuffle.partitions", "5")

// COMMAND ----------

flightData2015.sort("count").take(2)

// COMMAND ----------

// load df into temp SQL table
flightData2015.createOrReplaceTempView("flight_data_2015")

// COMMAND ----------

val sqlWay = spark.sql("""
SELECT DEST_COUNTRY_NAME, count(1)
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
""")

// COMMAND ----------

val dataFrameWay = flightData2015
  .groupBy('DEST_COUNTRY_NAME)
  .count()

// COMMAND ----------

sqlWay.explain

// COMMAND ----------

dataFrameWay.explain

// COMMAND ----------

spark.sql("SELECT max(count) from flight_data_2015").take(1)

// COMMAND ----------

import org.apache.spark.sql.functions.max

flightData2015.select(max("count")).take(1)

// COMMAND ----------

val maxSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count) DESC
LIMIT 10
""")

// COMMAND ----------

val minSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count), DEST_COUNTRY_NAME
LIMIT 10
""")

// COMMAND ----------

display(maxSql)

// COMMAND ----------

display(minSql)

// COMMAND ----------

import org.apache.spark.sql.functions.desc

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(8)
  .show()

// COMMAND ----------

display(flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort("destination_total", "DEST_COUNTRY_NAME")
  .limit(8))

// COMMAND ----------

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(8)
  .explain()
