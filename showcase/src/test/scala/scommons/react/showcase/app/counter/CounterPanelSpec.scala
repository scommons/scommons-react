package scommons.react.showcase.app.counter

import org.scalajs.dom.document
import scommons.react._
import scommons.react.redux.task.FutureTask
import scommons.react.showcase.app.counter.CounterActions._
import scommons.react.test._
import scommons.react.test.dom._

import scala.concurrent.Future

class CounterPanelSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils {

  it should "dispatch CounterChangeAction when onClick on plus button" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)
    domRender(<(CounterPanel())(^.wrapped := props)())
    val button = document.body.querySelectorAll("button").item(0)
    val action = CounterChangeAction(
      FutureTask("Changing Counter", Future.successful(0))
    )
    
    //then
    (actions.changeCounter _).expects(dispatch, state.value, 1).returning(action)
    dispatch.expects(action)
    
    //when
    fireDomEvent(Simulate.click(button))
  }

  it should "dispatch CounterChangeAction when onClick on minus button" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)
    domRender(<(CounterPanel())(^.wrapped := props)())
    val button = document.body.querySelectorAll("button").item(1)
    val action = CounterChangeAction(
      FutureTask("Changing Counter", Future.successful(0))
    )
    
    //then
    (actions.changeCounter _).expects(dispatch, state.value, -1).returning(action)
    dispatch.expects(action)
    
    //when
    fireDomEvent(Simulate.click(button))
  }

  it should "render component" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)

    //when
    val result = shallowRender(<(CounterPanel())(^.wrapped := props)())

    //then
    assertNativeComponent(result,
      <.>()(
        <.p()(
          "Welcome to the React Counter showcase example App." +
            " Use buttons bellow to increase/decrease the counter:"
        ),
        <.p()(s"${props.state.value}"),

        <.button()("+"),
        <.button()("-")
      )
    )
  }
}
