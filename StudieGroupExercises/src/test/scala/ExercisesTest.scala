
import org.scalacheck.{Arbitrary, Gen, Properties}
import org.scalacheck.Prop.{BooleanOperators, forAll}
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.FunSuite


object ExercisesProperties extends Properties("exercises"){
  import Exercises._

  /**Generates a tuple of two small integers*/
  val tupleSmallIntGen: Gen[(Int, Int)] = for {
    n <- Gen.choose(0, 7)
    x <- Gen.choose(0, 10)
  } yield (n,x)

  property("exp == exp2")  = forAll(tupleSmallIntGen)     { case (n:Int,x: Int) => exp(x,n)  == exp2(x,n) }
  property("exp2 == math.pow") = forAll(tupleSmallIntGen) { case (n:Int,x: Int) => exp(x,n) == math.pow(x,n) }


}

class ExercisesTest extends FunSuite {
  import Exercises._

  test("x == answer"){
    assert(x == answer)
  }
}
