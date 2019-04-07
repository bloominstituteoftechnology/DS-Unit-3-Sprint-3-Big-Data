// Databricks notebook source
// MAGIC %md
// MAGIC ### Sum of squares
// MAGIC If we list all the square numbers below 100, we get 1, 4, 9, 16, 25, 36, 49, 64, 81. The sum of these squares is 285.
// MAGIC 
// MAGIC Write Scala code to calculate that the sum of all the square numbers below 100 is 285.

// COMMAND ----------

var sum = 0
for(idx <- 1 to 9)
  {
  sum = sum + (idx * idx)
  }

// COMMAND ----------

// MAGIC %md
// MAGIC ### Optional bonus
// MAGIC To score a 3, write a Scala program that prints the numbers from 1 to 100. But for multiples of three print “Fizz” instead of the number and for the multiples of five print “Buzz.” For numbers which are multiples of both three and five print “FizzBuzz.”

// COMMAND ----------

// print numbers from 1 to 100. But for multiples of three print “Fizz” instead of the number and for the
// multiples of five print “Buzz.” For numbers which are multiples of both three and five print “FizzBuzz.”
for(idx <- 1 to 100)
  {
  if (idx % (3*5) == 0) {println("FizzBuzz")}
  else if (idx % 3 == 0) {println("Fizz")}
  else if (idx % 5 == 0) {println("Buzz")}
  else {println(idx)}
  }
