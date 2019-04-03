// Databricks notebook source
/////////////////////////////////////////////////
// 1. Basics
/////////////////////////////////////////////////

// Single-line comments start with two forward slashes

/*
  Multi-line comments, as you can already see from above, look like this.
*/


// COMMAND ----------

// Printing, and forcing a new line on the next print
println("Hello, World")

// COMMAND ----------

2 + 2

// COMMAND ----------

val x = 10

// COMMAND ----------

x = 20

// COMMAND ----------

var y = 10
y = 20     // y is now 20

// COMMAND ----------

val z: Int = 10
val a: Double = 1.0

// Notice automatic conversion from Int to Double, result is 10.0, not 10
val b: Double = 10

// COMMAND ----------

// Boolean values
print(true)
print(false)

// Boolean operations
!true         // false
!false        // true
true == false // false
10 > 5        // true

// COMMAND ----------

println(1 + 1)   // 2
println(2 - 1)   // 1
println(5 * 3)   // 15
println(6 / 2)   // 3
println(6 / 4)   // 1
println(6.0 / 4) // 1.5
println(6 / 4.0) // 1.5
1 + 7

// COMMAND ----------

"Scala strings are surrounded by double quotes"
'a' // A Scala Char
// 'Single quote strings don't exist' <= This causes an error

// Strings have the usual Java methods defined on them
println("hello world".length)
println("hello world".substring(2, 6))
println("hello world".replace("C", "3"))

// COMMAND ----------

"hello world".take(5)

// COMMAND ----------

"hello world".drop(5)

// COMMAND ----------

val n = 45
s"We have $n apples" // => "We have 45 apples"

// COMMAND ----------

// Expressions inside interpolated strings are also possible
val a = Array(11, 9, 6)
s"My second daughter is ${a(0) - a(2)} years old."    // => "My second daughter is 5 years old."

// COMMAND ----------

s"We have double the amount of ${n / 2.0} in apples." // => "We have double the amount of 22.5 in apples."

// COMMAND ----------

s"Power of 2: ${math.pow(2, 2)}"                      // => "Power of 2: 4"

// COMMAND ----------

// Formatting with interpolated strings with the prefix "f"
f"Power of 5: ${math.pow(5, 2)}%1.0f"         // "Power of 5: 25"

// COMMAND ----------

f"Square root of 122: ${math.sqrt(122)}%1.4f" // "Square root of 122: 11.0454"

// COMMAND ----------

// Raw strings, ignoring special characters.
raw"New line feed: \n. Carriage return: \r." // => "New line feed: \n. Carriage return: \r."

// COMMAND ----------

// Some characters need to be "escaped", e.g. a double quote inside a string:
"They stood outside the \"Rose and Crown\"" // => "They stood outside the "Rose and Crown""

// COMMAND ----------

// Triple double-quotes let strings span multiple rows and contain quotes
val html = """<form id="daform">
                <p>Press belo', Joe</p>
                <input type="submit">
              </form>"""
