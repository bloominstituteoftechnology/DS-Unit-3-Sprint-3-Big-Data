// Databricks notebook source
/////////////////////////////////////////////////
// 2. Functions
/////////////////////////////////////////////////

// Functions are defined like so:
//
//   def functionName(args...): ReturnType = { body... }
//
// If you come from more traditional languages, notice the omission of the
// return keyword. In Scala, the last expression in the function block is the
// return value.

// COMMAND ----------

def sumOfSquares(x: Int, y: Int): Int = {
  val x2 = x * x
  val y2 = y * y
  x2 + y2
}
sumOfSquares(3, 4)

// COMMAND ----------

def sumOfSquaresShort(x: Int, y: Int): Int = x * x + y * y
sumOfSquaresShort(7, 24)

// COMMAND ----------

def subtract(x: Int, y: Int): Int = x - y
subtract(10, 3)     // => 7


// COMMAND ----------

subtract(y=10, x=3)

// COMMAND ----------

def sq(x: Int) = x * x  // Compiler can guess return type is Int
sq(22)

// COMMAND ----------

def addWithDefault(x: Int, y: Int = 5) = x + y
addWithDefault(1)    // => 6

// COMMAND ----------

addWithDefault(1, 2) // => 3

// COMMAND ----------

val weirdSum: (Int, Int) => Int = (_ * 2 + _ * 3)
weirdSum(2, 4) // => 16

// COMMAND ----------

val addOne: Int => Int = _ + 1
addOne(5)      // => 6

// COMMAND ----------

val sq1: Int => Int = x => x * x
sq1(12)   // => 144
