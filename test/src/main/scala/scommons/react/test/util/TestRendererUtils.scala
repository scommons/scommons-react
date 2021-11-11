package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalactic.source.Position
import org.scalatest.{Assertion, Succeeded}
import scommons.react.{ReactClass, UiComponent}
import scommons.react.test.raw
import scommons.react.test.raw.{TestInstance, TestRenderer}
import scommons.react.test.util.RendererUtils.{testInstanceUtils => utils}
import scommons.react.test.util.TestRendererUtils.UiComponentMock

import scala.scalajs.js

trait TestRendererUtils {
  
  def mockUiComponent[T](name: String): UiComponent[T] = UiComponentMock[T](name)

  def createTestRenderer(element: ReactElement,
                         createMock: js.Function1[TestInstance, js.Any] = null): TestRenderer = {
    
    var result: TestRenderer = null
    TestRenderer.act { () =>
      result = TestRenderer.create(
        element,
        if (createMock != null) new raw.TestRendererOptions {
          override val createNodeMock = createMock
        }
        else null
      )
    }

    result
  }
  
  def testRender(element: ReactElement,
                 createMock: js.Function1[TestInstance, js.Any] = null): TestInstance = {
    
    val root = createTestRenderer(element, createMock).root
    root.children(0)
  }

  def findComponentProps[T](renderedComp: TestInstance,
                            searchComp: UiComponent[T])(implicit pos: Position): T = {
    
    utils.findComponentProps(renderedComp, searchComp)
  }

  def findProps[T](renderedComp: TestInstance, searchComp: UiComponent[T]): List[T] = {
    utils.findProps(renderedComp, searchComp)
  }

  def findComponents(component: TestInstance, componentType: Any): List[TestInstance] = {
    utils.findComponents(component, componentType)
  }

  def assertTestComponent[T](result: TestInstance, expectedComp: UiComponent[T])
                            (assertProps: T => Assertion,
                             assertChildren: List[TestInstance] => Assertion = _ => Succeeded
                            )(implicit pos: Position): Assertion = {

    utils.assertComponent(result, expectedComp)(assertProps, assertChildren)
  }

  def assertNativeComponent(result: TestInstance,
                            expectedElement: ReactElement
                           )(implicit pos: Position): Assertion = {
    
    assertNativeComponent(result, expectedElement, utils.expectNoChildren)
  }
  
  def assertNativeComponent(result: TestInstance,
                            expectedElement: ReactElement,
                            assertChildren: List[TestInstance] => Assertion
                           )(implicit pos: Position): Assertion = {

    utils.assertNativeComponent(result, expectedElement, assertChildren)
  }
}

object TestRendererUtils {

  private case class UiComponentMock[T](name: String) extends UiComponent[T] {

    protected def create(): ReactClass = name.asInstanceOf[ReactClass]
  }
}
