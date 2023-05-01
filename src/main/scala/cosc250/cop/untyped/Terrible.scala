package cosc250.cop.untyped

import com.wbillingsley.amdram.*
import cosc250.cop._
import Word._

import scala.util.Random

/**
  * This Actor is shockingly awful at this game. It gets the number and increments it,
  * but
  */
class Terrible extends MessageHandler[Any] {

  private var num:Int = 0

  def nextResponse() = {
    Seq(
      num + 1,
      Fizz,
      Buzz,
      FizzBuzz
    )(Random.nextInt(4))
  }

  override def receive(msg:Any)(using context:ActorContext[Any]) = {
    log("Terrible")(msg) 
    msg match {
      case (RefereeMessage.YourTurn, referee:Recipient[Any] @unchecked) => 
        referee ! (nextResponse(), context.self)
      case _ =>
        num += 1
    }
  }

}
