// Databricks notebook source
/////////////////////////////////////////////////
// 3. Flow Control
/////////////////////////////////////////////////

// COMMAND ----------

1 to 5
val r = 1 to 5
r.foreach(println)

// COMMAND ----------

(5 to 1 by -1) foreach (println)

// COMMAND ----------

// Recursion is the idiomatic way of repeating an action in Scala (as in most
// other functional languages).
// Recursive functions need an explicit return type, the compiler can't infer it.
// Here it's Unit, which is analagous to a `void` return type in Java
def showNumbersInRange(a: Int, b: Int): Unit = {
  print(a, ' ')
  if (a < b)
    showNumbersInRange(a + 1, b)
}
showNumbersInRange(1, 14)

// COMMAND ----------

// A while loop
var i = 0
while (i < 10) { println("i " + i); i += 1 }

while (i < 10) { println("i " + i); i += 1 }   // Yes, again. What happened? Why?

i    // Show the value of i. Note that while is a loop in the classical sense -
     // it executes sequentially while changing the loop variable. while is very
     // fast, but using the combinators and comprehensions above is easier
     // to understand and parallelize



// COMMAND ----------

// Conditionals

val x = 10

if (x == 1) println("yeah")
if (x == 10) println("yeah")
if (x == 11) println("yeah")


// COMMAND ----------

if (x == 11) println("yeah") else println("nay")


// COMMAND ----------

println(if (x == 10) "yeah" else "nope")
val text = if (x == 10) "yeah" else "nope"

