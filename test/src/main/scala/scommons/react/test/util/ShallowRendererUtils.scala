package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.statictags.{AttributeValueType, Element}
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

  def shallowRender(element: scalajs.js.Object): ShallowInstance = {
    shallowRender(createRenderer(), element)
  }
  
  def shallowRender(renderer: ShallowRenderer, element: scalajs.js.Object): ShallowInstance = {
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
                            expectedElement: Element,
                            assertChildren: List[ShallowInstance] => Assertion = expectNoChildren): Assertion = {

    result.`type` shouldBe expectedElement.name

    def normalize(classes: String) = classes.split(' ').map(_.trim).filter(_.nonEmpty)

    def assertAttribute(attrName: String, resultValue: Any, expectedValue: Any): Unit = {
      if (resultValue != expectedValue) {
        fail(s"Attribute value doesn't match for ${expectedElement.name}.$attrName" +
          s"\n\texpected: $expectedValue" +
          s"\n\tactual:   $resultValue")
      }
    }

    for (attr <- expectedElement.flattenedAttributes) {
      val resultValue = result.props.selectDynamic(attr.name).asInstanceOf[Any]
      attr.value match {
        case attrValue: Map[_, _] =>
          resultValue.asInstanceOf[scalajs.js.Dictionary[String]].toMap shouldBe attrValue
        case _ if attr.valueType == AttributeValueType.SPACE_SEPARATED =>
          normalize(resultValue.toString).toSet shouldBe normalize(attr.valueToString).toSet
        case _ if resultValue.isInstanceOf[String] =>
          resultValue shouldBe attr.valueToString
        case _ if resultValue.isInstanceOf[js.Array[_]] && attr.value.isInstanceOf[js.Array[_]] =>
          val resultArr = resultValue.asInstanceOf[js.Array[_]].toList
          val expectedArr = attr.value.asInstanceOf[js.Array[_]].toList
          assertAttribute(attr.name, resultArr, expectedArr)
        case _ =>
          assertAttribute(attr.name, resultValue, attr.value)
      }
    }

    val children = getComponentChildren(result)

    expectedElement.flattenedContents match {
      case expectedChildren if expectedChildren.nonEmpty =>
        children.size shouldBe expectedChildren.size

        for (i <- expectedChildren.indices) {
          val child = children(i)
          expectedChildren(i) match {
            case expected: Element => assertNativeComponent(child, expected)
            case expected =>
              if (child != expected) {
                fail(s"Child Element at index $i doesn't match for ${expectedElement.name}" +
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
