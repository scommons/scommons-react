package scommons.react.showcase

import scommons.react._
import scommons.react.test._

class ReactFragmentDemoSpec extends TestSpec with TestRendererUtils {

  it should "render component" in {
    //given
    val props = ReactFragmentDemoProps(List("test 1", "test 2"))
    val comp = <(ReactFragmentDemo())(^.wrapped := props)()

    //when
    val result = createTestRenderer(comp).root

    //then
    assertComponents(result.children, List(
      <.div()("Item #1"),
      <.div()("test 1"),
      <.div()("Item #2"),
      <.div()("test 2")
    ))
  }
  
  it should "render as child component" in {
    //given
    val props = ReactFragmentDemoProps(List("test"))
    val comp = <(ReactFragmentDemoSpec.Wrapper())(^.wrapped := props)()

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <(ReactFragmentDemo())(^.wrapped := props)(
        <.div()("Item #1"),
        <.div()("test")
      )
    )
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
