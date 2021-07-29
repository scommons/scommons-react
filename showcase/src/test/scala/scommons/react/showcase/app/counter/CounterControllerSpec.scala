package scommons.react.showcase.app.counter

import io.github.shogowada.scalajs.reactjs.React.Props
import io.github.shogowada.scalajs.reactjs.redux.Redux.Dispatch
import scommons.react.showcase.app.ShowcaseState
import scommons.react.test.TestSpec

class CounterControllerSpec extends TestSpec {

  it should "return component" in {
    //given
    val actions = mock[CounterActions]
    val controller = new CounterController(actions)
    
    //when & then
    controller.uiComponent shouldBe CounterPanel
  }
  
  it should "map state to props" in {
    //given
    val actions = mock[CounterActions]
    val props = mock[Props[Unit]]
    val controller = new CounterController(actions)
    val dispatch = mock[Dispatch]
    val counterState = mock[CounterState]
    val state = ShowcaseState(None, counterState)

    //when
    val result = controller.mapStateToProps(dispatch, state, props)
    
    //then
    inside(result) { case CounterPanelProps(disp, resActions, resCounterState) =>
      disp shouldBe dispatch
      resActions shouldBe resActions
      resCounterState shouldBe counterState
    }
  }
}
