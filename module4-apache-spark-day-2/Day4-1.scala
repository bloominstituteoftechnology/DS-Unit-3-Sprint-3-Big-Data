// Databricks notebook source
val df = spark.read.json("/databricks-datasets/samples/people/people.json")

// COMMAND ----------

df.show()

// COMMAND ----------

display(df)

// COMMAND ----------

// df.shape ==>> will give error 'cause don't exist
df.count

// COMMAND ----------

df.columns 

// COMMAND ----------

df.columns.length

// COMMAND ----------

df.dtypes

// COMMAND ----------

df.describe()

// COMMAND ----------

df.describe().show()

// COMMAND ----------

display(df.describe())

// COMMAND ----------

// Register the DataFrame as a SQL temporary view
df.createOrReplaceTempView("people")

val sqlDF = spark.sql("SELECT * FROM people")
sqlDF.show()

// COMMAND ----------

display(sqlDF)

// COMMAND ----------

// Print the schema in a tree format
df.printSchema()

// COMMAND ----------

// Select only the "name" column
display(df.select("name"))

// COMMAND ----------

// Count people by age
df.groupBy("age").count().show()

// COMMAND ----------

// == >> This import is needed to use the $-notation <<==
import spark.implicits._

// COMMAND ----------

// Select everybody, but increment the age by 1
df.select($"name", $"age" + 1).show()

// COMMAND ----------

// Select people older than 21
display(df.filter($"age" > 35))

// COMMAND ----------

val temp = df.take(3)

// COMMAND ----------

temp.foreach(println)

// COMMAND ----------

// range of 100 numbers to create a Dataset.
val range100 = spark.range(100)
range100.collect()

// COMMAND ----------

// inclusive 100
0 to 100

// COMMAND ----------

// not inclusive 100
0 until 100

// COMMAND ----------

// MAGIC %md
// MAGIC Check this shit out

// COMMAND ----------

case class ValueAndDouble(value:Long, valueDoubled:Long)

spark.range(2000)
.map(value => ValueAndDouble(value, value * 2))
.filter(vAndD => vAndD.valueDoubled % 2 == 0)
.where("value % 3 = 0")
.count()

// COMMAND ----------

// with less data
case class ValueAndDouble(value:Long, valueDoubled:Long)

spark.range(200)
.map(value => ValueAndDouble(value, value * 2))
.filter(vAndD => vAndD.valueDoubled % 2 == 0)
.where("value % 3 = 0")
.count()

// COMMAND ----------

// with even and collect instead of count less data
case class ValueAndDouble(value:Long, valueDoubled:Long)

spark.range(20)
.map(value => ValueAndDouble(value, value * 2))
.filter(vAndD => vAndD.valueDoubled % 2 == 0)
.where("value % 3 = 0")
.collect()

// COMMAND ----------

spark.range(20)
.map(value => ValueAndDouble(value, value * 2))

// COMMAND ----------

spark.range(20)
.collect()

// COMMAND ----------

spark.range(20)
.map(value => (value, value * 2))
.collect()

// COMMAND ----------

spark.range(20)
.map(value => (value, value * 2))
.filter(myPlaceholder => myPlaceholder._2 % 2 == 0) // _2 = get second element from tuple
.collect()

// COMMAND ----------

spark.range(20)
.map(value => (value, value * 2))
.filter(myPlaceholder => myPlaceholder._2 % 2 == 0)
.where("_1 % 3 = 0") // where first element divisible by 3
.collect()
