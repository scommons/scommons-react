package scommons.react.showcase

import scommons.react.showcase.ReactRefDemoSpec._
import scommons.react.test._

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportAll

class ReactRefDemoSpec extends TestSpec with TestRendererUtils {
  
  it should "set focus to input element when onClick" in {
    //given
    val inputMock = mock[HTMLInputElementMock]
    val comp = testRender(<(ReactRefDemo())()(), { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) inputMock.asInstanceOf[js.Any]
      else null
    })
    
    val button = inside(findComponents(comp, <.button.name)) {
      case List(btn) => btn
    }

    //then
    (inputMock.focus _).expects()
    
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

  @JSExportAll
  trait HTMLInputElementMock {

    def focus(): Unit
  }
}
