package cosc250.cop

/**
  * This is a little utility method I've made. It creates a PartialFunction that
  * writes out whatever it received.
  *
  * PartialFunctions can be composed together using "andThen". So if you want a method
  * that logs what it receives and then takes an action depending on what method it receives...
  *
  * log("MyActor") andThen { case ... }
  */
def log(name: String)(msg:Any) = {
  println(s"$name received $msg")
}


