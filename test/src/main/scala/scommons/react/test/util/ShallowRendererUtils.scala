package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalactic.source.Position
import org.scalatest.Assertion
import scommons.react.UiComponent
import scommons.react.test.raw.{ShallowInstance, ShallowRenderer}
import scommons.react.test.util.RendererUtils.{shallowInstanceUtils => utils}

trait ShallowRendererUtils {

  def createRenderer(): ShallowRenderer = new ShallowRenderer

  def shallowRender(element: ReactElement): ShallowInstance = {
    val renderer = createRenderer()
    renderer.render(element)
    renderer.getRenderOutput()
  }
  
  def findComponentProps[T](renderedComp: ShallowInstance,
                            searchComp: UiComponent[T])(implicit pos: Position): T = {
    
    utils.findComponentProps(renderedComp, searchComp)
  }

  def findProps[T](renderedComp: ShallowInstance, searchComp: UiComponent[T]): List[T] = {
    utils.findProps(renderedComp, searchComp)
  }

  def findComponents(component: ShallowInstance, componentType: Any): List[ShallowInstance] = {
    utils.findComponents(component, componentType)
  }

  def assertComponent[T](result: ShallowInstance, expectedComp: UiComponent[T])
                        (assertProps: T => Assertion,
                         assertChildren: List[ShallowInstance] => Assertion = utils.expectNoChildren
                        )(implicit pos: Position): Assertion = {

    utils.assertComponent(result, expectedComp)(assertProps, assertChildren)
  }

  def assertNativeComponent(result: ShallowInstance,
                            expectedElement: ReactElement
                           )(implicit pos: Position): Assertion = {
    
    assertNativeComponent(result, expectedElement, utils.expectNoChildren)
  }
  
  def assertNativeComponent(result: ShallowInstance,
                            expectedElement: ReactElement,
                            assertChildren: List[ShallowInstance] => Assertion
                           )(implicit pos: Position): Assertion = {

    utils.assertNativeComponent(result, expectedElement, assertChildren)
  }
}
