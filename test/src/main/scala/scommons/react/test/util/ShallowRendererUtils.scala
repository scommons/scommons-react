package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalatest.{Assertion, Matchers, Succeeded}
import scommons.react.UiComponent
import scommons.react.test.raw.{ShallowInstance, ShallowRenderer}

import scala.collection.mutable.ListBuffer
import scala.scalajs.js

trait ShallowRendererUtils extends Matchers {

  private val expectNoChildren: List[ShallowInstance] => Assertion = { children =>
    children.map {
      case i if !scalajs.js.isUndefined(i.`type`) => i.`type`.toString
      case i => i.toString
    } shouldBe Nil
  }

  def createRenderer(): ShallowRenderer = new ShallowRenderer

  def shallowRender(element: ReactElement): ShallowInstance = {
    shallowRender(createRenderer(), element)
  }
  
  def shallowRender(renderer: ShallowRenderer, element: ReactElement): ShallowInstance = {
    renderer.render(element)
    renderer.getRenderOutput()
  }

  def findComponentProps[T](renderedComp: ShallowInstance, searchComp: UiComponent[T]): T = {
    findProps[T](renderedComp, searchComp).headOption match {
      case Some(comp) => comp
      case None => throw new IllegalStateException(s"UiComponent $searchComp not found")
    }
  }

  def findProps[T](renderedComp: ShallowInstance, searchComp: UiComponent[T]): List[T] = {
    findComponents(renderedComp, searchComp.apply()).map(getComponentProps[T])
  }

  def getComponentProps[T](component: ShallowInstance): T = component.props.wrapped.asInstanceOf[T]

  def findComponents(component: ShallowInstance,
                     componentClass: ReactClass): List[ShallowInstance] = {

    def search(components: List[ShallowInstance],
               result: ListBuffer[ShallowInstance]): Unit = components match {

      case Nil =>
      case head :: tail =>
        if (head.`type` == componentClass) {
          result += head
        }

        search(getComponentChildren(head), result)
        search(tail, result)
    }

    val result = new ListBuffer[ShallowInstance]
    search(List(component), result)
    result.toList
  }

  def assertComponent[T](result: ShallowInstance, expectedComp: UiComponent[T])
                        (assertProps: T => Assertion,
                         assertChildren: List[ShallowInstance] => Assertion = expectNoChildren): Assertion = {

    result.`type` shouldBe expectedComp.apply()

    assertProps(result.props.wrapped.asInstanceOf[T])
    assertChildren(getComponentChildren(result))
  }

  def assertNativeComponent(result: ShallowInstance,
                            expectedElement: ReactElement,
                            assertChildren: List[ShallowInstance] => Assertion = expectNoChildren
                           ): Assertion = {

    val expectedInstance = expectedElement.asInstanceOf[ShallowInstance]
    
    result.`type` shouldBe expectedInstance.`type`

    def assertValue(name: String, resultValue: Any, expectedValue: Any): Unit = {
      if (resultValue != expectedValue) {
        fail(s"Attribute value doesn't match for $name" +
          s"\n\texpected: $expectedValue" +
          s"\n\tactual:   $resultValue")
      }
    }

    def assertArray(name: String, resultValue: js.Any, expectedArr: js.Array[_]): Unit = {
      assertValue(name, js.Array.isArray(resultValue), true)

      if (resultValue != expectedArr) {
        val resultList = resultValue.asInstanceOf[js.Array[_]].toList
        val expectedList = expectedArr.toList
        assertValue(name, resultList, expectedList)
      }
    }
    
    def assertObject(name: String, resultValue: js.Any, expectedObject: js.Object with js.Dynamic): Unit = {
      assertValue(name, js.typeOf(resultValue), "object")
      
      if (resultValue != expectedObject) {
        val resultObject = resultValue.asInstanceOf[js.Object with js.Dynamic]
        val resultKeys = js.Object.keys(resultObject).toSet
        val expectedKeys = js.Object.keys(expectedObject).toSet
        assertValue(name, resultKeys, expectedKeys)

        for (key <- expectedKeys) {
          val resultValue = resultObject.selectDynamic(key)
          val expectedValue = expectedObject.selectDynamic(key)
          if (js.typeOf(expectedValue) == "object")
            assertObject(s"$name.$key", resultValue, expectedValue.asInstanceOf[js.Object with js.Dynamic])
          else
            assertValue(s"$name.$key", resultValue, expectedValue)
        }
      }
    }
    
    for (attr <- js.Object.keys(expectedInstance.props).filter(_ != "children")) {
      val resultValue = result.props.selectDynamic(attr)
      val expectedValue = expectedInstance.props.selectDynamic(attr).asInstanceOf[js.Any]
      js.typeOf(expectedValue) match {
        case "object" =>
          if (js.Array.isArray(expectedValue)) {
            assertArray(s"${expectedInstance.`type`}.$attr",
              resultValue, expectedValue.asInstanceOf[js.Array[_]])
          }
          else {
            assertObject(s"${expectedInstance.`type`}.$attr",
              resultValue, expectedValue.asInstanceOf[js.Object with js.Dynamic])
          }
        case _ =>
          assertValue(s"${expectedInstance.`type`}.$attr", resultValue, expectedValue)
      }
    }

    val children = getComponentChildren(result)

    getComponentChildren(expectedInstance) match {
      case expectedChildren if expectedChildren.nonEmpty =>
        children.size shouldBe expectedChildren.size

        for (i <- expectedChildren.indices) {
          val child = children(i)
          expectedChildren(i) match {
            case expected if !scalajs.js.isUndefined(expected.asInstanceOf[js.Dynamic].`type`) =>
              assertNativeComponent(child, expected.asInstanceOf[ReactElement])
            case expected =>
              if (child != expected) {
                fail(s"Child Element at index $i doesn't match for ${expectedInstance.`type`}" +
                  s"\n\texpected: $expected" +
                  s"\n\tactual:   $child")
              }
          }
        }

        Succeeded
      case _ => assertChildren(children)
    }
  }

  private def getComponentChildren(result: ShallowInstance): List[ShallowInstance] = {
    if (scalajs.js.isUndefined(result.props)) Nil
    else {
      val children = result.props.children

      if (scalajs.js.isUndefined(children)) Nil
      else if (scalajs.js.Array.isArray(children)) {
        children.asInstanceOf[scalajs.js.Array[ShallowInstance]].toList
      }
      else List(children.asInstanceOf[ShallowInstance])
    }
  }
}
