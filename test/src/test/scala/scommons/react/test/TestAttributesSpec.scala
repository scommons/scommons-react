package scommons.react.test

import scommons.react._
import scommons.react.test.TestAttributesSpec._

class TestAttributesSpec extends TestSpec with TestRendererUtils {

  TestAttributesSpec.someChildComp = mockUiComponent("TestChild")

  it should "render and assertWrapped" in {
    //when
    val result = testRender(<(TestComponent())()())

    //then
    assertNativeComponent(result,
      <.div()(
        <(someChildComp())(^.assertWrapped(inside(_) {
          case TestComponentProps("test name", age) =>
            age shouldBe 123
        }))()
      )
    )
  }
}

object TestAttributesSpec {

  case class TestComponentProps(name: String, age: Int)

  private var someChildComp: UiComponent[TestComponentProps] = TestComponent
  
  object TestComponent extends FunctionComponent[TestComponentProps] {
    
    protected def render(props: Props): ReactElement = {
      <.div()(
        <(someChildComp())(^.wrapped := TestComponentProps("test name", 123))()
      )
    }
  } 
}
