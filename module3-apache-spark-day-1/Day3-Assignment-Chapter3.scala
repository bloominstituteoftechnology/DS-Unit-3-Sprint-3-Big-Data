// Databricks notebook source
import spark.implicits._
case class Flight(DEST_COUNTRY_NAME: String,
                  ORIGIN_COUNTRY_NAME: String,
                  count: BigInt)
val flightsDF = spark.read
  .parquet("/databricks-datasets/definitive-guide/data/flight-data/parquet/2010-summary.parquet/")
val flights = flightsDF.as[Flight]

// COMMAND ----------

flights
  .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
  .map(flight_row => flight_row)
  .take(8)

// COMMAND ----------

flights
  .take(8)
  .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
  .map(fr => Flight(fr.DEST_COUNTRY_NAME, fr.ORIGIN_COUNTRY_NAME, fr.count * 2))

// COMMAND ----------

// MAGIC %md
// MAGIC Actual assignment starts here

// COMMAND ----------

val staticDataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/definitive-guide/data/retail-data/by-day/*.csv")

// COMMAND ----------

staticDataFrame.createOrReplaceTempView("retail_data")

// COMMAND ----------

val staticSchema = staticDataFrame.schema

// COMMAND ----------

import org.apache.spark.sql.functions.{window, column, desc, col}
display(staticDataFrame
  .selectExpr(
    "CustomerId",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    col("CustomerId"), window(col("InvoiceDate"), "1 day"))
  .sum("total_cost"))

// COMMAND ----------

// This configuration simple specifies the number of partitions that should be created after a shuffle, by default the value is 200
// but since there aren’t many executors on my local machine it’s worth reducing this to five.
spark.conf.set("spark.sql.shuffle.partitions", "5")

// COMMAND ----------

val streamingDataFrame = spark.readStream
    .schema(staticSchema)
    .option("maxFilesPerTrigger", 1)
    .format("csv")
    .option("header", "true")
    .load("/databricks-datasets/definitive-guide/data/retail-data/by-day/*.csv")

// COMMAND ----------

streamingDataFrame.isStreaming // returns true

// COMMAND ----------

// call a streaming action to start the execution of this data flow
val purchaseByCustomerPerHour = streamingDataFrame
  .selectExpr(
    "CustomerId",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    $"CustomerId", window($"InvoiceDate", "1 day"))
  .sum("total_cost")

// COMMAND ----------

// kick off the stream - write it out to an in-memory table that will update after each trigger
purchaseByCustomerPerHour.writeStream
    .format("memory") // memory = store in-memory table
    .queryName("customer_purchases") // the name of the in-memory table
    .outputMode("complete") // complete = all the counts should be in the table
    .start()

// COMMAND ----------

// run queries against this table
spark.sql("""
  SELECT *
  FROM customer_purchases
  ORDER BY `sum(total_cost)` DESC
  """)
  .show(8)

// COMMAND ----------

staticDataFrame.printSchema()
