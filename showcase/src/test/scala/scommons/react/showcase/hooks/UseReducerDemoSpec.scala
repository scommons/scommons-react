package scommons.react.showcase.hooks

import scommons.react.test._

class UseReducerDemoSpec extends TestSpec with TestRendererUtils {

  it should "increase/decrease counters when onClick" in {
    //given
    val root = createTestRenderer(<(UseReducerDemo())()()).root
    
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List("counter1: 0, counter2: 10")
    }
    val (button1, button2) = inside(findComponents(root, <.button.name)) {
      case List(b1, b2) => (b1, b2)
    }

    //when & then
    button1.props.onClick(null)
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List("counter1: 1, counter2: 10")
    }
    
    //when & then
    button2.props.onClick(null)
    button1.props.onClick(null)
    inside(findComponents(root, <.p.name)) {
      case List(p) => p.children.toList shouldBe List("counter1: 2, counter2: 9")
    }
  }
  
  it should "render component" in {
    //given
    val comp = <(UseReducerDemo())()()

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <.div()(
        <.p()("counter1: 0, counter2: 10"),
        <.button(^.className := "counter1")("Increase counter1"),
        <.button(^.className := "counter2")("Decrease counter2")
      )
    )
  }
}
