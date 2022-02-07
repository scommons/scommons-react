package scommons.react

import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.statictags._

package object test {

  lazy val TestRenderer = test.raw.TestRenderer
  type TestInstance = test.raw.TestInstance
  type TestRendererUtils = test.util.TestRendererUtils

  object TestVirtualDOMAttributes {

    import VirtualDOMAttributes.Type._

    case class AssertPropAttributeSpec(name: String) extends AttributeSpec {
      def apply(f: Any => Any): Attribute[Any => Any] = Attribute(name, f, AS_IS)
    }
  }

  implicit class TestVirtualDOMAttributes(attributes: VirtualDOMAttributes) {

    import TestVirtualDOMAttributes._

    lazy val assertWrapped = AssertPropAttributeSpec("assertWrapped")
  }
}
