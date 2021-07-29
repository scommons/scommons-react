package scommons.react.showcase.hooks

import org.scalajs.dom.raw.HTMLInputElement
import scommons.react.showcase.hooks.UseRefDemoSpec._
import scommons.react.test._

import scala.scalajs.js

class UseRefDemoSpec extends TestSpec with TestRendererUtils {
  
  it should "set focus to input element when onClick" in {
    //given
    val focusMock = mockFunction[Unit]
    val root = createTestRenderer(<(UseRefDemo())()(), { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) {
        createHTMLInputElement(focusMock)
      }
      else null
    }).root
    val button = inside(findComponents(root, <.button.name)) {
      case List(b) => b
    }

    //then
    focusMock.expects()

    //when
    button.props.onClick(null)
  }
  
  it should "render component" in {
    //given
    val comp = <(UseRefDemo())()()

    //when
    val result = createTestRenderer(comp).root

    //then
    inside(result.children.toList) { case List(input, button) =>
      assertNativeComponent(input, <.input(^.`type` := "text")())
      assertNativeComponent(button, <.button()("Focus the input"))
    }
  }
}

object UseRefDemoSpec {
  
  def createHTMLInputElement(focusMock: () => Unit): HTMLInputElement = {
    js.Dynamic.literal(
      "focus" -> (focusMock: js.Function)
    ).asInstanceOf[HTMLInputElement]
  }
}
