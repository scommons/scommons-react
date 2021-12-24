package scommons.react.showcase

import scommons.react._
import scommons.react.test._

import scala.scalajs.js

class ErrorBoundaryDemoSpec extends TestSpec with TestRendererUtils {

  it should "render fallback UI" in {
    //given
    val failingChild = new ClassComponent[Unit] {
      protected def create(): ReactClass = createClass[Unit]( _ =>
        throw new Exception("test exception")
      )
    }

    // suppress intended error
    // see: https://github.com/facebook/react/issues/11098#issuecomment-412682721
    val savedConsoleError = js.Dynamic.global.console.error
    js.Dynamic.global.console.error = { _: js.Any =>
    }
    
    //when
    val result = testRender(<(ErrorBoundaryDemo())()(
      <(failingChild())()()
    ))
    
    //then
    js.Dynamic.global.console.error = savedConsoleError

    assertNativeComponent(result, <.div()(), inside(_) { case List(error, info) =>
      error shouldBe s"Error: java.lang.Exception: test exception"
      info.toString should startWith (
        """Info: 
          |        -> """.stripMargin
      )
    })
  }
  
  it should "render children" in {
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
