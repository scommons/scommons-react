package scommons.react.showcase.app.counter

import scommons.react.redux._
import scommons.react.redux.task.{FutureTask, TaskAction}
import scommons.react.showcase.app.counter.CounterActions._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

class CounterActions {

  def changeCounter(dispatch: Dispatch, counter: Int, dx: Int): CounterChangeAction = {
    
    // in real App may call some API here
    val future = Future.successful(counter + dx).andThen {
      case Success(result) => dispatch(CounterChangedAction(result))
      
      // Errors should be handled generally by the UI Task Manager
      //case Failure(e) => onError(())(e)
    }

    CounterChangeAction(FutureTask("Changing Counter", future))
  }
}

object CounterActions {

  /** Asynchronous, potentially calling some API, actions should extend [[TaskAction]],
    * so the UI Task Manager will be able to display status/loading
    */
  case class CounterChangeAction(task: FutureTask[Int]) extends TaskAction

  /** Simple actions just extend redux Action
    */
  case class CounterChangedAction(counter: Int) extends Action
}
