package scommons.react.showcase.hooks

import scommons.react.test.TestSpec
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
    p.textContent shouldBe {
      "counter1: 0, counter2: 1, counter3: 2, counter4: 3"
    }
    val button1 = domContainer.querySelector(".counter1")
    val button2 = domContainer.querySelector(".counter2")
    val button3 = domContainer.querySelector(".counter3")
    val button4 = domContainer.querySelector(".counter4")

    //when & then
    fireDomEvent(Simulate.click(button1))
    fireDomEvent(Simulate.click(button2))
    domContainer.querySelector("p").textContent shouldBe {
      "counter1: 1, counter2: 2, counter3: 2, counter4: 3"
    }
    
    //when & then
    fireDomEvent(Simulate.click(button4))
    fireDomEvent(Simulate.click(button3))
    fireDomEvent(Simulate.click(button1))
    domContainer.querySelector("p").textContent shouldBe {
      "counter1: 2, counter2: 2, counter3: 3, counter4: 4"
    }
  }
  
  it should "shallow render component" in {
    //given
    val comp = <(UseStateDemo())()()

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 1, counter3: 2, counter4: 3"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Increase counter2"),
        <.button(^.className := "counter3")("Increase counter3"),
        <.button(^.className := "counter4")("Increase counter4")
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
        <.p()("counter1: 0, counter2: 1, counter3: 2, counter4: 3"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Increase counter2"),
        <.button(^.className := "counter3")("Increase counter3"),
        <.button(^.className := "counter4")("Increase counter4")
      )
    )
  }
}
