package scommons.react.test.dom.util

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import org.scalajs.dom.MouseEvent
import org.scalatest.{Failed, OutcomeOf}
import scommons.react.UiComponent
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtilsSpec._

import scala.scalajs.js.JavaScriptException

class TestDOMUtilsSpec extends TestSpec with TestDOMUtils
  with OutcomeOf {

  it should "fail if more than one comp when findRenderedComponentProps" in {
    //given
    val component = renderIntoDocument(<(comp2)()())

    //when
    val e = the[JavaScriptException] thrownBy {
      findRenderedComponentProps(component, TestComp)
    }

    //then
    e.getMessage() should include ("Error: Did not find exactly one match (found: 2)")
  }

  it should "return props when findRenderedComponentProps" in {
    //given
    val component = renderIntoDocument(<(comp1)()())

    //when
    val result = findRenderedComponentProps(component, TestComp)

    //then
    result shouldBe TestCompProps(3)
  }

  it should "return event object when createDomEvent" in {
    //given
    val typeArg = "mouseup"

    //when
    val result = createDomEvent[MouseEvent](typeArg)

    //then
    result.`type` shouldBe typeArg
  }

  it should "fail if classes don't match when assertDOMElement" in {
    //given
    val component = renderIntoDocument(<.div(^.className := "test1 test2")())

    //when
    val Failed(e) = outcomeOf {
      assertDOMElement(findReactElement(component),
        <.div(^("class") := "test")()
      )
    }

    //then
    e.getMessage should include ("classes don't match")
  }

  it should "fail if attribute keys don't match when assertDOMElement" in {
    //given
    val component = renderIntoDocument(<.div(^.className := "test")())

    //when
    val Failed(e) = outcomeOf {
      assertDOMElement(findReactElement(component),
        <.div(^.className := "test")()
      )
    }

    //then
    e.getMessage should include ("attribute keys don't match")
  }

  it should "fail if attribute value don't match when assertDOMElement" in {
    //given
    val component = renderIntoDocument(<.div(^.height := 5)())

    //when
    val Failed(e) = outcomeOf {
      assertDOMElement(findReactElement(component),
        <.div(^.height := 10)()
      )
    }

    //then
    e.getMessage should include ("attribute value don't match")
  }

  it should "fail if child tags don't match when assertDOMElement" in {
    //given
    val component = renderIntoDocument(<.div()())

    //when
    val Failed(e) = outcomeOf {
      assertDOMElement(findReactElement(component),
        <.div()(
          <.p.empty
        )
      )
    }

    //then
    e.getMessage should include ("child tags don't match")
  }

  it should "fail if text doesn't match when assertDOMElement" in {
    //given
    val component = renderIntoDocument(<.div()("test"))

    //when
    val Failed(e) = outcomeOf {
      assertDOMElement(findReactElement(component),
        <.div()("test2")
      )
    }

    //then
    e.getMessage should include ("text doesn't match")
  }

  it should "assert props and children when assertDOMElement" in {
    //given
    val id = System.currentTimeMillis().toString
    val compClass = React.createClass[Unit, Unit] { _ =>
      <.div(
        ^.className := "test1 test2",
        ^.style := Map("display" -> "none"),
        ^.id := id,
        ^.hidden := true,
        ^.height := 10
      )(
        <.div()("child1"),
        <.div()("child2")
      )
    }
    val component = <(compClass)()()
    val result = renderIntoDocument(component)

    //when & then
    assertDOMElement(findReactElement(result),
      <.div(
        ^("class") := "test1 test2",
        ^("style") := "display: none;",
        ^.id := id,
        ^("hidden") := "",
        ^.height := 10
      )(
        <.div()("child1"),
        <.div()("child2")
      )
    )
  }
}

object TestDOMUtilsSpec {

  private val comp1 = React.createClass[TestCompProps, Unit] { _ =>
    <.div()(
      <(TestComp())(^.wrapped := TestCompProps(3))()
    )
  }

  private val comp2 = React.createClass[TestCompProps, Unit] { _ =>
    <.div()(
      <(TestComp())(^.wrapped := TestCompProps(1))(),
      <(TestComp())(^.wrapped := TestCompProps(2))()
    )
  }

  case class TestCompProps(test: Int)

  object TestComp extends UiComponent[TestCompProps] {

    protected def create(): ReactClass = React.createClass[PropsType, Unit] { self =>
      val props = self.props.wrapped
      <.div()(s"test: ${props.test}")
    }
  }
}
