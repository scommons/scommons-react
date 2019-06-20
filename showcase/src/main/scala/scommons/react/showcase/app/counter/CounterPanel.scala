package scommons.react.showcase.app.counter

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import io.github.shogowada.scalajs.reactjs.redux.Redux.Dispatch
import scommons.react._

case class CounterPanelProps(dispatch: Dispatch,
                             actions: CounterActions,
                             state: CounterState)

object CounterPanel extends FunctionComponent[CounterPanelProps] {

  protected def render(compProps: Props): ReactElement = {
    val props = compProps.wrapped
    
    <.>()(
      <.p()(
        "Welcome to the React Counter showcase example App." +
          " Use buttons bellow to increase/decrease the counter:"
      ),
      
      <.p()(s"${props.state.value}"),
      
      <.button(^.onClick := { _: MouseSyntheticEvent =>
        props.dispatch(props.actions.changeCounter(props.dispatch, props.state.value, 1))
      })("+"),
      
      <.button(^.onClick := { _: MouseSyntheticEvent =>
        props.dispatch(props.actions.changeCounter(props.dispatch, props.state.value, -1))
      })("-")
    )
  }
}
