package scommons.react.showcase

import scommons.react._
import scommons.react.test._

class ReactFragmentDemoSpec extends TestSpec with TestRendererUtils {

  it should "render component" in {
    //given
    val props = ReactFragmentDemoProps(List("test 1", "test 2"))
    val comp = <(ReactFragmentDemo())(^.wrapped := props)()

    //when
    val results = createTestRenderer(comp).root.children.toList

    //then
    inside(results) { case List(child1, child2, child3, child4) =>
      assertNativeComponent(child1, <.div()("Item #1"))
      assertNativeComponent(child2, <.div()("test 1"))
      assertNativeComponent(child3, <.div()("Item #2"))
      assertNativeComponent(child4, <.div()("test 2"))
    }
  }
  
  it should "render as child component" in {
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
