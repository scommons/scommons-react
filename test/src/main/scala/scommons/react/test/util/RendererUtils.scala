package scommons.react.test.util

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalactic.source.Position
import org.scalatest.matchers.should.Matchers
import org.scalatest.{Assertion, Succeeded}
import scommons.react.UiComponent
import scommons.react.test.raw.RenderedInstance

import scala.collection.mutable.ListBuffer
import scala.scalajs.js

sealed trait RendererUtils[Instance <: RenderedInstance] extends Matchers {

  private[util] def expectNoChildren(implicit pos: Position): List[Instance] => Assertion = { children =>
    val resultChildren = children.map {
      case i if !scalajs.js.isUndefined(i.`type`) => i.`type`.toString
      case i => i.toString
    }
    
    assert(resultChildren.isEmpty, ": Expected no children")
  }

  def findComponentProps[T](renderedComp: Instance,
                            searchComp: UiComponent[T]
                           )(implicit pos: Position): T = {
    
    findProps[T](renderedComp, searchComp).headOption match {
      case Some(comp) => comp
      case None => fail(s"UiComponent $searchComp not found")
    }
  }

  def findProps[T](renderedComp: Instance, searchComp: UiComponent[T]): List[T] = {
    def getComponentProps(component: Instance): T = {
      component.props.wrapped.asInstanceOf[T]
    }
    
    findComponents(renderedComp, searchComp.apply()).map(getComponentProps)
  }

  def findComponents(component: Instance, componentType: Any): List[Instance] = {

    def search(components: List[Instance],
               result: ListBuffer[Instance]): Unit = components match {

      case Nil =>
      case head :: tail =>
        if (head.`type` == componentType) {
          result += head
        }

        search(getComponentChildren(head), result)
        search(tail, result)
    }

    val result = new ListBuffer[Instance]
    search(List(component), result)
    result.toList
  }

  def assertComponent[T](result: Instance, expectedComp: UiComponent[T])
                        (assertProps: T => Assertion,
                         assertChildren: List[Instance] => Assertion
                        )(implicit pos: Position): Assertion = {

    result.`type` shouldBe expectedComp.apply()

    assertProps(result.props.wrapped.asInstanceOf[T])
    assertChildren(getComponentChildren(result))
  }

  def assertNativeComponent(result: Instance,
                            expectedElement: ReactElement,
                            assertChildren: List[Instance] => Assertion
                           )(implicit pos: Position): Assertion = {

    val expectedInstance = expectedElement.asInstanceOf[Instance]
    
    result.`type` shouldBe expectedInstance.`type`
    
    val expectedType = js.Object(expectedInstance.`type`).toString

    def assertArray(name: String, resultValue: js.Any, expectedArr: js.Array[_]): Unit = {
      assertAttrValue(name, js.Array.isArray(resultValue), true)

      if (resultValue != expectedArr) {
        val resultList = resultValue.asInstanceOf[js.Array[_]].toList
        val expectedList = expectedArr.toList
        assertAttrValue(name, resultList, expectedList)
      }
    }
    
    def assertObject(name: String, resultValue: js.Any, expectedObject: js.Object with js.Dynamic): Unit = {
      assertAttrValue(name, js.typeOf(resultValue), "object")
      
      if (resultValue != expectedObject) {
        val resultObject = resultValue.asInstanceOf[js.Object with js.Dynamic]
        val resultKeys = js.Object.keys(resultObject).toSet
        val expectedKeys = js.Object.keys(expectedObject).toSet
        assertAttrValue(name, resultKeys, expectedKeys)

        for (key <- expectedKeys) {
          val resultValue = resultObject.selectDynamic(key)
          val expectedValue = expectedObject.selectDynamic(key)
          if (js.typeOf(expectedValue) == "object")
            assertObject(s"$name.$key", resultValue, expectedValue.asInstanceOf[js.Object with js.Dynamic])
          else
            assertAttrValue(s"$name.$key", resultValue, expectedValue)
        }
      }
    }
    
    for (attr <- js.Object.keys(expectedInstance.props).filter(_ != "children")) {
      val resultValue = result.props.selectDynamic(attr)
      val expectedValue = expectedInstance.props.selectDynamic(attr).asInstanceOf[js.Any]
      js.typeOf(expectedValue) match {
        case "object" =>
          if (js.Array.isArray(expectedValue)) {
            assertArray(s"$expectedType.$attr",
              resultValue, expectedValue.asInstanceOf[js.Array[_]])
          }
          else {
            assertObject(s"$expectedType.$attr",
              resultValue, expectedValue.asInstanceOf[js.Object with js.Dynamic])
          }
        case _ =>
          assertAttrValue(s"$expectedType.$attr", resultValue, expectedValue)
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
              assertNativeComponent(child, expected.asInstanceOf[ReactElement], expectNoChildren)
            case expected =>
              if (child != expected) {
                fail(s"Child Element at index $i doesn't match for $expectedType" +
                  s"\n\texpected: $expected" +
                  s"\n\tactual:   $child")
              }
          }
        }

        Succeeded
      case _ => assertChildren(children)
    }
  }

  protected def assertAttrValue(name: String,
                                resultValue: Any,
                                expectedValue: Any)(implicit pos: Position): Unit = {
    
    if (resultValue != expectedValue) {
      fail(s"Attribute value doesn't match for $name" +
        s"\n\texpected: $expectedValue" +
        s"\n\tactual:   $resultValue")
    }
  }

  private def getComponentChildren(result: Instance): List[Instance] = {
    val resultChildren = result.asInstanceOf[js.Dynamic].children

    // in case of ShallowInstance or ReactElement get children from props
    // in case of TestInstance return children as it is
    //
    if (scalajs.js.isUndefined(resultChildren)) {
      if (scalajs.js.isUndefined(result.props)) Nil
      else {
        val children = result.props.children

        if (scalajs.js.isUndefined(children)) Nil
        else if (scalajs.js.Array.isArray(children)) {
          children.asInstanceOf[scalajs.js.Array[Instance]].toList
        }
        else List(children.asInstanceOf[Instance])
      }
    }
    else resultChildren.asInstanceOf[js.Array[Instance]].toList
  }
}

object RendererUtils {

  import scommons.react.test.raw.{ShallowInstance, TestInstance}
  
  val shallowInstanceUtils: RendererUtils[ShallowInstance] = new RendererUtils[ShallowInstance] {

    override def assertNativeComponent(result: ShallowInstance,
                                       expectedElement: ReactElement,
                                       assertChildren: List[ShallowInstance] => Assertion
                                      )(implicit pos: Position): Assertion = {

      val expectedInstance = expectedElement.asInstanceOf[ShallowInstance]

      val expectedType = js.Object(expectedInstance.`type`).toString
      assertAttrValue(s"$expectedType.key", result.key, expectedInstance.key)

      super.assertNativeComponent(result, expectedElement, assertChildren)
    }
  }

  val testInstanceUtils: RendererUtils[TestInstance] = new RendererUtils[TestInstance] {}
}
