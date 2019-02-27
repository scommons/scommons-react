package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalatest.Assertion
import scommons.react.UiComponent
import scommons.react.test.raw.{ShallowInstance, ShallowRenderer}
import scommons.react.test.util.RendererUtils.{shallowInstanceUtils => utils}

trait ShallowRendererUtils {

  def createRenderer(): ShallowRenderer = new ShallowRenderer

  def shallowRender(element: ReactElement): ShallowInstance = {
    shallowRender(createRenderer(), element)
  }
  
  def shallowRender(renderer: ShallowRenderer, element: ReactElement): ShallowInstance = {
    renderer.render(element)
    renderer.getRenderOutput()
  }

  def findComponentProps[T](renderedComp: ShallowInstance, searchComp: UiComponent[T]): T = {
    utils.findComponentProps(renderedComp, searchComp)
  }

  def findProps[T](renderedComp: ShallowInstance, searchComp: UiComponent[T]): List[T] = {
    utils.findProps(renderedComp, searchComp)
  }

  def getComponentProps[T](component: ShallowInstance): T = {
    utils.getComponentProps(component)
  }

  def findComponents(component: ShallowInstance, componentClass: ReactClass): List[ShallowInstance] = {
    utils.findComponents(component, componentClass)
  }

  def assertComponent[T](result: ShallowInstance, expectedComp: UiComponent[T])
                        (assertProps: T => Assertion,
                         assertChildren: List[ShallowInstance] => Assertion = utils.expectNoChildren): Assertion = {

    utils.assertComponent(result, expectedComp)(assertProps, assertChildren)
  }

  def assertNativeComponent(result: ShallowInstance,
                            expectedElement: ReactElement,
                            assertChildren: List[ShallowInstance] => Assertion = utils.expectNoChildren
                           ): Assertion = {

    utils.assertNativeComponent(result, expectedElement, assertChildren)
  }
}
