package scommons.react.test.raw

import scommons.react._
import scommons.react.test.TestSpec

class ShallowRendererSpec extends TestSpec {

  it should "test ShallowRenderer" in {
    //given
    val testComp = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.div(
          ^.className := "test-class",
          ^.style := Map("display" -> "none")
        )(
          <.div.empty,
          <.span()("Hello")
        )
      }
    }

    //when
    val renderer = new ShallowRenderer
    renderer.render(<(testComp())()())
    val result = renderer.getRenderOutput()

    //then
    result.`type` shouldBe "div"
    result.props.className shouldBe "test-class"
    result.props.style.display shouldBe "none"
    result.props.children.length shouldBe 2
  }
}
