package scommons.react.showcase

import scommons.react._
import scommons.react.test._
import scommons.react.test.dom._

import scala.scalajs.js

class FunctionComponentDemoSpec extends TestSpec
  with TestDOMUtils
  with ShallowRendererUtils
  with TestRendererUtils {

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

  it should "render component in dom" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemoSpec.Wrapper())(^.wrapped := props)(
      "some child"
    )

    //when
    domRender(comp)

    //then
    assertDOMElement(domContainer.querySelector(".root"),
      <.div(^("class") := "root")(
        props.values.map { v =>
          <.div()(v)
        },
        "some child"
      )
    )
  }
  
  it should "shallow render as top component" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemo())(^.wrapped := props)(
      "some child"
    )

    //when
    val result = shallowRender(comp)

    //then
    assertNativeComponent(result,
      <.div(^.className := "root")(
        props.values.zipWithIndex.map { case (v, i) =>
          <.div(^.key := s"$i")(v)
        },
        "some child"
      )
    )
  }
  
  it should "shallow render as nested component" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemoSpec.Wrapper())(^.wrapped := props)()

    //when
    val result = shallowRender(comp)

    //then
    assertComponent(result, FunctionComponentDemo) { resProps =>
      resProps shouldBe props
    }
  }
  
  it should "shallow render as nested component with children" in {
    //given
    val props = FunctionComponentDemoProps(List("test"))
    val comp = <(FunctionComponentDemoSpec.Wrapper())(^.wrapped := props)(
      "some child"
    )

    //when
    val result = shallowRender(comp)

    //then
    assertComponent(result, FunctionComponentDemo)({ resProps =>
      resProps shouldBe props
    }, { case List(child) =>
      child shouldBe "some child"
    })
  }
  
  it should "test render component" in {
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
    val renderer = createRenderer()
    renderer.render(<(funcComp())(^.wrapped := props)())
    isRendered shouldBe true
    isRendered = false
    
    //when
    renderer.render(<(funcComp())(^.wrapped := props)())
    
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
