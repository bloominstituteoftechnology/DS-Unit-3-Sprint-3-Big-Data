// Databricks notebook source
spark

// COMMAND ----------

val NUM_SAMPLES = math.pow(10, 7).toInt

// COMMAND ----------

val count = sc.parallelize(1 to NUM_SAMPLES).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count()

println(s"Pi is roughly ${4.0 * count / NUM_SAMPLES}")

// COMMAND ----------

val flightData2015 = spark
.read
.option("inferSchema", "true")
.option("header","true")
.csv("/databricks-datasets/definitive-guide/data/flight-data/csv/2015-summary.csv")

// COMMAND ----------

flightData2015.take(3)

// COMMAND ----------

flightData2015.sort("count").explain()

// COMMAND ----------

flightData2015.sort("count").take(2)

// COMMAND ----------

flightData2015.createOrReplaceTempView("flight_data_2015")

// COMMAND ----------

val sqlWay = spark.sql("""
SELECT DEST_COUNTRY_NAME, count(1)
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME""")

val dataFrameWay = flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .count()

sqlWay.explain
dataFrameWay.explain

// COMMAND ----------

spark.sql("SELECT max(count) from flight_data_2015").take(1)

// COMMAND ----------

import org.apache.spark.sql.functions.max
flightData2015.select(max("count")).take(1)

// COMMAND ----------

val maxSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) AS destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count) DESC
LIMIT 5""")

maxSql.collect()

// COMMAND ----------

import org.apache.spark.sql.functions.desc

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .collect()

// COMMAND ----------

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .explain()

// COMMAND ----------



// COMMAND ----------

val DF1 = spark.read.format("csv")
  .option("inferSchema", "true")
  .option("header", "true")
  .load("/databricks-datasets/definitive-guide/data/flight-data/csv/2015-summary.csv")

// COMMAND ----------

val DF2 = DF1.groupBy("DEST_COUNTRY_NAME").count().collect()
val DF3 = DF1.groupBy("ORIGIN_COUNTRY_NAME").count().collect()
val df4 = DF1.groupBy("count").count().collect()

// COMMAND ----------

DF1.cache()
DF1.count()

// COMMAND ----------

val DF2 = DF1.groupBy("DEST_COUNTRY_NAME").count().collect()
val DF3 = DF1.groupBy("ORIGIN_COUNTRY_NAME").count().collect()
val df4 = DF1.groupBy("count").count().collect()

// COMMAND ----------



// COMMAND ----------



// COMMAND ----------

val staticDataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/definitive-guide/data/retail-data/by-day/*.csv")

staticDataFrame.createOrReplaceTempView("retail_data")
val staticSchema = staticDataFrame.schema

// COMMAND ----------

import org.apache.spark.sql.functions.{window, column, desc, col}
staticDataFrame
  .selectExpr(
  "CustomerId",
  "(UnitPrice * Quantity) as total_cost",
  "InvoiceDate")
  .groupBy(
    col("CustomerId"), window(col("InvoiceDate"), "1 day"))
  .sum("total_cost")
  .show(5)

// COMMAND ----------

spark.conf.set("spark.sql.shuffle.partitions", "5")

// COMMAND ----------

val streamingDataFrame = spark.readStream
  .schema(staticSchema)
  .option("maxFilesPerTrigger", 1)
  .format("csv")
  .option("header", "true")
  .load("/databricks-datasets/definitive-guide/data/retail-data/by-day/*.csv")

// COMMAND ----------

streamingDataFrame.isStreaming

// COMMAND ----------

val purchaseByCustomerPerHour = streamingDataFrame
  .selectExpr(
    "CustomerId",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    $"CustomerId", window($"InvoiceDate", "1 day"))
  .sum("total_cost")

// COMMAND ----------

purchaseByCustomerPerHour.writeStream
  .format("memory") //memory = store in-memory table
  .queryName("customer_purchases") //the name of the in-memory table
  .outputMode("complete") // complete = all the counts should be in the table
  .start()
  

// COMMAND ----------

spark.sql("""
  SELECT *
  FROM customer_purchases
  ORDER BY 'sum(total_cost)' DESC
  """)
  .show(5)

// COMMAND ----------


