package scommons

import io.github.shogowada.scalajs.reactjs
import io.github.shogowada.scalajs.reactjs.VirtualDOM
import io.github.shogowada.scalajs.reactjs.VirtualDOM.VirtualDOMElements.ReactClassElementSpec
import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.statictags._

import scala.language.implicitConversions

package object react {

  type ReactElement = reactjs.elements.ReactElement
  
  implicit class ReactVirtualDOMElements(elements: VirtualDOMElements) {
    lazy val > : ReactClassElementSpec = elements(raw.React.Fragment)
  }
  
  lazy val < : VirtualDOM.VirtualDOMElements = VirtualDOM.<
  lazy val ^ : VirtualDOM.VirtualDOMAttributes = VirtualDOM.^

  implicit def elementsToVirtualDOMs(element: Element): ReactElement =
    VirtualDOM.elementsToVirtualDOMs(element)
}
