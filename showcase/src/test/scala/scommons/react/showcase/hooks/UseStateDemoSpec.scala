package scommons.react.showcase.hooks

import scommons.react.test.TestSpec
import scommons.react.test.dom.raw.ReactTestUtils.Simulate
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class UseStateDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "increase counters when onClick" in {
    //given
    domRender(<(UseStateDemo())()())
    
    val p = domContainer.querySelector("p")
    p.textContent shouldBe "counter1: 0, counter2: 0"
    val button1 = domContainer.querySelector(".counter1")
    val button2 = domContainer.querySelector(".counter2")

    //when & then
    fireDomEvent(Simulate.click(button1))
    domContainer.querySelector("p").textContent shouldBe "counter1: 1, counter2: 0"
    
    //when & then
    fireDomEvent(Simulate.click(button2))
    fireDomEvent(Simulate.click(button1))
    domContainer.querySelector("p").textContent shouldBe "counter1: 2, counter2: 1"
  }
  
  it should "shallow render component" in {
    //given
    val comp = <(UseStateDemo())()()

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 0"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Increase counter2")
      )
    )
  }
  
  it should "test render component" in {
    //given
    val comp = <(UseStateDemo())()()

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 0"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Increase counter2")
      )
    )
  }
}
