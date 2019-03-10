package scommons.react.showcase.hooks

import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class UseMemoDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val comp = new UseMemoDemo(() => ())
    val props = UseMemoDemoProps(1, "2")
    
    //when
    domRender(<(comp())(^.wrapped := props)())
    
    //then
    val div = domContainer.querySelector("div")
    div.textContent shouldBe s"a: ${props.a}, b: ${props.b}"
  }
  
  it should "shallow render component" in {
    //given
    val comp = new UseMemoDemo(() => ())
    val props = UseMemoDemoProps(1, "2")

    //when
    val result = shallowRender(<(comp())(^.wrapped := props)())

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
    val renderer = createRenderer()
    renderer.render(<(comp())(^.wrapped := props)())
    called shouldBe true
    called = false

    //when
    renderer.render(<(comp())(^.wrapped := props)())

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
    val renderer = createRenderer()
    renderer.render(<(comp())(^.wrapped := props)())
    called shouldBe true
    called = false
    val newProps = props.copy(b = "3")

    //when
    renderer.render(<(comp())(^.wrapped := newProps)())

    //then
    called shouldBe true
    assertNativeComponent(renderer.getRenderOutput(),
      <.div()(s"a: ${newProps.a}, b: ${newProps.b}")
    )
  }
  
  it should "test render component" in {
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
}
