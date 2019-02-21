package scommons.react.showcase.hooks

import scommons.react._
import scommons.react.showcase.hooks.UseContextDemoApp._
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.raw.{ShallowInstance, TestRenderer}
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class UseContextDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val value1 = 123
    val value2 = ContextObj(456, "test")
    val app = new UseContextDemoApp(value1, value2)
    val child = new UseContextDemo(() => ())
    
    //when
    domRender(<(app())()(
      <(child())()()
    ))
    
    //then
    assertDOMElement(domContainer,
      <.div()(
        <.div()(s"#1: $value1, #2: $value2, #3: defValue")
      )
    )
  }

  it should "shallow render child component" in {
    //given
    val child = new UseContextDemo(() => ())

    //when
    val result = shallowRender(<(child())()())

    //then
    assertNativeComponent(result,
      <.div()("#1: 1, #2: ContextObj(2,user), #3: defValue")
    )
  }

  it should "shallow render app component" in {
    //given
    val value1 = 123
    val value2 = ContextObj(456, "test")
    val app = new UseContextDemoApp(value1, value2)
    val child = new UseContextDemo(() => ())

    //when
    val result = shallowRender(<(app())()(
      <(child())()()
    ))

    //then
    assertNativeComponent(result,
      <(Context1.Provider)(^.contextValue := value1)(), { children1: List[ShallowInstance] =>
        assertNativeComponent(children1.head,
          <(Context2.Provider)(^.contextValue := value2)(), { children2: List[ShallowInstance] =>
            assertComponent(children2.head, child)({ resProps =>
              resProps shouldBe ((): Unit)
            })
          }
        )
      }
    )
  }

  it should "test render component" in {
    //given
    val value1 = 123
    val value2 = ContextObj(456, "test")
    val app = new UseContextDemoApp(value1, value2)
    val child = new UseContextDemo(() => ())

    //when
    val result = testRender(<(app())()(
      <(child())()()
    ))

    //then
    assertTestComponent(result, child)({ resProps =>
      resProps shouldBe ((): Unit)
    }, { case List(resChild) =>
      assertNativeComponent(resChild,
        <.div()(s"#1: $value1, #2: $value2, #3: defValue")
      )
    })
  }
  
  it should "not re-render child component if context hasn't changed" in {
    //given
    var called = false
    val comp = new UseContextDemo({ () =>
      called = true
    })
    val value1 = 123
    val value2 = ContextObj(456, "test")
    val app = new UseContextDemoApp(value1, value2)
    val renderer = createTestRenderer(<(app())()(
      <(comp())()()
    ))
    called shouldBe true
    called = false
    val updatedApp = new UseContextDemoApp(value1, value2.copy(id = 456))

    //when
    TestRenderer.act { () =>
      renderer.update(<(updatedApp())()(
        <(comp())()()
      ))
    }

    //then
    called = false
  }
  
  it should "re-render child component if one of contexts has changed" in {
    //given
    var called = false
    val comp = new UseContextDemo({ () =>
      called = true
    })
    val value1 = 123
    val value2 = ContextObj(456, "test")
    val app = new UseContextDemoApp(value1, value2)
    val renderer = createTestRenderer(<(app())()(
      <(comp())()()
    ))
    called shouldBe true
    called = false
    val updatedApp = new UseContextDemoApp(321, value2)

    //when
    TestRenderer.act { () =>
      renderer.update(<(updatedApp())()(
        <(comp())()()
      ))
    }

    //then
    called = true
  }
}
