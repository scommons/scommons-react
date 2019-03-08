package scommons

import io.github.shogowada.scalajs.reactjs
import io.github.shogowada.scalajs.reactjs.VirtualDOM
import io.github.shogowada.scalajs.reactjs.VirtualDOM.VirtualDOMElements.ReactClassElementSpec
import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.statictags._

import scala.language.implicitConversions
import scala.scalajs.js

package object react {

  type ReactElement = reactjs.elements.ReactElement
  
  implicit class ReactVirtualDOMElements(elements: VirtualDOMElements) {
    lazy val > : ReactClassElementSpec = elements(raw.React.Fragment)
  }

  object ReactVirtualDOMAttributes {

    import VirtualDOMAttributes.Type._

    case class ContextValueAttributeSpec(name: String) extends AttributeSpec {
      def :=(value: js.Any): Attribute[js.Any] = Attribute(name, value, AS_IS)
      def :=(value: AnyRef): Attribute[js.Any] = Attribute(name, value.asInstanceOf[js.Any], AS_IS)
    }
    
    case class ReactRefAttributeSpec(name: String) extends AttributeSpec {
      def :=(value: ReactRef[_]): Attribute[js.Any] = Attribute(name, value.native, AS_IS)
    }
  }

  implicit class ReactVirtualDOMAttributes(attributes: VirtualDOMAttributes) {

    import ReactVirtualDOMAttributes._

    lazy val contextValue = ContextValueAttributeSpec("value")
    lazy val reactRef = ReactRefAttributeSpec("ref")
  }

  lazy val < : VirtualDOM.VirtualDOMElements = VirtualDOM.<
  lazy val ^ : VirtualDOM.VirtualDOMAttributes = VirtualDOM.^

  implicit def elementsToVirtualDOMs(element: Element): ReactElement =
    VirtualDOM.elementsToVirtualDOMs(element)
}
