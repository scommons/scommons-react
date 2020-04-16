package scommons.react.showcase

import scommons.react._
import scommons.react.test._
import scommons.react.test.dom._

import scala.scalajs.js

class ClassComponentDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {
  
  private type DemoProps = ClassComponentDemoProps
  private type DemoState = ClassComponentDemoState

  it should "render component in dom" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")

    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(*, *, *, *).never()
      (componentDidUpdate.apply _).expects(*, *, *, *).never()
      (componentWillUnmount.apply _).expects(*, *).never()
    }

    //when
    domRender(<(comp())(^.wrapped := props)("some child"))

    //then
    assertDOMElement(domContainer, <.div()(
      <.div()(
        s"${props.propValue}",
        s"initial: ${props.propValue}",
        "some child"
      )
    ))
  }
  
  it should "shallow render component" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")

    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(*, *).never()
      (shouldComponentUpdate.apply _).expects(*, *, *, *).never()
      (componentDidUpdate.apply _).expects(*, *, *, *).never()
      (componentWillUnmount.apply _).expects(*, *).never()
    }

    //when
    val result = shallowRender(<(comp())(^.wrapped := props)("some child"))

    //then
    assertNativeComponent(result,
      <.div()(
        s"${props.propValue}",
        s"initial: ${props.propValue}",
        "some child"
      )
    )
  }
  
  it should "test render component" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")

    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(*, *, *, *).never()
      (componentDidUpdate.apply _).expects(*, *, *, *).never()
      (componentWillUnmount.apply _).expects(*, *).never()
    }

    //when
    val result = testRender(<(comp())(^.wrapped := props)("some child"))

    //then
    assertNativeComponent(result,
      <.div()(
        s"${props.propValue}",
        s"initial: ${props.propValue}",
        "some child"
      )
    )
  }
  
  it should "update component when props change" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val newProps = props.copy(propValue = "updated")

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(props, state, newProps, state).returning(true)
      (rendered.apply _).expects(newProps, state)
      (componentDidUpdate.apply _).expects(newProps, state, props, state)
      (componentWillUnmount.apply _).expects(newProps, state)
    }

    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))
    
    //when
    renderer.update(<(comp())(^.wrapped := newProps)("some child"))
    renderer.unmount()
  }
  
  it should "update component when state change" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val newState = state.copy(stateValue = "updated")

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(newState)
      (shouldComponentUpdate.apply _).expects(props, state, props, newState).returning(true)
      (rendered.apply _).expects(props, newState)
      (componentDidUpdate.apply _).expects(props, newState, props, state)
      (shouldComponentUpdate.apply _).expects(props, newState, props, newState).returning(true)
      (rendered.apply _).expects(props, newState)
      (componentDidUpdate.apply _).expects(props, newState, props, newState)
      (componentWillUnmount.apply _).expects(props, newState)
    }

    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))
    
    //when
    renderer.update(<(comp())(^.wrapped := props)("some child"))
    renderer.unmount()
  }
  
  it should "update component when children change" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(props, state, props, state).returning(true)
      (rendered.apply _).expects(props, state)
      (componentDidUpdate.apply _).expects(props, state, props, state)
      (componentWillUnmount.apply _).expects(props, state)
    }

    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))
    
    //when
    renderer.update(<(comp())(^.wrapped := props)("some child2"))

    //then
    assertNativeComponent(renderer.root.children(0),
      <.div()(
        s"${props.propValue}",
        s"initial: ${props.propValue}",
        "some child2"
      )
    )

    //when
    renderer.unmount()
  }
  
  it should "re-render component if props hasn't changed when shouldComponentUpdate => true" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val sameProps = props.copy(propValue = props.propValue)

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(props, state, props, state).returning(true)
      (rendered.apply _).expects(props, state)
      (componentDidUpdate.apply _).expects(props, state, props, state)
      (componentWillUnmount.apply _).expects(sameProps, state)
    }
    
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))

    //when
    renderer.update(<(comp())(^.wrapped := sameProps)("some child"))
    renderer.unmount()
  }

  it should "re-render component if state hasn't changed when shouldComponentUpdate => true" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val sameState = state.copy(stateValue = state.stateValue)

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(sameState)
      (shouldComponentUpdate.apply _).expects(props, state, props, state).returning(true)
      (rendered.apply _).expects(props, state)
      (componentDidUpdate.apply _).expects(props, state, props, state)
      (componentWillUnmount.apply _).expects(props, sameState)
    }

    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))

    //when
    renderer.update(<(comp())(^.wrapped := props)("some child"))
    renderer.unmount()
  }

  it should "not re-render component if props hasn't changed when shouldComponentUpdate => false" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val sameProps = props.copy(propValue = props.propValue)

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(state)
      (shouldComponentUpdate.apply _).expects(props, state, props, state).returning(false)
      (rendered.apply _).expects(*, *).never()
      (componentDidUpdate.apply _).expects(*, *, *, *).never()
      (componentWillUnmount.apply _).expects(sameProps, state)
    }
    
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))

    //when
    renderer.update(<(comp())(^.wrapped := sameProps)("some child"))
    renderer.unmount()
  }

  it should "not re-render component if state hasn't changed when shouldComponentUpdate => false" in {
    //given
    val componentDidMount = mock[(DemoProps, DemoState) => ClassComponentDemoState]
    val shouldComponentUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Boolean]
    val componentDidUpdate = mock[(DemoProps, DemoState, DemoProps, DemoState) => Unit]
    val componentWillUnmount = mock[(DemoProps, DemoState) => Unit]
    val rendered = mock[(ClassComponentDemoProps, ClassComponentDemoState) => Unit]

    val props = ClassComponentDemoProps("test")
    val comp = new ClassComponentDemo(
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      rendered = rendered
    )
    val state = ClassComponentDemoState(s"initial: ${props.propValue}")
    val sameState = state.copy(stateValue = state.stateValue)

    //then
    inSequence {
      (rendered.apply _).expects(props, state)
      (componentDidMount.apply _).expects(props, state).returning(sameState)
      (shouldComponentUpdate.apply _).expects(props, state, props, state).returning(false)
      (rendered.apply _).expects(*, *).never()
      (componentDidUpdate.apply _).expects(*, *, *, *).never()
      (componentWillUnmount.apply _).expects(props, sameState)
    }

    val renderer = createTestRenderer(<(comp())(^.wrapped := props)("some child"))

    //when
    renderer.update(<(comp())(^.wrapped := props)("some child"))
    renderer.unmount()
  }

  it should "set component name" in {
    //given
    val comp = <(ClassComponentDemoSpec.TestComp())()()

    //when
    val result = createTestRenderer(comp).root

    //then
    result.`type`.toString shouldBe "ClassComponentDemoSpec$TestComp"
    result.`type`.asInstanceOf[js.Dynamic].displayName shouldBe "ClassComponentDemoSpec$TestComp"
  }
}

object ClassComponentDemoSpec {

  object TestComp extends ClassComponent[Unit] {

    protected def create(): ReactClass = createClass[Unit] { _ =>
      <.div()("test")
    }
  }
}
