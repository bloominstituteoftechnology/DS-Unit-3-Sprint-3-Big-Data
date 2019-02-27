1 + 1
2 - 1
5 * 3
6 / 2
6 / 4
6.0 / 4
6 / 4.0


// Strings

"hello world".length
"hello world".substring(2, 6)
"hello world".replace("C", "3")

"hello world".take(5)
"hello world".drop(5)


val n = 45
s"We have $n apples"
s"Power of 2: ${math.pow(2, 2)}"
"They stood outside the \"Rose and Crown\""

// Functions

def sumofSquares(x: Int, y: Int): Int = {
  val x2 = x * x
  val y2 = y * y
  x2 + y2
}

def sumOfSquaresShort(x: Int, y: Int): Int = x * x + y * y //single line func

def subtract(x: Int, y: Int): Int = x - y

subtract(10, 3)
subtract(3, 10)

def sq(x: Int) = x * x // can intuit return type

def addWithDefault(x: Int, y: Int = 5) = x + y //default params
addWithDefault(1, 2)
addWithDefault(1)

(x: Int) => x * x //anonymous function

val sq: Int => Int = x => x * x

// placeholder definition
val addOne: Int => Int = _ + 1
val weirdSum: (Int, Int) => Int = (_ * 2 + _ * 3)

//FLOW CONTROL

1 to 5
val r = 1 to 5
r.foreach(println)
r foreach println

(5 to 1 by -1) foreach (println)

// recursion needs explicit return type
def showNumbersInRange(a: Int, b: Int): Unit = {
  print(a)
  if (a < b)
    showNumbersInRange(a + 1, b)
}
showNumbersInRange(1, 14)

// CONDITIONALS

val x = 10

if (x == 1) println("yeah")
if (x == 10) println("yeah")
if (x == 11) println("yeah")
if (x == 11) println("yeah") else println("nay")

println(if (x == 10) "yeah" else "nope")
val text = if (x == 10) "yeah" else "nope"

/////////////////////////////////////////////////
// 4. Data Structures
/////////////////////////////////////////////////

val a = Array(1, 2, 3, 5, 8, 13)
a(0)
a(3)
a(21) //no good

val s = Set(1, 3, 7)
s(0) //false
s(1) //true

val divideInts = (x: Int, y: Int) => (x / y, x % y)
val d = divideInts(10, 3)

d._1    //Division Result
d._2    //Remainder

val (div, mod) = divideInts(10, 3) // explicit assignment

/////////////////////////////////////////////////
// 7. Functional Programming
/////////////////////////////////////////////////

val add10: Int => Int = _ + 10
List(1, 2, 3) map add10

List(1, 2, 3) map (x => x + 10)

List(1, 2, 3) map (_ + 10) //if only one argument


//from set above
s.map(sq)

val sSquared = s. map(sq)

sSquared.filter(_ < 10)

sSquared.reduce (_+_)


case class Person(name: String, age: Int)
List(
  Person(name = "Dom", age = 23),
  Person(name = "Bob", age = 30)
).filter(_.age > 25)

val aListOfNumbers = List(1, 2, 3, 4, 10, 20, 100)
aListOfNumbers foreach (x => println(x))
aListOfNumbers foreach println

/////////////////////////////////////////////////
// 9. Misc
/////////////////////////////////////////////////

// Importing things
import scala.collection.immutable.List

// Import all "sub packages"
import scala.collection.immutable._

// Import multiple classes in one statement
import scala.collection.immutable.{List, Map}

// Rename an import using '=>'
import scala.collection.immutable.{List => ImmutableList}
