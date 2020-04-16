package scommons.react.showcase.hooks

import org.scalajs.dom.document
import scommons.react._
import scommons.react.test._
import scommons.react.test.dom._

class UseRefDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val comp = <(UseRefDemo())()()

    //when
    domRender(comp)

    //then
    assertDOMElement(domContainer, <.div()(
      <.input(^.`type` := "text")(),
      <.button()("Focus the input")
    ))
  }
  
  it should "set focus to input element when onClick" in {
    //given
    domRender(<(UseRefDemo())()())
    
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
    val comp = <(UseRefDemo())()()

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.>()(
        <.input(^.`type` := "text")(),
        <.button()("Focus the input")
      )
    )
  }
  
  it should "test render component" in {
    //given
    val comp = <(UseRefDemo())()()

    //when
    val result = createTestRenderer(comp).root

    //then
    result.children.size shouldBe 2
    assertNativeComponent(result.children(0),
      <.input(^.`type` := "text")()
    )
    assertNativeComponent(result.children(1),
      <.button()("Focus the input")
    )
  }
}
