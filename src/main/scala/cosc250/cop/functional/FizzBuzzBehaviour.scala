package cosc250.cop.functional

import com.wbillingsley.amdram.*

import cosc250.cop.Word.{Buzz, Fizz, FizzBuzz}
import cosc250.cop._


/** A terrible behaviour, that responds randomly  */
def terribleBehaviour(name:String, i:Int):MessageHandler[PlayerHears] = MessageHandler { (msg, context) =>

  msg match {
    // This terrible behaviour, when challenged, just always says "FizzBuzz"
    case RefereeSays(RefereeMessage.YourTurn, referee) =>
      referee ! PlayerSays(FizzBuzz, context.self)
      
      // And then returns a terrible behaviour for its new state. 
      terribleBehaviour(name, i)

    case RefereeSays(_, _) =>
      // Increment the counter whenever someone says something other than "Your Turn"
      terribleBehaviour(name, i + 1)
  }
      
  
}


def fizzBuzzBehaviour(name:String, i:Int):MessageHandler[PlayerHears] = MessageHandler { (msg, context) =>
  msg match {
    case RefereeSays(RefereeMessage.YourTurn, referee) =>
      referee ! PlayerSays(fizzBuzz(i + 1), context.self)
      fizzBuzzBehaviour(name, i)

    case RefereeSays(_, _) =>
      fizzBuzzBehaviour(name, i + 1)
  }
}