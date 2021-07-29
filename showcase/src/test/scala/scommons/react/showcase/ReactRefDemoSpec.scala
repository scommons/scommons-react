package scommons.react.showcase

import org.scalajs.dom.raw.HTMLInputElement
import scommons.react.showcase.ReactRefDemoSpec._
import scommons.react.test._

import scala.scalajs.js

class ReactRefDemoSpec extends TestSpec with TestRendererUtils {
  
  it should "set focus to input element when onClick" in {
    //given
    val focusMock = mockFunction[Unit]
    val comp = testRender(<(ReactRefDemo())()(), { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) {
        createHTMLInputElement(focusMock)
      }
      else null
    })
    
    val button = inside(findComponents(comp, <.button.name)) {
      case List(btn) => btn
    }

    //then
    focusMock.expects()
    
    //when
    button.props.onClick(null)
  }
  
  it should "render component" in {
    //given
    val comp = <(ReactRefDemo())()()

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.input(^.`type` := "text")(),
        
        <.button()("Focus the text input")
      )
    )
  }
}

object ReactRefDemoSpec {

  def createHTMLInputElement(focusMock: () => Unit): HTMLInputElement = {
    js.Dynamic.literal(
      "focus" -> (focusMock: js.Function)
    ).asInstanceOf[HTMLInputElement]
  }
}
