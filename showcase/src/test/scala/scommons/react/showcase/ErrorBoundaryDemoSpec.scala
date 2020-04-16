package scommons.react.showcase

import org.scalajs.dom.{Event, window}
import scommons.react._
import scommons.react.test._
import scommons.react.test.dom._

import scala.scalajs.js

class ErrorBoundaryDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {
  
  it should "render component in dom" in {
    //when
    domRender(<(ErrorBoundaryDemo())()("some child"))

    //then
    assertDOMElement(domContainer, <.div()(
      "some child"
    ))
  }

  it should "render fallback UI in dom" in {
    //given
    val failingChild = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[Unit]( _ =>
        throw new Exception("test exception")
      )
    }

    // suppress intended error
    // see: https://github.com/facebook/react/issues/11098#issuecomment-412682721
    val onError: js.Function1[Event, Unit] = { e =>
      e.preventDefault()
    }
    window.addEventListener("error", onError)
    
    val savedConsoleError = js.Dynamic.global.console.error
    js.Dynamic.global.console.error = { _: js.Any =>
    }
    
    //when
    domRender(<(ErrorBoundaryDemo())()(
      <(failingChild())()()
    ))
    
    //then
    window.removeEventListener("error", onError)
    js.Dynamic.global.console.error = savedConsoleError

    assertDOMElement(domContainer, <.div()(
      <.div()(
        s"Error: java.lang.Exception: test exception",
        """Info: 
          |    in ErrorBoundaryDemoSpec$$anon$1
          |    in ErrorBoundaryDemo""".stripMargin
      )
    ))
  }
  
  it should "shallow render component" in {
    //given

    //when
    val result = shallowRender(<(ErrorBoundaryDemo())()("some child"))

    //then
    result shouldBe "some child"
  }
  
  it should "test render component" in {
    //given
    val comp = <(ErrorBoundaryDemo())()("some child")

    //when
    val result = createTestRenderer(comp).root

    //then
    result.children.toList shouldBe List("some child")
  }
  
  it should "set component name" in {
    //given
    val comp = <(ErrorBoundaryDemo())()("some child")

    //when
    val result = createTestRenderer(comp).root

    //then
    result.`type`.toString shouldBe "ErrorBoundaryDemo"
    result.`type`.asInstanceOf[js.Dynamic].displayName shouldBe "ErrorBoundaryDemo"
  }
}
