//import Exercises.MyNil
import cats.data.State

object Exercises {

  /**
    * Exercise 1: Basic
    *
    * A simple definition of the exponential function could be given by
    */
  def exp(x: Int, n: Int): Int = n match {
    case 0 => 1
    case _ => x * exp(x, n - 1)
  }

  //Todo: write a function exp2 that is more efficient than exp for large n, treating the cases n is even/odd separately
  //you can exploit that exp(x,n) == exp(exp(x, n/2), 2)
  def exp2(x: Int, n: Int): Int = ???


  /**
    * Exercise 2: Basic
    *
    * Given the following definitions:
    **/
  def thrice(x: Int) = List(x, x, x)

  def sums(ls: List[Int]): List[Int] = ls match {
    case x :: y :: tail => x :: sums(x + y :: tail)
    case tail => tail
  }

  /**
    * What does the following expression evaluate to?
    */
  val x = sums(List(1, 2, 3, 4)).map(thrice)
  //Todo: fill in answer2
  val answer = ???
  // run ExercisesTest to check answer!


  /** Exercise 3: Learning about an ADT
    *
    * Rember that List is defined as an algebraic datatype (ADT) along the following lines: */
  sealed trait MyList[+A] /*extends Functor[MyList]*/ {
  case class MyCons[+A](head: A, tail: MyList[A]) extends MyList[A]
  case object MyNil extends MyList[Nothing]

  object MyList {
    //here we implement the algebra of MyList

    /**smart constructor for MyNil. That is, we can create a MyNil with MyList.myNil, which will convince the compiler that
      * it is of type MyList[A] and not of type MyNil*/
    def myNil[A]: MyList[A] = MyNil
    /**smart consturctor for MyCons*/
    def myCons[B](h: B, t: MyList[B]): MyList[B] = MyCons(h,t)

    //Todo: implement a method lastOption that returns the optional last element of the list
    def lastOption[A](ls: MyList[A]): Option[A] = ???

    //Todo: also implement map
    def map[A,B](ls: MyList[A])(f: A=> B): MyList[B] = ???
    /* Optional Terminology: If an ADT (algebraic data type) has a map function, then it is called a Functor */


    //Todo: extra: implement flatMap and unit
    /*unit is the name of the function that lifts an element into the ADT. In the case of
    List, it is the function a: A => List(a). */

    def flatMap = ???
    def unit = ???

    /*Optional Terminology: if an ADT implements the functions flatMap and unit, then it
    is a Monad.*/
  }
  }

  /** Exercise 4: Optional: short intro to High Kinded Types
    *
    *
    * Note that List is not a type, though List[Int], List[String] and List[Float] are! we can
    * look at List as a function between types, it wil map from Int to List[Int], form String to List[String} etc.
    * We therefore call List a type constructor
    *
    * Just as there are type parameters, there are type constructor parameters! For example:
    * A type constructor that has a map method, is called a Functor
    **/

  //Todo: Mix the Functor trait into the companion object of MyList. This makes explicit that MyList is a Functor.
  //what are some disadvantages of mixing Functor into MyList?
  trait Functor[TC[_]] {
    //TC stands for type constructor, [_] is needed since it is not a type, but a type constructor
    def map[A, B](self: TC[A])(f: A => B): TC[B]
  }



  /**Exercise 5: Basic currying*/


  /**Let say we have the function add, that just adds two integers*/
  def add(n: Int, m: Int): Int = n + m

  /**We want to use this function to implement def incr, that adds 1 to a integer*/
  val incr: Int => Int = ??? //todo: Implement incr by partially applying add


  val incr_curry: Int => Int = ??? //todo: Implement incr a second time, now by making add a curried function. use Function2.curried

  /**Observe that a curried function takes 1 argument, and return a partially applied function!
      *
      * In this example, the argument taken by the curried function is 1: Int. The partially
      * applied function that is returned is incr_curry
      * */

  //Todo: finish the test property to test the functions above.



  /**Exercise 6: State Actions revisited*/

  /**
    * We are going to make a simple implementation of a Tamagochi!
    */
  object Tamagotchi {

    /**the state of our Tamagotchi is modelled with a case class*/
    case class Gotchi(fuel: Int, happiness: Int, rest: Int)

    /**Posible reactions of the tamagotchi*/
    trait Reaction
    case object Laugh extends Reaction
    case object Cry extends Reaction
    case object Vomit extends Reaction
    case object Die extends Reaction
    case object Sleep extends Reaction

    /**we can feed our tamagotchi. Feed is a state action, which means that it can change the state, and yields a result,
      * the result will be the reaction of the tamagotchi
      *
      * State actions are used when we want to remove the logic of having to deal with some internal state away from the programmer
      * */
    def feed: State[Gotchi, Reaction] = State {
        gotchi => {
          val newFuel = gotchi.fuel + 1 //add one to fuel
          val reaction = if (newFuel < 0) Die else if (newFuel > 100) Vomit else Laugh
          (gotchi.copy(fuel = newFuel), reaction) //returns function that returns the new state and the reaction
        }
      }
      //todo: playing costs energy! add this to the method, using "useEnergy" below.
      def play: State[Gotchi, Reaction] = State {
        gotchi => {
          val newHappy = gotchi.happiness + 1 //add one to happiness
          val reaction = if (newHappy < 0) Cry else Laugh
          (gotchi.copy(happiness = newHappy), reaction)
        }
      }
      /**State action that lets the tamagotchi rest*/
      def toBed(hours: Int): State[Gotchi, Reaction] = State {
        gotchi => {
          val tired = gotchi.rest < 0
          val newRest = gotchi.rest + hours

          if (tired) (gotchi.copy(rest = newRest), Sleep) //the gotchi is tired, so it will sleep
          else (gotchi, Cry) //the gotchi is not tired, so it will not go to sleep but Cry
        }
      }

      //Todo: implement a state action, that changes the state, by reducing the gotchi;s energy.
      def useEnergy(cal: Int): State[Gotchi, Unit] = State.modify(???)

      //Todo: implement a reset state action. State.set(_) returns a state action that sets the state, but has no result
      def reset: State[Gotchi, Unit] = State.set(???)


      //todo: write a state action, that performs a state action "stateAct" as long as the gotchi keeps laughing
      def doWhileLaughing(stateAct: State[Gotchi, Reaction]): State[Gotchi, Reaction] = ???
    }
}
