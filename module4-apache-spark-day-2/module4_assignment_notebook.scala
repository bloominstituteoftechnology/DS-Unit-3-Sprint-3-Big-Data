// Databricks notebook source
spark

// COMMAND ----------

val df = spark
.read
.option("inferSchema", "true")
.option("header", "true")
.csv("/databricks-datasets/data.gov/farmers_markets_geographic_data/data-001/market_data.csv")

// COMMAND ----------

// Number of rows in df
df.count()

// COMMAND ----------

// Number of columns in df
df.columns.size

// COMMAND ----------

// Number of markets in zip
// df.filter($"zip".contains("49007")).count()

val myzip_mkts = spark.sql("""
SELECT COUNT(zip)
FROM df_sql
WHERE zip == '77025'""").collect()

display(myzip_mkts)

// COMMAND ----------

// Number of Markets in State
// df.filter($"State".contains("Texas")).count()

val mystate_mkts = spark.sql("""
SELECT COUNT(State)
FROM df_sql
WHERE State == 'Texas'""").collect()

display(mystate_mkts)

// COMMAND ----------

// Number of Markets in County

val mycounty_mkts = spark.sql("""
SELECT COUNT(County)
FROM df_sql
WHERE County == 'Harris'""").collect()

display(mycounty_mkts)

// COMMAND ----------

df.columns

// COMMAND ----------

// What farmer's market has the longest name?

import org.apache.spark.sql.functions.desc

display(df
  .selectExpr(
  "MarketName",
  "length(MarketName) as name_length")
  .sort(desc("name_length"))
  .limit(1)
  .collect)

// COMMAND ----------



// COMMAND ----------

// What farmer's market has the longest name?

// convert dataframe to sql object
df.createOrReplaceTempView("df_sql")

// sql query needs 'spark.sql' before query AND '.collect() after'
val longest_name = spark.sql("""
SELECT  MarketName
FROM df_sql
ORDER BY LENGTH(MarketName) DESC
LIMIT 1""").collect()

display(longest_name)

// COMMAND ----------

// What are the top ten states with the most farmer's markets?

val top_states_with_mkt = spark.sql("""
SELECT State, COUNT(State)
FROM df_sql
GROUP BY State
ORDER BY COUNT(State) DESC
LIMIT 10""").collect()

display(top_states_with_mkt)

// COMMAND ----------

// Number of States
display(df.select("State").distinct())

// COMMAND ----------


