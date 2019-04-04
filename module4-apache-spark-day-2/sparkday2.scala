// Databricks notebook source
// Load into spark dataframe, infer schema, include the header
val df = spark.read.format("csv")
.option("header", "true")
.option("inferSchema", "true")
.load("/databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv")

// COMMAND ----------

// How many columns does this dataframe have? How many rows?
df.columns.size

// COMMAND ----------

df.count()

// COMMAND ----------

display(df)

// COMMAND ----------

// How many farmers markets are in my zipcode? In my state? In the country?
df.filter($"zip" === 90265).count()

// COMMAND ----------

// Cali is a state of mind
df.filter($"State" === "California").count()

// COMMAND ----------

// Total farmers markets (in the country)
df.count()

// COMMAND ----------

// What farmers market has the longest name?
import org.apache.spark.sql.functions.length
import org.apache.spark.sql.functions.max

df.createOrReplaceTempView("markets")

display(spark.sql("""
SELECT MarketName, length(MarketName)
FROM markets
ORDER BY length(MarketName) DESC
LIMIT 5
"""))

// COMMAND ----------

// What are the top ten states with the most farmers markets?
import org.apache.spark.sql.functions.col

df.groupBy("State").count().sort(col("count").desc).limit(10).show()

// COMMAND ----------

// Display some visualizations...
display(df)

// COMMAND ----------


