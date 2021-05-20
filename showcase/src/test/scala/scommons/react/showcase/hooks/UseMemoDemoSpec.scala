package scommons.react.showcase.hooks

import scommons.react.test._

class UseMemoDemoSpec extends TestSpec with TestRendererUtils {

  it should "render component" in {
    //given
    val comp = new UseMemoDemo(() => ())
    val props = UseMemoDemoProps(1, "2")

    //when
    val result = testRender(<(comp())(^.wrapped := props)())

    //then
    assertNativeComponent(result,
      <.div()(s"a: ${props.a}, b: ${props.b}")
    )
  }
  
  it should "not call memo callback func if inputs haven't changed" in {
    //given
    var called = false
    val comp = new UseMemoDemo({ () =>
      called = true
    })
    val props = UseMemoDemoProps(1, "2")
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    called shouldBe true
    called = false

    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := props)())
    }

    //then
    called shouldBe false
  }
  
  it should "call memo callback func if one of inputs has changed" in {
    //given
    var called = false
    val comp = new UseMemoDemo({ () =>
      called = true
    })
    val props = UseMemoDemoProps(1, "2")
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    called shouldBe true
    called = false
    val newProps = props.copy(b = "3")

    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := newProps)())
    }

    //then
    called shouldBe true
    assertNativeComponent(renderer.root.children(0),
      <.div()(s"a: ${newProps.a}, b: ${newProps.b}")
    )
  }
}
