package cosc250.cop.untyped

import com.wbillingsley.amdram.*
import cosc250.cop._
import cosc250.cop.Word.{Buzz, Fizz, FizzBuzz}


/*
 * Now you need to define your FizzBuzz Actor...
 * 
 * Amdram actors are all typed. But we can work as if they were untyped by defining a MessageHandler[Any]
 * 
 * Although it makes the type signatures easier (Any), it's not a good idea to use this for all your actors.
 * The trade-off is that the compiler can't tell you if you've handled all the cases you need to or not.
 * And it's easy to get tuples the wrong way around, etc.
 * 
 */
class FizzBuzzActor(name:String) extends MessageHandler[Any] {

  private var number = 0

  def fizzBuzz(i:Int):FizzBuzzMessage =
    if i % 15 == 0 then FizzBuzz
    else if i % 5 == 0 then Buzz
    else if i % 3 == 0 then Fizz
    else i

  override def receive(msg:Any)(using context: ActorContext[Any]) = {
    log(name)(msg)
    msg match {
      // Now decide how your actor is going to respond to the messages. Note, you might need to
      // Create member variables and functions...

      // We have to have at least one case statement to make this compile as a PartialFunction
      // with the types...
      case (RefereeMessage.YourTurn, referee:Recipient[Any] @unchecked) => {
        referee ! (fizzBuzz(number + 1), context.self)
      }
      case _  =>
        number += 1
    }
  }
}