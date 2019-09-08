package scommons.react.test.util

import io.github.shogowada.statictags.{Attribute, AttributeSpec}
import org.scalactic.source.Position
import org.scalatest.exceptions.TestFailedException
import org.scalatest.{Assertion, Failed, OutcomeOf}
import scommons.react._
import scommons.react.test.TestSpec
import scommons.react.test.raw.RenderedInstance
import scommons.react.test.util.RendererUtilsSpec._

import scala.scalajs.js

abstract class RendererUtilsSpec[Instance <: RenderedInstance] extends TestSpec
  with OutcomeOf {

  protected def doRender(element: ReactElement): Instance

  def findComponentProps[T](renderedComp: Instance, searchComp: UiComponent[T])(implicit pos: Position): T
  def findProps[T](renderedComp: Instance, searchComp: UiComponent[T]): List[T]
  def findComponents(component: Instance, componentType: Any): List[Instance]
  def assertNativeComponent(result: Instance, expectedElement: ReactElement)(implicit pos: Position): Assertion
  
  it should "fail if comp not found when findComponentProps" in {
    //given
    val comp = doRender(<(emptyComp)()())
    val searchComp = TestComp

    //when
    val e = the[TestFailedException] thrownBy {
      findComponentProps(comp, searchComp)
    }

    //then
    e.getMessage shouldBe s"UiComponent $searchComp not found"
  }

  it should "return props when findComponentProps" in {
    //given
    val comp = doRender(<(comp2Class)()())

    //when
    val result = findComponentProps(comp, TestComp)

    //then
    result shouldBe Comp1Props(1)
  }

  it should "return components props when findProps" in {
    //given
    val comp = doRender(<(comp2Class)()())

    //when
    val result = findProps(comp, TestComp)

    //then
    result shouldBe List(
      Comp1Props(1),
      Comp1Props(2)
    )
  }

  it should "not find component when findComponents" in {
    //given
    val comp = doRender(<(TestComp())(^.wrapped := Comp1Props(123))())

    //when & then
    findComponents(comp, TestComp()) shouldBe Nil
    findComponents(comp, comp2Class) shouldBe Nil
  }

  it should "find all components when findComponents" in {
    //given
    val comp = doRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when
    val results = findComponents(comp, TestComp())

    //then
    results.map(p => p.props.wrapped.asInstanceOf[Comp1Props]) shouldBe {
      List(Comp1Props(1), Comp1Props(2))
    }
  }

  it should "fail if array attribute doesn't match when assertNativeComponent" in {
    //given
    val compClass = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.p(^.testArr := js.Array("test"))()
      }
    }
    val comp = doRender(<(compClass())()())

    //when
    val Failed(e) = outcomeOf {
      assertNativeComponent(comp, <.p(^.testArr := js.Array("test2"))())
    }

    //then
    e.getMessage should include(
      "Attribute value doesn't match for p.testArr" +
        "\n\texpected: List(test2)" +
        "\n\tactual:   List(test)")
  }

  it should "fail if object attribute doesn't match when assertNativeComponent" in {
    //given
    val compClass = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.p(^.testObj := js.Dynamic.literal(
          test = 1
        ))()
      }
    }
    val comp = doRender(<(compClass())()())

    //when
    val Failed(e) = outcomeOf {
      assertNativeComponent(comp, <.p(^.testObj := js.Dynamic.literal(
        test = 2
      ))())
    }

    //then
    e.getMessage should include(
      "Attribute value doesn't match for p.testObj.test" +
        "\n\texpected: 2" +
        "\n\tactual:   1")
  }

  it should "fail if boolean attribute doesn't match when assertNativeComponent" in {
    //given
    val compClass = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.p(^.disabled := true)()
      }
    }
    val comp = doRender(<(compClass())()())

    //when
    val Failed(e) = outcomeOf {
      assertNativeComponent(comp, <.p(^.disabled := false)())
    }

    //then
    e.getMessage should include(
      "Attribute value doesn't match for p.disabled" +
        "\n\texpected: false" +
        "\n\tactual:   true")
  }

  it should "fail if child doesn't match when assertNativeComponent" in {
    //given
    val comp = doRender(<(TestComp())(^.wrapped := Comp1Props(1))("test1 child"))

    //when
    val Failed(e) = outcomeOf {
      assertNativeComponent(comp, <.p(^.className := "test1")("test2 child"))
    }

    //then
    e.getMessage should include ("""Child Element at index 0 doesn't match for p""")
  }

  it should "fail if non-empty when assertNativeComponent" in {
    //given
    val comp = doRender(<(TestComp())(^.wrapped := Comp1Props(1))("test1 child"))

    //when
    val Failed(e) = outcomeOf {
      assertNativeComponent(comp, <.p(^.className := "test1")())
    }

    //then
    e.getMessage should include ("""List("test1 child") was not empty : Expected no children""")
  }

  it should "assert props and children when assertNativeComponent" in {
    //given
    val id = System.currentTimeMillis().toString
    val compClass = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.div(
          ^.className := "test1 test2",
          ^.style := Map("display" -> "none"),
          ^.id := id,
          ^.hidden := true,
          ^.height := 10
        )(
          <.div(
            ^.testArr := js.Array("test"),
            ^.testObj := js.Dynamic.literal(
              test = 1,
              nested = js.Dynamic.literal(
                test2 = 2
              )
            )
          )(),
          <.div()("child1"),
          <.div()("child2")
        )
      }
    }
    val comp = doRender(<(compClass())()())

    //when & then
    assertNativeComponent(comp, <.div(
      ^.className := "test1 test2",
      ^.style := Map("display" -> "none"),
      ^.id := id,
      ^.hidden := true,
      ^.height := 10
    )(
      <.div(
        ^.testArr := js.Array("test"),
        ^.testObj := js.Dynamic.literal(
          test = 1,
          nested = js.Dynamic.literal(
            test2 = 2
          )
        )
      )(),
      <.div()("child1"),
      <.div()("child2")
    ))
  }
}

object RendererUtilsSpec {

  case class Comp1Props(a: Int)
  case class Comp2Props(b: Boolean)

  object TestComp extends FunctionComponent[Comp1Props] {
    protected def render(props: Props): ReactElement = {
      <.p(^.className := "test1")(props.children)
    }
  }

  val emptyComp: ReactClass = new FunctionComponent[Comp1Props] {
    protected def render(props: Props): ReactElement = {
      <.div.empty
    }
  }.apply()

  val comp2Class: ReactClass = new FunctionComponent[Comp2Props] {
    protected def render(props: Props): ReactElement = {
      <.div(^.className := "test2")(
        <(TestComp())(^.wrapped := Comp1Props(1))("test2 child1"),
        <(TestComp())(^.wrapped := Comp1Props(2))("test2 child2")
      )
    }
  }.apply()

  import io.github.shogowada.scalajs.reactjs.VirtualDOM.VirtualDOMAttributes
  import VirtualDOMAttributes.Type._

  case class TestArrayAttributeSpec(name: String) extends AttributeSpec {

    def :=(value: js.Array[String]): Attribute[js.Array[String]] = Attribute(name, value, AS_IS)
  }

  case class TestObjectAttributeSpec(name: String) extends AttributeSpec {

    def :=(value: js.Object): Attribute[js.Object] = Attribute(name, value, AS_IS)
  }

  implicit class TestVirtualDOMAttributes(attributes: VirtualDOMAttributes) {

    lazy val testArr = TestArrayAttributeSpec("testArr")
    lazy val testObj = TestObjectAttributeSpec("testObj")
  }
}
