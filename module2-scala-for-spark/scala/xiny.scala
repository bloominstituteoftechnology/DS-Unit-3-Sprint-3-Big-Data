def sumOfSquares(x: Int, y: Int): Int = {
  val x2 = x * x
  val y2 = y * y
  x2 + y2
}
// is equivalent to 
def sumOfSquaresShort(x: Int, y: Int): Int = x * x + y * y

def subtract(x: Int, y: Int): Int = x - y
print("named args can be given in any ordering ")
println(subtract(y=10, x=7) == 7-10)

// anonymous
print("anonymous function works ")
println(((x: Int) => x * x)(10) == 100)

val weirdSum: (Int, Int) => Int = (_ * 2 + _ * 3)
// wildcards for when arguments are used once in body.
