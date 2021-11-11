package scommons.react.test

import scommons.react.{FunctionComponent, ReactElement}

import scala.scalajs.js

class TestErrorBoundarySpec extends TestSpec with TestRendererUtils {

  it should "render children if no errors" in {
    //when
    val result = createTestRenderer(<(TestErrorBoundary())()(
      "some child"
    )).root

    //then
    inside(result.children.toList) { case List(child) =>
      child shouldBe "some child"
    }
  }

  it should "render error details if error during render" in {
    //given
    val errorComp = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        throw new IllegalArgumentException("test error")
      }
    }

    // suppress intended error
    // see: https://github.com/facebook/react/issues/11098#issuecomment-412682721
    val savedConsoleError = js.Dynamic.global.console.error
    js.Dynamic.global.console.error = { _: js.Any =>
    }

    //when
    val result = testRender(<(TestErrorBoundary())()(
      <(errorComp())()()
    ))

    //then
    js.Dynamic.global.console.error = savedConsoleError

    assertNativeComponent(result,
      <.div()(
        "java.lang.IllegalArgumentException: test error"
      )
    )
  }
}
