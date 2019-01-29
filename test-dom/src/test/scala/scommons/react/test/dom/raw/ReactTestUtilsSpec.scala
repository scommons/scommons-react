package scommons.react.test.dom.raw

import io.github.shogowada.scalajs.reactjs.React
import scommons.react.test.TestSpec
import scommons.react.test.dom.raw.ReactTestUtils._
import scommons.react.test.dom.raw.ReactTestUtilsSpec.TestProps

import scala.scalajs.js.JavaScriptException

class ReactTestUtilsSpec extends TestSpec {

  it should "test renderIntoDocument" in {
    //given
    val testProps = TestProps("this is a test")
    val testCompClass = React.createClass[TestProps, Unit] { (self) =>
      <.div()(
        s"Hello, ${self.props.wrapped.test}!"
      )
    }
    val testElem = React.createElement(testCompClass, React.wrap(testProps))

    //when
    val result = renderIntoDocument(testElem)

    //then
    result.props.wrapped shouldBe testProps
  }

  it should "test isElement" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()("Hello"))
    val testElem = React.createElement(testCompClass)
    val tree = renderIntoDocument(testElem)
    val div = findRenderedDOMComponentWithTag(tree, "div")

    //when & then
    isElement(testElem) shouldBe true

    //when & then
    isElement(div) shouldBe false
  }

  it should "test isElementOfType" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()("Hello"))
    val testCompClass2 = React.createClass[Unit, Unit](_ => <.div()("Hello2"))
    val testElem = React.createElement(testCompClass)

    //when & then
    isElementOfType(testElem, testCompClass) shouldBe true

    //when & then
    isElementOfType(testElem, testCompClass2) shouldBe false
  }

  it should "test isDOMComponent" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()("Hello"))
    val testElem = React.createElement(testCompClass)
    val tree = renderIntoDocument(testElem)
    val div = findRenderedDOMComponentWithTag(tree, "div")

    //when & then
    isDOMComponent(div) shouldBe true

    //when & then
    isDOMComponent(tree) shouldBe false
  }

  it should "test isCompositeComponent" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()("Hello"))
    val testElem = React.createElement(testCompClass)
    val tree = renderIntoDocument(testElem)
    val div = findRenderedDOMComponentWithTag(tree, "div")

    //when & then
    isCompositeComponent(tree) shouldBe true

    //when & then
    isCompositeComponent(div) shouldBe false
  }

  it should "test isCompositeComponentWithType" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()("Hello"))
    val testCompClass2 = React.createClass[Unit, Unit](_ => <.div()("Hello2"))
    val tree = renderIntoDocument(React.createElement(testCompClass))

    //when & then
    isCompositeComponentWithType(tree, testCompClass) shouldBe true

    //when & then
    isCompositeComponentWithType(tree, testCompClass2) shouldBe false
  }

  it should "test findAllInRenderedTree" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](_ => <.div()(<.span()("Hello")))
    val tree = renderIntoDocument(React.createElement(testCompClass))

    //when & then
    findAllInRenderedTree(tree, isDOMComponent(_)).length shouldBe 2

    //when & then
    findAllInRenderedTree(tree, isCompositeComponentWithType(_, testCompClass)).length shouldBe 1

    //when & then
    findAllInRenderedTree(tree, _ => false).length shouldBe 0
  }

  it should "test scryRenderedDOMComponentsWithClass" in {
    //given
    val tree = renderIntoDocument(React.createElement(React.createClass[Unit, Unit](_ =>
      <.div(^.className := "test")(
        <.div(^.className := "test2")(),
        <.span(^.className := "test")("Hello")
      )
    )))

    //when & then
    scryRenderedDOMComponentsWithClass(tree, "test").length shouldBe 2

    //when & then
    scryRenderedDOMComponentsWithClass(tree, "test2").length shouldBe 1

    //when & then
    scryRenderedDOMComponentsWithClass(tree, "test3").length shouldBe 0
  }

  it should "test findRenderedDOMComponentWithClass" in {
    //given
    val tree = renderIntoDocument(React.createElement(React.createClass[Unit, Unit](_ =>
      <.div(^.className := "test")(
        <.div(^.id := "2", ^.className := "test2")(),
        <.span(^.className := "test")("Hello")
      )
    )))

    //when & then
    findRenderedDOMComponentWithClass(tree, "test2").id shouldBe "2"

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedDOMComponentWithClass(tree, "test")
    }).getMessage should include("Error: Did not find exactly one match (found: 2) for class:test")

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedDOMComponentWithClass(tree, "test3")
    }).getMessage should include("Error: Did not find exactly one match (found: 0) for class:test3")
  }

  it should "test scryRenderedDOMComponentsWithTag" in {
    //given
    val tree = renderIntoDocument(React.createElement(React.createClass[Unit, Unit](_ =>
      <.div()(
        <.div.empty,
        <.span()("Hello")
      )
    )))

    //when & then
    scryRenderedDOMComponentsWithTag(tree, "div").length shouldBe 2

    //when & then
    scryRenderedDOMComponentsWithTag(tree, "span").length shouldBe 1

    //when & then
    scryRenderedDOMComponentsWithTag(tree, "p").length shouldBe 0
  }

  it should "test findRenderedDOMComponentWithTag" in {
    //given
    val testId = "test-id"
    val testClassName = "test-class"
    val testProps = TestProps("this is a test")
    val testCompClass = React.createClass[TestProps, Unit] { (self) =>
      <.div()("div 1",
        <.div()("div 2"),
        <.p(
          ^.id := testId,
          ^.className := testClassName)(
          s"Hello, ${self.props.wrapped.test}!"
        )
      )
    }
    val tree = renderIntoDocument(React.createElement(testCompClass, React.wrap(testProps)))

    //when & then
    val result = findRenderedDOMComponentWithTag(tree, "p")
    result.id shouldBe testId
    result.className shouldBe testClassName
    result.innerHTML shouldBe s"Hello, ${testProps.test}!"

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedDOMComponentWithTag(tree, "div")
    }).getMessage should include("Error: Did not find exactly one match (found: 2) for tag:div")

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedDOMComponentWithTag(tree, "span")
    }).getMessage should include("Error: Did not find exactly one match (found: 0) for tag:span")
  }

  it should "test scryRenderedComponentsWithType" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](self => <.div()(self.props.children))
    val testCompClass2 = React.createClass[Unit, Unit](_ => <.div()("Hello2"))
    val testCompClass3 = React.createClass[Unit, Unit](_ => <.div()("Hello3"))
    val tree = renderIntoDocument(React.createElement(React.createClass[Unit, Unit](_ =>
      <(testCompClass)()(
        <(testCompClass2).empty,
        <(testCompClass2).empty
      )
    )))

    //when & then
    scryRenderedComponentsWithType(tree, testCompClass2).length shouldBe 2

    //when & then
    scryRenderedComponentsWithType(tree, testCompClass).length shouldBe 1

    //when & then
    scryRenderedComponentsWithType(tree, testCompClass3).length shouldBe 0
  }

  it should "test findRenderedComponentWithType" in {
    //given
    val testCompClass = React.createClass[Unit, Unit](self => <.div()(self.props.children))
    val testCompClass2 = React.createClass[Unit, Unit](_ => <.div()("Hello2"))
    val testCompClass3 = React.createClass[Unit, Unit](_ => <.div()("Hello3"))
    val tree = renderIntoDocument(React.createElement(React.createClass[Unit, Unit](_ =>
      <(testCompClass)(^.id := "2")(
        <(testCompClass2).empty,
        <(testCompClass2).empty
      )
    )))

    //when & then
    findRenderedComponentWithType(tree, testCompClass).props.id shouldBe "2"

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedComponentWithType(tree, testCompClass2)
    }).getMessage should include("Error: Did not find exactly one match (found: 2) for componentType:function")

    //when & then
    (the [JavaScriptException] thrownBy {
      findRenderedComponentWithType(tree, testCompClass3)
    }).getMessage should include("Error: Did not find exactly one match (found: 0) for componentType:function")
  }
}

object ReactTestUtilsSpec {

  case class TestProps(test: String)
}
