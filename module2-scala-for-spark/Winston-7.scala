// Databricks notebook source
// from earlier
val sq: Int => Int = x => x * x

/////////////////////////////////////////////////
// 7. Functional Programming
/////////////////////////////////////////////////

// Scala allows methods and functions to return, or take as parameters, other
// functions or methods.

// COMMAND ----------

val add10: Int => Int = _ + 10 // A function taking an Int and returning an Int
List(1, 2, 3) map add10 // List(11, 12, 13) - add10 is applied to each element

// COMMAND ----------

// Anonymous functions can be used instead of named functions:
List(1, 2, 3) map (x => x + 10)

// COMMAND ----------

// And the underscore symbol, can be used if there is just one argument to the
// anonymous function. It gets bound as the variable
List(1, 2, 3) map (_ + 10)

// COMMAND ----------

// If the anonymous block AND the function you are applying both take one
// argument, you can even omit the underscore
List("Dom", "Bob", "Natalia") foreach println

// COMMAND ----------

// Combinators
// Using `s` from above:
val s = Set(1, 3, 7)
s.map(sq)

// COMMAND ----------

val sSquared = s.map(sq)

sSquared.filter(_ < 10)

// COMMAND ----------

sSquared.reduce (_+_)

// COMMAND ----------

// The filter function takes a predicate (a function from A -> Boolean) and
// selects all elements which satisfy the predicate
List(1, 2, 3) filter (_ > 2) // List(3)

// COMMAND ----------

// diz wacked
case class Person(name: String, age: Int)
List(
  Person(name = "Dom", age = 23),
  Person(name = "Bob", age = 30)
).filter(_.age > 25) // List(Person("Bob", 30))

// COMMAND ----------

// Certain collections (such as List) in Scala have a `foreach` method,
// which takes as an argument a type returning Unit - that is, a void method
val aListOfNumbers = List(1, 2, 3, 4, 10, 20, 100)
aListOfNumbers foreach (x => println(x))
aListOfNumbers foreach println
