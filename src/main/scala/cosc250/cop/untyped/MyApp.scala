package cosc250.cop.untyped

import java.util.concurrent.TimeoutException
import com.wbillingsley.amdram.*

import scala.concurrent.ExecutionContext.Implicits.global
import cosc250.cop._

@main def app() = {

  // Create the actor system
  val troupe = SingleEcTroupe()
  println("Created the actor system")

  val referee = troupe.spawn(Referee())

  // Create three of your players
  val algernon = troupe.spawn(FizzBuzzActor("Algernon"))
  val bertie = troupe.spawn(FizzBuzzActor("Bertie"))
  val cecily = troupe.spawn(FizzBuzzActor("Cecily"))

  // Create a terrible player
  val hello = troupe.spawn(Terrible())

  // Give the list of players to the referee to start the game
  referee ! Game(List(algernon, bertie, cecily, hello), 5)

  // Keep the main thread awake for 1 second before it exits
  try {
    Thread.sleep(1000)
  } catch {
    case _ => 
      // wake and exit
  }

}

