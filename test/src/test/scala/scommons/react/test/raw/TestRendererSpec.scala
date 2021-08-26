package scommons.react.test.raw

import scommons.react._
import scommons.react.test.TestSpec
import scommons.react.test.raw.TestRendererSpec._

import scala.scalajs.js

class TestRendererSpec extends TestSpec {

  it should "mock reference" in {
    //given
    val comp = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[ReactRef[js.Dynamic]](
        getInitialState = { _ =>
          ReactRef.create[js.Dynamic]
        },
        componentDidMount = { self =>
          self.state.current.focus()
        },
        render = { self =>
          <.input(
            ^.`type` := "text",
            ^.reactRef := self.state
          )()
        }
      )
    }
    val focusMock = mockFunction[Unit]
    val compMock: js.Function1[TestInstance, js.Any] = { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) {
        createHTMLInputElement(focusMock)
      }
      else null
    }
    
    //then
    focusMock.expects()

    //when
    TestRenderer.create(<(comp())()(), new TestRendererOptions {
      override val createNodeMock = compMock
    })
  }
  
  it should "mount component" in {
    //given
    var isMounted = false
    val comp = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[Unit](
        componentDidMount = { _ =>
          isMounted = true
        },
        render = { _ =>
          <.div(^("testProp") := "test")(
            <.span()("Test")
          )
        }
      )
    }
    
    //when
    val result = TestRenderer.create(<(comp())()(), null).root
    
    //then
    isMounted shouldBe true
    result.`type` shouldBe comp()
    
    val container = result.children(0)
    container.`type` shouldBe "div"
    container.props.testProp shouldBe "test"
  }
  
  it should "update component" in {
    //given
    var isUpdated = false
    val comp = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[Unit](
        componentDidUpdate = { (_, _, _) =>
          isUpdated = true
        },
        render = { self =>
          <.div(^("testProp") := self.props.native.test.asInstanceOf[String])(
            <.span()("Test")
          )
        }
      )
    }
    val renderer = TestRenderer.create(<(comp())(^("test") := "test")(), null)
    isUpdated shouldBe false
    
    //when
    renderer.update(<(comp())(^("test") := "updated")())
    
    //then
    val result = renderer.root
    isUpdated shouldBe true
    result.`type` shouldBe comp()
    
    val container = result.children(0)
    container.`type` shouldBe "div"
    container.props.testProp shouldBe "updated"
  }
  
  it should "unmount component" in {
    //given
    var isUnmounted = false
    val comp = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[Unit](
        componentWillUnmount = { _ =>
          isUnmounted = true
        },
        render = { _ =>
          <.div(^("testProp") := "test")(
            <.span()("Test")
          )
        }
      )
    }
    val renderer = TestRenderer.create(<(comp())()(), null)
    isUnmounted shouldBe false
    
    //when
    renderer.unmount()
    
    //then
    isUnmounted shouldBe true
  }
}

object TestRendererSpec {

  def createHTMLInputElement(focusMock: () => Unit): js.Object = {
    js.Dynamic.literal(
      "focus" -> (focusMock: js.Function)
    )
  }
}
