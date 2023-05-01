package cosc250.cop.functional

import com.wbillingsley.amdram.*
import scala.concurrent.ExecutionContext.Implicits.global

@main def typedApp() = {

  val troupe = SingleEcTroupe()
  println("Created the actor system")

  val algernon = troupe.spawn(fizzBuzzBehaviour("Algernon", 0))
  val bertie = troupe.spawn(fizzBuzzBehaviour("Bertie", 0))
  val ref = troupe.spawn(referee(RefereeState(0, 100, List(algernon, bertie))))

  ref ! Go

  try {
    Thread.sleep(1000)
  } catch {
    case _ => 
      // wake and exit
  }

}
