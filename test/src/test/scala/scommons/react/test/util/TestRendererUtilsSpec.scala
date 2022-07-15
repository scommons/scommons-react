package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalatest.exceptions.TestFailedException
import scommons.react._
import scommons.react.hooks._
import scommons.react.test.raw.TestInstance
import scommons.react.test.util.RendererUtilsSpec._
import scommons.react.test.util.TestRendererUtilsSpec._

import scala.scalajs.js

class TestRendererUtilsSpec extends RendererUtilsSpec[TestInstance]
  with TestRendererUtils {

  protected def doRender(element: ReactElement): TestInstance = testRender(element)

  TestComponent.childComp = mockUiComponent("SomeTestChild")

  it should "render mockUiComponent" in {
    //when
    val result = testRender(<(TestComponent())()())
    
    //then
    TestComponent.childComp.toString shouldBe "UiComponentMock(SomeTestChild)"
    
    assertTestComponent(result, TestComponent.childComp) { props =>
      props shouldBe ()
    }
  }

  it should "render mock reference" in {
    //given
    val comp = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        val elementRef = useRef[js.Dynamic](null)

        useLayoutEffect({ () =>
          elementRef.current.focus()
        }, Nil)
        
        <.input(
          ^.`type` := "text",
          ^.reactRef := elementRef
        )()
      }
    }
    val focusMock = mockFunction[Unit]

    //then
    focusMock.expects()

    //when
    testRender(<(comp())()(), { el =>
      if (el.`type` == "input".asInstanceOf[js.Any]) {
        createHTMLInputElement(focusMock)
      }
      else null
    })
  }

  it should "return top child instance when render" in {
    //when
    val comp = testRender(<(TestComp())(^.wrapped := Comp1Props(1))("test1 child"))

    //then
    assertNativeComponent(comp, <.p(^.className := "test1")(), inside(_) { case List(child) =>
      child shouldBe "test1 child"
    })
  }

  it should "not fail if non-empty when assertTestComponent" in {
    //given
    val comp = testRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when
    assertNativeComponent(comp, <.div(^.className := "test2")(), inside(_) { case List(comp1, _) =>
      assertTestComponent(comp1, TestComp) { props: Comp1Props =>
        props shouldBe Comp1Props(1)
      }
    })
  }

  it should "assert props and children when assertTestComponent" in {
    //given
    val comp = testRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when & then
    assertNativeComponent(comp, <.div(^.className := "test2")(), inside(_) { case List(comp1, comp2) =>
      assertTestComponent(comp1, TestComp)({ props =>
        props shouldBe Comp1Props(1)
      }, inside(_) { case List(child) =>
        assertNativeComponent(child, <.p(^.className := "test1")("test2 child1"))
      })

      assertTestComponent(comp2, TestComp)({ props =>
        props shouldBe Comp1Props(2)
      }, inside(_) { case List(child) =>
        assertNativeComponent(child, <.p(^.className := "test1")("test2 child2"))
      })
    })
  }

  it should "fail if wrong number of components when assertComponents" in {
    //given
    val comp = testRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when
    val e = the[TestFailedException] thrownBy {
      assertComponents(comp.children, List(
        <(TestComp())(^.wrapped := Comp1Props(1))(
          <.p(^.className := "test1")("test2 child1")
        )
      ))
    }

    //then
    e.getMessage shouldBe "Result components count(2) doesn't match expected count(1)"
  }

  it should "assert children when assertComponents" in {
    //given
    val result = createTestRenderer(<(comp2Class)(^.wrapped := Comp2Props(true))()).root

    //when & then
    assertComponents(result.children, List(
      <.div(^.className := "test2")(
        <(TestComp())(^.wrapped := Comp1Props(1))(
          <.p(^.className := "test1")("test2 child1")
        ),
        <(TestComp())(^.wrapped := Comp1Props(2))(
          <.p(^.className := "test1")("test2 child2")
        )
      )
    ))
  }
}

object TestRendererUtilsSpec {
  
  object TestComponent extends FunctionComponent[Unit] {
    
    private[util] var childComp: UiComponent[Unit] = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.>()()
      }
    }
    
    protected def render(props: TestComponent.Props): ReactElement = {
      <(childComp())()()
    }
  }

  def createHTMLInputElement(focusMock: () => Unit): js.Object = {
    js.Dynamic.literal(
      "focus" -> (focusMock: js.Function)
    )
  }
}
