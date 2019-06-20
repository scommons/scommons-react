package scommons.react.showcase.app.counter

import io.github.shogowada.scalajs.reactjs.React.Props
import io.github.shogowada.scalajs.reactjs.redux.Redux.Dispatch
import scommons.react.UiComponent
import scommons.react.redux.BaseStateController
import scommons.react.showcase.app.ShowcaseStateDef

class CounterController(actions: CounterActions)
  extends BaseStateController[ShowcaseStateDef, CounterPanelProps] {

  lazy val uiComponent: UiComponent[CounterPanelProps] = CounterPanel

  def mapStateToProps(dispatch: Dispatch, state: ShowcaseStateDef, props: Props[Unit]): CounterPanelProps = {
    CounterPanelProps(dispatch, actions, state.counterState)
  }
}
