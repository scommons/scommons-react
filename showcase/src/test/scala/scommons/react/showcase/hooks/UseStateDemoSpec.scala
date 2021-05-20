package scommons.react.showcase.hooks

import scommons.react.test._

class UseStateDemoSpec extends TestSpec with TestRendererUtils {

  it should "increase counters when onClick" in {
    //given
    val root = createTestRenderer(<(UseStateDemo())()()).root
    
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List(
        "counter1: 0, counter2: 1, counter3: 2, counter4: 3"
      )
    }
    val (button1, button2, button3, button4) = inside(findComponents(root, <.button.name)) {
      case List(b1, b2, b3, b4) => (b1, b2, b3, b4)
    }

    //when & then
    button1.props.onClick(null)
    button2.props.onClick(null)
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List(
        "counter1: 1, counter2: 2, counter3: 2, counter4: 3"
      )
    }
    
    //when & then
    button4.props.onClick(null)
    button3.props.onClick(null)
    button1.props.onClick(null)
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List(
        "counter1: 2, counter2: 2, counter3: 3, counter4: 4"
      )
    }
  }
  
  it should "render component" in {
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
