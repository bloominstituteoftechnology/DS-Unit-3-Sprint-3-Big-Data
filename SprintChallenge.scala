// Databricks notebook source
// If we list all the square numbers below 100, we get 1, 4, 9, 16, 25, 36, 49, 64, 81. The sum of these squares is 285.
var lists = 1 to 9
var squares = lists.map(i => i * i)
var sum_squares = squares.reduce(_ + _)

// COMMAND ----------

// Write Scala code to find the sum of all the square numbers below 1000.
var lists = 1 to 31
var squares = lists.map(i => i * i)
var sum_squares = squares.reduce(_ + _)

// COMMAND ----------

// Optional Bonus
// To score a 3, write a Scala program that prints the numbers from 1 to 100. 
// But for multiples of three print “Fizz” instead of the number and for the multiples of five print “Buzz.” 
// For numbers which are multiples of both three and five print “FizzBuzz.”

for( i <- 1 to 100 ){
  if( i%3 == 0 && i%5 == 0){
    println("FizzBuzz");
  }
  else if( i%3 == 0){
    println("Fizz");
  }
  else if( i%5 == 0){
    println("Buzz");
  }
  else {
    println(i);
  }
}

// COMMAND ----------

val df = spark.read.option("inferSchema", "true").option("header", "true").csv("/databricks-datasets/samples/population-vs-price/data_geo.csv")

// COMMAND ----------

println("Columns", df.columns.length)
println("Rows", df.count())

// COMMAND ----------

val df2 = df.na.drop()
println("Rows", df2.count())

// COMMAND ----------

display(df2)

// COMMAND ----------

df2.createOrReplaceTempView("df2")
spark.sql("""SELECT City FROM df2 WHERE `State Code` = "UT" """).show()

// COMMAND ----------

// Sort the cleaned dataframe by 2015 median sales price (either ascending or descending), 
// using either Spark SQL or the DataFrame API. Your results will display that San Jose, 
// California was most expensive in this dataset ($900,000) and Rockford, Illinois was least expensive ($78,600). 
display(spark.sql("""SELECT * FROM df2 ORDER BY `2015 Median Sales Price` DESC LIMIT 1"""))

// COMMAND ----------

display(spark.sql("""SELECT * FROM df2 ORDER BY `2015 Median Sales Price` ASC LIMIT 1"""))

// COMMAND ----------

// Stretch
display(df2.sort($"2015 Median Sales Price".asc).take(1))

// COMMAND ----------

display(df2.sort($"2015 Median Sales Price".desc).take(1))

// COMMAND ----------


