package scommons.react.showcase

import scommons.react._
import scommons.react.test._

import scala.scalajs.js

class FunctionComponentDemoSpec extends TestSpec with TestRendererUtils {

  it should "set function name" in {
    //given
    val compClass = FunctionComponentDemo()
    
    //when
    val result = compClass.asInstanceOf[js.Dynamic]
    
    //then
    result.name shouldBe "FunctionComponentDemo"
    result.displayName shouldBe "FunctionComponentDemo"
    result.toString shouldBe {
      "function FunctionComponentDemo(props){ return render(props) }"
    }
  }

  it should "render as top component" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemo())(^.wrapped := props)(
      "some child"
    )

    //when
    val result = testRender(comp)

    //then
    assertNativeComponent(result,
      <.div(^.className := "root")(
        props.values.zipWithIndex.map { case (v, i) =>
          <.div()(v)
        },
        "some child"
      )
    )
  }
  
  it should "render as nested component" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemoSpec.Wrapper())(^.wrapped := props)()

    //when
    val result = testRender(comp)

    //then
    assertTestComponent(result, FunctionComponentDemo) { resProps =>
      resProps shouldBe props
    }
  }
  
  it should "render as nested component with children" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemoSpec.Wrapper())(^.wrapped := props)(
      "some child"
    )

    //when
    val result = testRender(comp)

    //then
    assertTestComponent(result, FunctionComponentDemo)({ resProps =>
      resProps shouldBe props
    }, { case List(child) =>
      assertNativeComponent(child,
        <.div(^.className := "root")(
          props.values.map { v =>
            <.div()(v)
          },
          "some child"
        )
      )
    })
  }
  
  it should "re-render component even if props hasn't changed" in {
    //given
    var isRendered = false
    val funcComp = new FunctionComponent[FunctionComponentDemoProps] {
      protected def render(props: Props): ReactElement = {
        isRendered = true
        <.div.empty
      }
    }
    val props = FunctionComponentDemoProps(List("test"))
    val renderer = createTestRenderer(<(funcComp())(^.wrapped := props)())
    isRendered shouldBe true
    isRendered = false
    
    //when
    TestRenderer.act { () =>
      renderer.update(<(funcComp())(^.wrapped := props)())
    }
    
    //then
    isRendered shouldBe true
  }
}

object FunctionComponentDemoSpec {

  object Wrapper extends FunctionComponent[FunctionComponentDemoProps] {

    protected def render(props: Props): ReactElement = {
      <(FunctionComponentDemo())(^.wrapped := props.wrapped)(
        props.children
      )
    }
  }
}
