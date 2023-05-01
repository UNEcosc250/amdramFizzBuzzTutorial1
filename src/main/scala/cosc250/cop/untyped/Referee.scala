package cosc250.cop.untyped

import com.wbillingsley.amdram.*

import cosc250.cop._
import Word._

case class Game(players:List[Recipient[Any]], rounds:Int)

class Referee() extends MessageHandler[Any]:

  /* Who'se playing */
  private var game:Game = Game(Nil, 0)

  /* Game state */
  private var round = 0
  private var togo:List[Recipient[Any]] = Nil
  private var out:Set[Recipient[Any]] = Set.empty
  private var i = 0

  /** Whether a player is still "in" */
  def isIn(p:Recipient[Any]) = !out.contains(p)

  /** The players who aren't "out" */
  def in = game.players.filter(isIn)

  /** The player who is currently supposed to be responding to a message */
  def whoseTurn = togo.head

  /** Challenge the next player */
  def challenge()(using context:ActorContext[Any]) =
    i = i + 1
    if togo.nonEmpty then
      whoseTurn ! (RefereeMessage.YourTurn, context.self) 
    else {
      println("Referee: Game over")
      System.exit(0)
    }

  /** Move to the next player, incrementing the round if necessary */
  def moveToNextPlayer() = {
    if togo.tail.nonEmpty then {
      // Move to the next player who's still in
      togo = togo.tail.filter(isIn)
    } else
      round += 1
      if round < game.rounds then
        togo = game.players.filter(isIn)
      else
        togo = Nil
  }


  def fizzBuzz(i:Int):FizzBuzzMessage =
    if i % 15 == 0 then FizzBuzz
    else if i % 5 == 0 then Buzz
    else if i % 3 == 0 then Fizz
    else i

  override def receive(message:Any)(using context:ActorContext[Any]) = {
    log("Referee")(message) 
    message match {
      case Game(players, rounds) =>
        println("Referee: Setting up game")
        this.game = Game(players, rounds)
        round = 0
        togo = players
        i = 0

        // Start the game!
        challenge()

      case (m: FizzBuzzMessage, sender:Recipient[Any] @unchecked) =>

        // Send the message to all players
        for p <- game.players if !out.contains(p) do p ! m

        if sender == whoseTurn then
          if m == fizzBuzz(i) then {
            moveToNextPlayer()
            challenge()
          } else {
            out += sender
            println(s"NOTICE: ${sender} is out. Players still in: ${in}")
            sender ! RefereeMessage.Wrong(m, fizzBuzz(i))

            moveToNextPlayer()
            challenge()
          }
    }
  }




