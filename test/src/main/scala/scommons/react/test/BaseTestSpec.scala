package scommons.react.test

import io.github.shogowada.scalajs.reactjs.VirtualDOM
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import io.github.shogowada.statictags.Element
import org.scalatest.{Inside, Matchers}

import scala.language.implicitConversions

trait BaseTestSpec extends Matchers
  with Inside {

  lazy val < : VirtualDOM.VirtualDOMElements = VirtualDOM.<
  lazy val ^ : VirtualDOM.VirtualDOMAttributes = VirtualDOM.^

  implicit def elementsToVirtualDOMs(element: Element): ReactElement =
    VirtualDOM.elementsToVirtualDOMs(element)
}
