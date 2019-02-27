package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalatest.{Assertion, Succeeded}
import scommons.react.UiComponent
import scommons.react.test.raw.{TestInstance, TestRenderer}
import scommons.react.test.util.RendererUtils.{testInstanceUtils => utils}

trait TestRendererUtils {

  def createTestRenderer(element: ReactElement): TestRenderer = {
    var result: TestRenderer = null
    TestRenderer.act { () =>
      result = TestRenderer.create(element)
    }

    result
  }
  
  def testRender(element: ReactElement): TestInstance = {
    val root = createTestRenderer(element).root
    root.children(0)
  }

  def findComponentProps[T](renderedComp: TestInstance, searchComp: UiComponent[T]): T = {
    utils.findComponentProps(renderedComp, searchComp)
  }

  def findProps[T](renderedComp: TestInstance, searchComp: UiComponent[T]): List[T] = {
    utils.findProps(renderedComp, searchComp)
  }

  def getComponentProps[T](component: TestInstance): T = {
    utils.getComponentProps(component)
  }

  def findComponents(component: TestInstance, componentClass: ReactClass): List[TestInstance] = {
    utils.findComponents(component, componentClass)
  }

  def assertTestComponent[T](result: TestInstance, expectedComp: UiComponent[T])
                            (assertProps: T => Assertion,
                             assertChildren: List[TestInstance] => Assertion = _ => Succeeded): Assertion = {

    utils.assertComponent(result, expectedComp)(assertProps, assertChildren)
  }

  def assertNativeComponent(result: TestInstance, expectedElement: ReactElement): Assertion = {
    assertNativeComponent(result, expectedElement, utils.expectNoChildren)
  }
  
  def assertNativeComponent(result: TestInstance,
                            expectedElement: ReactElement,
                            assertChildren: List[TestInstance] => Assertion): Assertion = {

    utils.assertNativeComponent(result, expectedElement, assertChildren)
  }
}
