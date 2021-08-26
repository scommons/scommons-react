package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import scommons.react._
import scommons.react.dom._
import scommons.react.hooks._

object UseReducerDemo extends FunctionComponent[Unit] {

  private case class DemoState(count: Int)
  
  sealed trait DemoAction
  case object Increment extends DemoAction
  case object Decrement extends DemoAction
  
  private def reducer(state: DemoState, action: DemoAction): DemoState = action match {
    case Increment => state.copy(count = state.count + 1)
    case Decrement => state.copy(count = state.count - 1)
  }
  
  protected def render(props: Props): ReactElement = {
    val (state1, dispatch1) = useReducer(reducer, DemoState(0))
    val (state2, dispatch2) = useReducer(reducer, DemoState(10))

    <.div()(
      <.p()(s"counter1: ${state1.count}, counter2: ${state2.count}"),
      
      <.button(^.className := "counter1", ^.onClick := { (_: MouseSyntheticEvent) =>
        dispatch1(Increment)
      })("Increase counter1"),
      
      <.button(^.className := "counter2", ^.onClick := { (_: MouseSyntheticEvent) =>
        dispatch2(Decrement)
      })("Decrease counter2")
    )
  }
}
