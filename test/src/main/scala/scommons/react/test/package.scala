package scommons.react

import io.github.shogowada.scalajs.reactjs.VirtualDOM._
import io.github.shogowada.statictags._

package object test {

  lazy val TestRenderer = test.raw.TestRenderer
  type TestInstance = test.raw.TestInstance
  type TestRendererUtils = test.util.TestRendererUtils

  object TestVirtualDOMAttributes {

    import VirtualDOMAttributes.Type._

    case class AssertWrappedAttributeSpec(name: String) extends AttributeSpec {
      def apply(f: Any => Any): Attribute[Any => Any] = Attribute(name, f, AS_IS)
    }

    case class AssertPlainAttributeSpec(name: String) extends AttributeSpec {
      def apply[T](f: T => Any): Attribute[T => Any] = Attribute(name, f, AS_IS)
    }
  }

  implicit class TestVirtualDOMAttributes(attributes: VirtualDOMAttributes) {

    import TestVirtualDOMAttributes._

    lazy val assertWrapped = AssertWrappedAttributeSpec("assertWrapped")
    lazy val assertPlain = AssertPlainAttributeSpec("assertPlain")
  }
}
