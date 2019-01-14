package scommons.react.test.raw

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import scommons.react.test.TestSpec

class ShallowRendererSpec extends TestSpec {

  it should "test ShallowRenderer" in {
    //given
    val testElem = React.createElement(React.createClass[Unit, Unit](_ =>
      <.div(
        ^.className := "test-class",
        ^.style := Map("display" -> "none")
      )(
        <.div.empty,
        <.span()("Hello")
      )
    ))

    //when
    val result = ShallowRenderer.renderAndGetOutput(testElem)

    //then
    result.`type` shouldBe "div"
    result.props.className shouldBe "test-class"
    result.props.style.display shouldBe "none"
    result.props.children.length shouldBe 2
  }
}
