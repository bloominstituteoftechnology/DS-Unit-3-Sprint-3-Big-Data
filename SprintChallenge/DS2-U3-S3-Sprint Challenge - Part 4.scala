// Databricks notebook source
// MAGIC %md
// MAGIC # Part 4. Spark SQL / DataFrame API
// MAGIC 
// MAGIC In this part, you'll work with data that compares city population versus median sale prices of homes. This is an example Databricks dataset available at this path:
// MAGIC ```
// MAGIC /databricks-datasets/samples/population-vs-price/data_geo.csv
// MAGIC ```
// MAGIC 
// MAGIC Load the data into a Spark dataframe. Infer the schema and include the header.

// COMMAND ----------

// imports
import org.apache.spark.sql._

// COMMAND ----------

val df = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv("/databricks-datasets/samples/population-vs-price/data_geo.csv")

// COMMAND ----------

// MAGIC %md
// MAGIC Write code to show the dataframe has 294 rows and 6 columns.

// COMMAND ----------

val df_rows = df.count()
val df_cols = df.columns.size
//println("The dataframe has ", df_rows, " rows and ", df_cols, " columns")

// COMMAND ----------

// MAGIC %md
// MAGIC Drop rows containing any null values.

// COMMAND ----------

val df2 = df.na.drop()

// COMMAND ----------

// MAGIC %md
// MAGIC Write code to show the cleaned dataframe has 109 rows, after dropping rows with null values.

// COMMAND ----------

val df2_rows = df2.count()

// COMMAND ----------

// MAGIC %md
// MAGIC The cleaned dataframe has 1 city for the state of Utah. Display the name of the city

// COMMAND ----------

display(df2.filter($"State" === "Utah").select("City"))

// COMMAND ----------

// MAGIC %md
// MAGIC Sort the cleaned dataframe by 2015 median sales price

// COMMAND ----------

display(df2.sort("2015 median sales price"))

// COMMAND ----------

// clicked on "2015 median sales price" to show descending
display(df2)
