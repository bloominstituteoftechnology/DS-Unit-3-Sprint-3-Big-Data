// Databricks notebook source
val count = sc.parallelize(1 to 10).filter { _ =>
  val x = math.random
  val y = math.random
  x*x + y*y < 1
}.count()
println(s"Pi is roughly ${4.0 * count / 10}")

// COMMAND ----------

val myRange = spark.range(1000).toDF("number")


// COMMAND ----------

val divisBy2 = myRange.where("number % 2 = 0")


// COMMAND ----------

divisBy2.count()


// COMMAND ----------

val flightData2015 = spark
  .read
  .option("inferSchema", "true")
  .option("header", "true")
  .csv("/FileStore/tables/2015_summary-ebaee.csv")


// COMMAND ----------

flightData2015.take(3)

// COMMAND ----------

flightData2015.sort("count").explain()

// COMMAND ----------

spark.conf.set("spark.sql.shuffle.partitions", "5")

// COMMAND ----------

flightData2015.sort("count").take(2)


// COMMAND ----------

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
dataFrameWay.explain

// COMMAND ----------

spark.sql("SELECT max(count) from flight_data_2015").take(1)


// COMMAND ----------

import org.apache.spark.sql.functions.max

// COMMAND ----------

flightData2015.select(max("count")).take(1)


// COMMAND ----------

val maxSql = spark.sql("""
SELECT DEST_COUNTRY_NAME, sum(count) as destination_total
FROM flight_data_2015
GROUP BY DEST_COUNTRY_NAME
ORDER BY sum(count) DESC
LIMIT 5
""")

// COMMAND ----------

import org.apache.spark.sql.functions.desc


// COMMAND ----------

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .show()

// COMMAND ----------

flightData2015
  .groupBy("DEST_COUNTRY_NAME")
  .sum("count")
  .withColumnRenamed("sum(count)", "destination_total")
  .sort(desc("destination_total"))
  .limit(5)
  .explain()

// COMMAND ----------

import spark.implicits._


// COMMAND ----------

case class Flight(DEST_COUNTRY_NAME: String,
                  ORIGIN_COUNTRY_NAME: String,
                  count: BigInt)



// COMMAND ----------

import spark.implicits._
case class Flight(DEST_COUNTRY_NAME: String,
                  ORIGIN_COUNTRY_NAME: String,
                  count: BigInt)
val flightsDF = spark.read
  .parquet("databricks-datasets/definitive-guide/data/flight-data/parquet/2010-summary.parquet/")
val flights = flightsDF.as[Flight]

// COMMAND ----------

flights
  .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
  .map(flight_row => flight_row)
  .take(5)


// COMMAND ----------

flights
  .take(5)
  .filter(flight_row => flight_row.ORIGIN_COUNTRY_NAME != "Canada")
  .map(fr => Flight(fr.DEST_COUNTRY_NAME, fr.ORIGIN_COUNTRY_NAME, fr.count + 5))

// COMMAND ----------

val staticDataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("databricks-datasets/definitive-guide/data/retail-data/by-day/*.csv")


// COMMAND ----------

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
    .load("/data/retail-data/by-day/*.csv")


// COMMAND ----------

streamingDataFrame.isStreaming // returns true

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
    .format("memory") // memory = store in-memory table
    .queryName("customer_purchases") // the name of the in-memory table
    .outputMode("complete") // complete = all the counts should be in the table
    .start()


// COMMAND ----------

spark.sql("""
  SELECT *
  FROM customer_purchases
  ORDER BY `sum(total_cost)` DESC
  """)
  .show(5)

// COMMAND ----------

staticDataFrame.printSchema()

// COMMAND ----------

import org.apache.spark.sql.functions.date_format
val preppedDataFrame = staticDataFrame
  .na.fill(0)
  .withColumn("day_of_week", date_format($"InvoiceDate", "EEEE"))
  .coalesce(5)

// COMMAND ----------

val trainDataFrame = preppedDataFrame
  .where("InvoiceDate < '2011-07-01'")
val testDataFrame = preppedDataFrame
  .where("InvoiceDate >= '2011-07-01'")

// COMMAND ----------

trainDataFrame.count()
testDataFrame.count()


// COMMAND ----------

import org.apache.spark.ml.feature.StringIndexer
val indexer = new StringIndexer()
  .setInputCol("day_of_week")
  .setOutputCol("day_of_week_index")

// COMMAND ----------

import org.apache.spark.ml.feature.OneHotEncoder
val encoder = new OneHotEncoder()
  .setInputCol("day_of_week_index")
  .setOutputCol("day_of_week_encoded")


// COMMAND ----------

import org.apache.spark.ml.feature.VectorAssembler

val vectorAssembler = new VectorAssembler()
  .setInputCols(Array("UnitPrice", "Quantity", "day_of_week_encoded"))
  .setOutputCol("features")

// COMMAND ----------

import org.apache.spark.ml.Pipeline

val transformationPipeline = new Pipeline()
  .setStages(Array(indexer, encoder, vectorAssembler))

// COMMAND ----------

val fittedPipeline = transformationPipeline.fit(trainDataFrame)


// COMMAND ----------

val transformedTraining = fittedPipeline.transform(trainDataFrame)


// COMMAND ----------

transformedTraining.cache()


// COMMAND ----------

import org.apache.spark.ml.clustering.KMeans
val kmeans = new KMeans()
  .setK(20)
  .setSeed(1L)


// COMMAND ----------

val kmModel = kmeans.fit(transformedTraining)


// COMMAND ----------

kmModel.computeCost(transformedTraining)


// COMMAND ----------

val transformedTest = fittedPipeline.transform(testDataFrame)


// COMMAND ----------

kmModel.computeCost(transformedTest)


// COMMAND ----------

spark.sparkContext.parallelize(Seq(1, 2, 3)).toDF()


// COMMAND ----------


