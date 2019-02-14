package scommons.react.showcase

import scommons.react._
import scommons.react.test.TestSpec
import scommons.react.test.util.ShallowRendererUtils

import scala.scalajs.js

class FunctionComponentDemoSpec extends TestSpec with ShallowRendererUtils {

  it should "set function name" in {
    //given
    val compClass = FunctionComponentDemo()
    
    //when
    val result = compClass.asInstanceOf[js.Dynamic]
    
    //then
    result.name shouldBe "FunctionComponentDemo"
    result.toString shouldBe {
      "function FunctionComponentDemo(props){ return render(props) }"
    }
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
      <.div()(
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
