package scommons.react.showcase.app

import scommons.react.redux.task.{AbstractTask, TaskReducer}
import scommons.react.showcase.app.counter.{CounterState, CounterStateReducer}

trait ShowcaseStateDef {

  def currentTask: Option[AbstractTask]
  def counterState: CounterState
}

case class ShowcaseState(currentTask: Option[AbstractTask],
                         counterState: CounterState) extends ShowcaseStateDef

object ShowcaseStateReducer {

  def reduce(state: Option[ShowcaseState], action: Any): ShowcaseState = ShowcaseState(
    currentTask = TaskReducer(state.flatMap(_.currentTask), action),
    counterState = CounterStateReducer(state.map(_.counterState), action)
  )
}
