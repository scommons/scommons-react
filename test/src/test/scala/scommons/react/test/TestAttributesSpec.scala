package scommons.react.test

import scommons.react._
import scommons.react.test.TestAttributesSpec._

import scala.scalajs.js

class TestAttributesSpec extends TestSpec with TestRendererUtils {

  TestAttributesSpec.wrappedChildComp = mockUiComponent("TestWrappedChild")
  TestAttributesSpec.plainChildComp = mockUiComponent("TestPlainChild")

  it should "render and assertWrapped" in {
    //when
    val result = testRender(<(TestWrappedComponent())()())

    //then
    assertNativeComponent(result,
      <.div()(
        <(wrappedChildComp())(^.assertWrapped(inside(_) {
          case TestWrappedProps("test wrapped", age) =>
            age shouldBe 123
        }))()
      )
    )
  }

  it should "render and assertPlain" in {
    //when
    val result = testRender(<(TestPlainComponent())()())

    //then
    inside(findComponentProps(result, plainChildComp, plain = true)) {
      case TestPlainProps("test plain", 123) =>
    }
    inside(findProps(result, plainChildComp, plain = true)) {
      case List(TestPlainProps("test plain", 123)) =>
    }
    
    assertNativeComponent(result,
      <.div()(
        <(plainChildComp())(^.assertPlain[TestPlainProps](inside(_) {
          case TestPlainProps("test plain", age) =>
            age shouldBe 123
        }))()
      )
    )
  }
}

object TestAttributesSpec {

  case class TestWrappedProps(name: String, age: Int)

  private var wrappedChildComp: UiComponent[TestWrappedProps] = TestWrappedComponent
  
  object TestWrappedComponent extends FunctionComponent[TestWrappedProps] {
    
    protected def render(props: Props): ReactElement = {
      <.div()(
        <(wrappedChildComp())(^.wrapped := TestWrappedProps("test wrapped", 123))()
      )
    }
  } 

  trait TestPlainProps extends js.Object {
    val name: String
    val age: Int
  }
  
  object TestPlainProps {

    def apply(name: String, age: Int): TestPlainProps = {
      js.Dynamic.literal(
        name = name,
        age = age
      ).asInstanceOf[TestPlainProps]
    }

    def unapply(arg: TestPlainProps): Option[(String, Int)] = {
      Some((
        arg.name,
        arg.age
      ))
    }
  }

  private var plainChildComp: UiComponent[TestPlainProps] = TestPlainComponent
  
  object TestPlainComponent extends FunctionComponent[TestPlainProps] {
    
    protected def render(props: Props): ReactElement = {
      <.div()(
        <(plainChildComp())(^.plain := TestPlainProps("test plain", 123))()
      )
    }
  } 
}
