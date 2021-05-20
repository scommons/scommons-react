package scommons.react.showcase.app.counter

import scommons.react.redux.task.FutureTask
import scommons.react.showcase.app.counter.CounterActions._
import scommons.react.test._

import scala.concurrent.Future

class CounterPanelSpec extends TestSpec with TestRendererUtils {

  it should "dispatch CounterChangeAction when onClick on plus button" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)
    val root = createTestRenderer(<(CounterPanel())(^.wrapped := props)()).root
    val button = inside(findComponents(root, <.button.name)) {
      case List(b, _) => b
    }
    val action = CounterChangeAction(
      FutureTask("Changing Counter", Future.successful(0))
    )
    
    //then
    (actions.changeCounter _).expects(dispatch, state.value, 1).returning(action)
    dispatch.expects(action)
    
    //when
    button.props.onClick(null)
  }

  it should "dispatch CounterChangeAction when onClick on minus button" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)
    val root = createTestRenderer(<(CounterPanel())(^.wrapped := props)()).root
    val button = inside(findComponents(root, <.button.name)) {
      case List(_, b) => b
    }
    val action = CounterChangeAction(
      FutureTask("Changing Counter", Future.successful(0))
    )
    
    //then
    (actions.changeCounter _).expects(dispatch, state.value, -1).returning(action)
    dispatch.expects(action)
    
    //when
    button.props.onClick(null)
  }

  it should "render component" in {
    //given
    val dispatch = mockFunction[Any, Any]
    val actions = mock[CounterActions]
    val state = CounterState(123)
    val props = CounterPanelProps(dispatch, actions, state)

    //when
    val result = createTestRenderer(<(CounterPanel())(^.wrapped := props)()).root

    //then
    inside(result.children.toList) { case List(p1, p2, b1, b2) =>
      assertNativeComponent(p1, <.p()(
        "Welcome to the React Counter showcase example App." +
          " Use buttons bellow to increase/decrease the counter:"
      ))
      assertNativeComponent(p2, <.p()(s"${props.state.value}"))
      assertNativeComponent(b1, <.button()("+"))
      assertNativeComponent(b2, <.button()("-"))
    }
  }
}
