// Databricks notebook source
// Use this Databricks dataset:

// /databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv
// Load the data into a Spark dataframe. Infer the schema and include the header.
// How many columns does this dataframe have? How many rows?
// How many farmer's markets are in your zip code? In your state? In the country?
// What farmer's market has the longest name?
// What are the top ten states with the most farmer's markets?
// Display visualizations. Explore plot options.
// Export your notebook as an HTML file. Commit the file to your GitHub repo. (You can use nbviewer to view your HTML file on GitHub.)

// COMMAND ----------

val df = spark.read.option("inferSchema", "true").option("header", "true").csv("/databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv")
df.show()

// COMMAND ----------

display(df)

// COMMAND ----------

// How many columns does this dataframe have? How many rows?
println(df.columns.length)
println(df.count)

// COMMAND ----------

df.dtypes

// COMMAND ----------

display(df.describe())

// COMMAND ----------

// How many farmer's markets are in your zip code? In your state? In the country?
// Register the DataFrame as a SQL temporary view
df.createOrReplaceTempView("market")

// spark.sql("SELECT * FROM market").show()
display(spark.sql("SELECT * FROM market WHERE zip == 30310"))

// COMMAND ----------

// spark.sql("SELECT * FROM market").show()
display(spark.sql("SELECT * FROM market WHERE State == 'Georgia'"))

// COMMAND ----------

// What farmer's market has the longest name?
// spark.sql("SELECT * FROM market").show()
// display(spark.sql("SELECT * FROM market WHERE State == 'Georgia'"))
display(spark.sql("SELECT LENGTH(MarketName) AS LengthOfName, MarketName FROM market ORDER BY LengthOfName DESC"))

// COMMAND ----------

// What are the top ten states with the most farmer's markets?
display(spark.sql("SELECT State FROM market GROUP BY State ORDER BY count(State) DESC"))

// COMMAND ----------

// https://docs.databricks.com/_static/notebooks/charts-and-graphs-scala.html
// Display visualizations. Explore plot options.

display(df)
// Plot X and Y

// COMMAND ----------

display(df)

// COMMAND ----------

// MAGIC %md
// MAGIC Extra

// COMMAND ----------

df.printSchema()

// COMMAND ----------

// This import is needed to use the $-notation
import spark.implicits._
df.select($"State", $"zip").show()

// COMMAND ----------


