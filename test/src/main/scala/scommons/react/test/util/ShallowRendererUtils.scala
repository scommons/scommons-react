package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.statictags.{AttributeValueType, Element}
import org.scalatest.{Assertion, Matchers, Succeeded}
import scommons.react.UiComponent
import scommons.react.test.raw.ShallowRenderer
import scommons.react.test.raw.ShallowRenderer._

import scala.collection.mutable.ListBuffer

trait ShallowRendererUtils extends Matchers {

  private val expectNoChildren: List[ComponentInstance] => Assertion = { children =>
    children shouldBe Nil
  }

  def createRenderer(): ShallowRenderer = new ShallowRenderer

  def shallowRender(element: scalajs.js.Object): ComponentInstance = renderAndGetOutput(element)

  def findComponentProps[T](renderedComp: ComponentInstance, searchComp: UiComponent[T]): T = {
    findProps[T](renderedComp, searchComp).headOption match {
      case Some(comp) => comp
      case None => throw new IllegalStateException(s"UiComponent $searchComp not found")
    }
  }

  def findProps[T](renderedComp: ComponentInstance, searchComp: UiComponent[T]): List[T] = {
    findComponents(renderedComp, searchComp.apply()).map(getComponentProps[T])
  }

  def getComponentProps[T](component: ComponentInstance): T = component.props.wrapped.asInstanceOf[T]

  def findComponents(component: ComponentInstance,
                     componentClass: ReactClass): List[ComponentInstance] = {

    def search(components: List[ComponentInstance],
               result: ListBuffer[ComponentInstance]): Unit = components match {

      case Nil =>
      case head :: tail =>
        if (head.`type` == componentClass) {
          result += head
        }

        search(getComponentChildren(head), result)
        search(tail, result)
    }

    val result = new ListBuffer[ComponentInstance]
    search(List(component), result)
    result.toList
  }

  def assertComponent[T](result: ComponentInstance, expectedComp: UiComponent[T])
                        (assertProps: T => Assertion,
                         assertChildren: List[ComponentInstance] => Assertion = expectNoChildren): Assertion = {

    result.`type` shouldBe expectedComp.apply()

    assertProps(result.props.wrapped.asInstanceOf[T])
    assertChildren(getComponentChildren(result))
  }

  def assertNativeComponent(result: ComponentInstance,
                            expectedElement: Element,
                            assertChildren: List[ComponentInstance] => Assertion = expectNoChildren): Assertion = {

    def normalize(classes: String) = classes.split(' ').map(_.trim).filter(_.nonEmpty)

    result.`type` shouldBe expectedElement.name

    for (attr <- expectedElement.flattenedAttributes) {
      val resultValue = result.props.selectDynamic(attr.name).asInstanceOf[Any]
      attr.value match {
        case attrValue: Map[_, _] =>
          resultValue.asInstanceOf[scalajs.js.Dictionary[String]].toMap shouldBe attrValue
        case _ if attr.valueType == AttributeValueType.SPACE_SEPARATED =>
          normalize(resultValue.toString).toSet shouldBe normalize(attr.valueToString).toSet
        case _ if resultValue.isInstanceOf[String] =>
          resultValue shouldBe attr.valueToString
        case _ =>
          resultValue shouldBe attr.value
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
            case expected => child shouldBe expected
          }
        }

        Succeeded
      case _ => assertChildren(children)
    }
  }

  private def getComponentChildren(result: ComponentInstance): List[ComponentInstance] = {
    if (scalajs.js.isUndefined(result.props)) Nil
    else {
      val children = result.props.children

      if (scalajs.js.isUndefined(children)) Nil
      else if (scalajs.js.Array.isArray(children)) {
        children.asInstanceOf[scalajs.js.Array[ComponentInstance]].toList
      }
      else List(children.asInstanceOf[ComponentInstance])
    }
  }
}
