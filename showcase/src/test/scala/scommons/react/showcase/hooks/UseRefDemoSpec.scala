package scommons.react.showcase.hooks

import scommons.react.showcase.hooks.UseRefDemoSpec.MouseSyntheticEventMock
import scommons.react.test._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

class UseRefDemoSpec extends TestSpec with TestRendererUtils {
  
  it should "set focus to input element when onClick" in {
    //given
    val inputMock = mock[MouseSyntheticEventMock]
    val root = createTestRenderer(<(UseRefDemo())()(), { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) inputMock.asInstanceOf[js.Any]
      else null
    }).root
    val button = inside(findComponents(root, <.button.name)) {
      case List(b) => b
    }

    //then
    (inputMock.focus _).expects()

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
  
  @JSExportAll
  trait MouseSyntheticEventMock {

    def focus(): Unit
  }
}
