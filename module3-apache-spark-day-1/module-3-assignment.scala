// Databricks notebook source
spark

// COMMAND ----------

val NUM_SAMPLES = math.pow(10,5).toInt

// COMMAND ----------

val accum = sc.doubleAccumulator("My Accumulator")

// COMMAND ----------

// Nilakantha Series
accum.reset()
accum.add(3.0)
sc.parallelize(1 to NUM_SAMPLES).foreach(I => accum.add({
  val i = I.toDouble
  val sign = if (I % 2 == 0) -1 else 1 
  sign * 4.0/((2*i)* (2*i + 1)* (2*i + 2))
}
))


// COMMAND ----------

math.abs((accum.value - math.Pi) / math.Pi)

// COMMAND ----------

val file_location_csv = "/FileStore/tables/201[0,1]*.csv"
val staticDataFrame = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load(file_location_csv)

staticDataFrame.createOrReplaceTempView("retail_data")
val staticSchema = staticDataFrame.schema

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.sql.functions.{window, column, desc, col}
staticDataFrame
  .selectExpr(
    "CustomerID",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    col("CustomerID"), window(col("InvoiceDate"), "1 day"))
  .sum("total_cost")
  .show(5)

// COMMAND ----------

// COMMAND ----------

spark.conf.set("spark.sql.shuffle.partitions", "5")


// COMMAND ----------

val streamingDataFrame = spark.readStream
    .schema(staticSchema)
    .option("maxFilesPerTrigger", 1)
    .format("csv")
    .option("header", "true")
    .load(file_location_csv)

// COMMAND ----------

// COMMAND ----------

streamingDataFrame.isStreaming // returns true


// COMMAND ----------

// in Scala
val purchaseByCustomerPerHour = streamingDataFrame
  .selectExpr(
    "CustomerID",
    "(UnitPrice * Quantity) as total_cost",
    "InvoiceDate")
  .groupBy(
    $"CustomerID", window($"InvoiceDate", "1 day"))
  .sum("total_cost")

// COMMAND ----------

// COMMAND ----------

// in Scala
purchaseByCustomerPerHour.writeStream
    .format("memory") // memory = store in-memory table
    .queryName("customer_purchases") // the name of the in-memory table
    .outputMode("complete") // complete = all the counts should be in the table
    .start()

// COMMAND ----------

// COMMAND ----------

// in Scala
spark.sql("""
  SELECT *
  FROM customer_purchases
  ORDER BY `sum(total_cost)` DESC
  """)
  .show(5)


// COMMAND ----------

staticDataFrame.printSchema()

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.sql.functions.date_format
val preppedDataFrame = staticDataFrame
  .na.fill(0)
  .withColumn("day_of_week", date_format($"InvoiceDate", "EEEE"))
  .coalesce(5)

// COMMAND ----------

// COMMAND ----------

// in Scala
val trainDataFrame = preppedDataFrame
  .where("InvoiceDate < '2011-07-01'")
val testDataFrame = preppedDataFrame
  .where("InvoiceDate >= '2011-07-01'")

// COMMAND ----------

// COMMAND ----------


println(trainDataFrame.count())
println(testDataFrame.count())

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.ml.feature.StringIndexer
val indexer = new StringIndexer()
  .setInputCol("day_of_week")
  .setOutputCol("day_of_week_index")

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.ml.feature.OneHotEncoder
val encoder = new OneHotEncoder()
  .setInputCol("day_of_week_index")
  .setOutputCol("day_of_week_encoded")

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.ml.feature.VectorAssembler

val vectorAssembler = new VectorAssembler()
  .setInputCols(Array("UnitPrice", "Quantity", "day_of_week_encoded"))
  .setOutputCol("features")

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.ml.Pipeline

val transformationPipeline = new Pipeline()
  .setStages(Array(indexer, encoder, vectorAssembler))


// COMMAND ----------

// in Scala
val fittedPipeline = transformationPipeline.fit(trainDataFrame)


// COMMAND ----------

// in Scala
val transformedTraining = fittedPipeline.transform(trainDataFrame)


// COMMAND ----------

transformedTraining.cache()

// COMMAND ----------

// COMMAND ----------

// in Scala
import org.apache.spark.ml.clustering.KMeans
val kmeans = new KMeans()
  .setK(20)
  .setSeed(1L)


// COMMAND ----------

// in Scala
val kmModel = kmeans.fit(transformedTraining)


// COMMAND ----------

kmModel.computeCost(transformedTraining)

// COMMAND ----------

// COMMAND ----------

// in Scala
val transformedTest = fittedPipeline.transform(testDataFrame)


// COMMAND ----------

kmModel.computeCost(transformedTest)


// COMMAND ----------

// in Scala
spark.sparkContext.parallelize(Seq(1, 2, 3)).toDF()


// COMMAND ----------

// COMMAND ----------



// COMMAND ----------


