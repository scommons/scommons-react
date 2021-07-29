package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalajs.dom.raw.HTMLInputElement
import scommons.react._
import scommons.react.hooks._
import scommons.react.test.raw.TestInstance
import scommons.react.test.util.RendererUtilsSpec._
import scommons.react.test.util.TestRendererUtilsSpec._

import scala.scalajs.js

class TestRendererUtilsSpec extends RendererUtilsSpec[TestInstance]
  with TestRendererUtils {

  protected def doRender(element: ReactElement): TestInstance = testRender(element)

  it should "render mock reference" in {
    //given
    val comp = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        val elementRef = useRef[HTMLInputElement](null)

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
    assertNativeComponent(comp, <.p(^.className := "test1")(), { case List(child) =>
      child shouldBe "test1 child"
    })
  }

  it should "not fail if non-empty when assertTestComponent" in {
    //given
    val comp = testRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when
    assertNativeComponent(comp, <.div(^.className := "test2")(), { case List(comp1, _) =>
      assertTestComponent(comp1, TestComp) { props: Comp1Props =>
        props shouldBe Comp1Props(1)
      }
    })
  }

  it should "assert props and children when assertTestComponent" in {
    //given
    val comp = testRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when & then
    assertNativeComponent(comp, <.div(^.className := "test2")(), { case List(comp1, comp2) =>
      assertTestComponent(comp1, TestComp)({ props =>
        props shouldBe Comp1Props(1)
      }, { case List(child) =>
        assertNativeComponent(child, <.p(^.className := "test1")("test2 child1"))
      })

      assertTestComponent(comp2, TestComp)({ props =>
        props shouldBe Comp1Props(2)
      }, { case List(child) =>
        assertNativeComponent(child, <.p(^.className := "test1")("test2 child2"))
      })
    })
  }
}

object TestRendererUtilsSpec {

  def createHTMLInputElement(focusMock: () => Unit): HTMLInputElement = {
    js.Dynamic.literal(
      "focus" -> (focusMock: js.Function)
    ).asInstanceOf[HTMLInputElement]
  }
}
