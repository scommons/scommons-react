package scommons.react.showcase.app.counter

import scommons.react.showcase.app.counter.CounterActions._

case class CounterState(value: Int = 0)

object CounterStateReducer {

  def apply(state: Option[CounterState], action: Any): CounterState = {
    reduce(state.getOrElse(CounterState()), action)
  }

  private def reduce(state: CounterState, action: Any): CounterState = action match {
    case CounterChangedAction(counter) => state.copy(value = counter)
    case _ => state
  }
}
