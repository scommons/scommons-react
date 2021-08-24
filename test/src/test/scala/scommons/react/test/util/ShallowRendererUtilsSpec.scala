package scommons.react.test.util

import org.scalatest.Failed
import scommons.react._
import scommons.react.test.raw.ShallowInstance
import scommons.react.test.util.RendererUtilsSpec._

@deprecated("Will be removed soon, use TestRendererUtils", "0.5.1")
class ShallowRendererUtilsSpec extends RendererUtilsSpec[ShallowInstance]
  with ShallowRendererUtils {

  protected def doRender(element: ReactElement): ShallowInstance = shallowRender(element)

  it should "return renderer when createRenderer" in {
    //when
    val renderer = createRenderer()

    //then
    renderer.render(<(TestComp())(^.wrapped := Comp1Props(1))("test1 child"))

    assertNativeComponent(renderer.getRenderOutput(), <.p(^.className := "test1")(), { case List(child) =>
      child shouldBe "test1 child"
    })
  }

  it should "fail if non-empty when assertComponent" in {
    //given
    val comp = shallowRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when
    assertNativeComponent(comp, <.div(^.className := "test2")(), { case List(comp1, _) =>
      val e = inside(outcomeOf {
        assertComponent(comp1, TestComp) { props: Comp1Props =>
          props shouldBe Comp1Props(1)
        }
      }) {
        case Failed(e) => e
      }

      //then
      e.getMessage should include ("""List("test2 child1") was not empty : Expected no children""")
    })
  }

  it should "assert props and children when assertComponent" in {
    //given
    val comp = shallowRender(<(comp2Class)(^.wrapped := Comp2Props(true))())

    //when & then
    assertNativeComponent(comp, <.div(^.className := "test2")(), { case List(comp1, comp2) =>
      assertComponent(comp1, TestComp)({ props =>
        props shouldBe Comp1Props(1)
      }, { case List(child) =>
        child shouldBe "test2 child1"
      })

      assertComponent(comp2, TestComp)({ props =>
        props shouldBe Comp1Props(2)
      }, { case List(child) =>
        child shouldBe "test2 child2"
      })
    })
  }

  it should "fail if key attribute doesn't match when assertNativeComponent" in {
    //given
    val compClass = new FunctionComponent[Unit] {
      protected def render(props: Props): ReactElement = {
        <.p(^.key := "123")()
      }
    }
    val comp = shallowRender(<(compClass())()())

    //when
    val e = inside(outcomeOf {
      assertNativeComponent(comp, <.p(^.key := "12345")())
    }) {
      case Failed(e) => e
    }

    //then
    e.getMessage should include(
      "Attribute value doesn't match for p.key" +
        "\n\texpected: 12345" +
        "\n\tactual:   123")
  }
}
