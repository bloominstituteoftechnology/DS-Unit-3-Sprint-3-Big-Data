// Databricks notebook source
// MAGIC %md
// MAGIC Assignment
// MAGIC 
// MAGIC Use this Databricks dataset:
// MAGIC 
// MAGIC /databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv
// MAGIC Load the data into a Spark dataframe. Infer the schema and include the header.
// MAGIC 
// MAGIC How many columns does this dataframe have? How many rows?
// MAGIC 
// MAGIC How many farmer's markets are in your zip code? In your state? In the country?
// MAGIC 
// MAGIC What farmer's market has the longest name?
// MAGIC 
// MAGIC What are the top ten states with the most farmer's markets?
// MAGIC 
// MAGIC Display visualizations. Explore plot options.
// MAGIC 
// MAGIC Export your notebook as an HTML file. Commit the file to your GitHub repo. (You can use nbviewer to view your HTML file on GitHub.)

// COMMAND ----------

val df = spark.read.format("csv")
  .option("header", "true")
  .option("inferSchema", "true")
  .load("/databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv")

// COMMAND ----------

display(df.limit(100))

// COMMAND ----------

println("Data frame has", df.count, "rows")
println("Data frame has", df.columns.size, "columns")

// COMMAND ----------

display(df.describe())

// COMMAND ----------

df.createOrReplaceTempView("markets")
val sqlDF = spark.sql("SELECT * FROM markets WHERE zip == 94903")
display(sqlDF)

// COMMAND ----------

println("There are", sqlDF.count, "Farmer's Markets in zip 94903")

// COMMAND ----------

val sqlDF = spark.sql("SELECT * FROM markets WHERE State == 'California'")
display(sqlDF)

// COMMAND ----------

println("There are", sqlDF.count, "Farmer's Markets in California, which figures")

// COMMAND ----------

// MAGIC %md
// MAGIC Longest Market Name

// COMMAND ----------

//select top 1 CR
//from table t
//order by len(CR) desc
//SELECT CR 
//FROM table1 
//WHERE len(CR) = (SELECT max(len(CR)) FROM table1)
//val sqlDF = spark.sql("SELECT MarketName FROM markets ORDER BY LEN(MarketName) DESC LIMIT 1")
val sqlDF = spark.sql("SELECT MarketName FROM markets WHERE LENGTH(MarketName) = (SELECT MAX(LENGTH(MarketName)) FROM markets)")
display(sqlDF)

// COMMAND ----------

// MAGIC %md
// MAGIC States with most Farmer's Markets

// COMMAND ----------

val sqlDF = spark.sql("SELECT State, count(MarketName) AS NumberOfMarkets FROM markets GROUP BY State ORDER BY count(MarketName) DESC LIMIT 10")
display(sqlDF)

// COMMAND ----------

display(sqlDF)
