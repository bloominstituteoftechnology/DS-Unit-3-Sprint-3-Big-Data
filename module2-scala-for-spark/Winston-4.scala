// Databricks notebook source
/////////////////////////////////////////////////
// 4. Data Structures
/////////////////////////////////////////////////

// COMMAND ----------

val a = Array(1, 2, 3, 5, 8, 13)
a(0)     // Int = 1
a(3)     // Int = 5

val s = Set(1, 3, 7)
s(0)      // Boolean = false
s(1)      // Boolean = true

// COMMAND ----------

a(21)    // Throws an exception


// COMMAND ----------

// Tuples

(1, 2)

// COMMAND ----------

(4, 3, 2)

// COMMAND ----------

(1, 2, "three")

// COMMAND ----------

(a, 2, "three")

// COMMAND ----------

// Why have this?
val divideInts = (x: Int, y: Int) => (x / y, x % y)

// The function divideInts gives you the result and the remainder

divideInts(10, 3)    // (Int, Int) = (3,1)

// COMMAND ----------

// To access the elements of a tuple, use _._n where n is the 1-based index of
// the element
val d = divideInts(10, 3)    // (Int, Int) = (3,1)

d._1    // Int = 3

// COMMAND ----------

d._2    // Int = 1

// COMMAND ----------

// Alternatively you can do multiple-variable assignment to tuple, which is more
// convenient and readable in many cases
val (div, mod) = divideInts(10, 3)

println(div)     // Int = 3
mod     // Int = 1
