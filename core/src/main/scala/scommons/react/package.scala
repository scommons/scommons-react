package scommons

import io.github.shogowada.scalajs.reactjs
import io.github.shogowada.scalajs.reactjs.VirtualDOM
import io.github.shogowada.statictags.Element

import scala.language.implicitConversions

package object react {

  type Props[T] = reactjs.React.Props[T]
  type ReactElement = reactjs.elements.ReactElement
  
  lazy val < : VirtualDOM.VirtualDOMElements = VirtualDOM.<
  lazy val ^ : VirtualDOM.VirtualDOMAttributes = VirtualDOM.^

  implicit def elementsToVirtualDOMs(element: Element): ReactElement =
    VirtualDOM.elementsToVirtualDOMs(element)
}
