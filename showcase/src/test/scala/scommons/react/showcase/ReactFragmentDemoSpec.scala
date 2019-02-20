package scommons.react.showcase

import scommons.react._
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils
import scommons.react.test.util.{ShallowRendererUtils, TestRendererUtils}

class ReactFragmentDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

  it should "render component in dom" in {
    //given
    val props = ReactFragmentDemoProps(List("test 1", "test 2"))
    val comp = <(ReactFragmentDemoSpec.Wrapper())(^.wrapped := props)()

    //when
    domRender(comp)

    //then
    assertDOMElement(domContainer,
      <.div()(
        <.div()("Item #1"),
        <.div()("test 1"),
        <.div()("Item #2"),
        <.div()("test 2")
      )
    )
  }
  
  it should "shallow render component" in {
    //given
    val props = ReactFragmentDemoProps(List("test"))
    val comp = <(ReactFragmentDemo())(^.wrapped := props)()

    //when
    val result = shallowRender(comp)

    //then
    result.`type` shouldBe raw.React.Fragment
    
    assertNativeComponent(result,
      <.>()(
        props.values.zipWithIndex.map { case (v, i) =>
          <.>(^.key := s"$i")(
            <.div()(s"Item #${i + 1}"),
            <.div()(v)
          )
        }
      )
    )
  }
  
  it should "test render component" in {
    //given
    val props = ReactFragmentDemoProps(List("test"))
    val comp = <(ReactFragmentDemoSpec.Wrapper())(^.wrapped := props)()

    //when
    val result = testRender(comp)

    //then
    assertTestComponent(result, ReactFragmentDemo)({ resProps =>
      resProps shouldBe props
    }, { case List(child1, child2) =>
      assertNativeComponent(child1, <.div()("Item #1"))
      assertNativeComponent(child2, <.div()("test"))
    })
  }
}

object ReactFragmentDemoSpec {

  object Wrapper extends FunctionComponent[ReactFragmentDemoProps] {

    protected def render(props: Props): ReactElement = {
      <(ReactFragmentDemo())(^.wrapped := props.wrapped)(
        props.children
      )
    }
  }
}
