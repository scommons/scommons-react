package scommons.react.showcase

import org.scalajs.dom.document
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class ReactRefDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val comp = <(ReactRefDemo())()()

    //when
    domRender(comp)

    //then
    assertDOMElement(domContainer, <.div()(
      <.div()(
        <.input(^.`type` := "text")(),

        <.button()("Focus the text input")
      )
    ))
  }
  
  it should "set focus to input element when onClick" in {
    //given
    domRender(<(ReactRefDemo())()())
    
    val button = domContainer.querySelector("button")
    document.hasFocus() shouldBe false

    //when
    fireDomEvent(Simulate.click(button))

    //then
    domContainer.querySelector("input") shouldBe document.activeElement
    document.hasFocus() shouldBe true
  }
  
  it should "shallow render component" in {
    //given
    val comp = <(ReactRefDemo())()()

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.input(^.`type` := "text")(),

        <.button()("Focus the text input")
      )
    )
  }
  
  it should "test render component" in {
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
