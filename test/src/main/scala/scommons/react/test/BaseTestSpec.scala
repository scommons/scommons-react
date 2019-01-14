package scommons.react.test

import io.github.shogowada.scalajs.reactjs.VirtualDOM
import org.scalatest.{Inside, Matchers}

trait BaseTestSpec extends Matchers
  with Inside {

  lazy val < : VirtualDOM.VirtualDOMElements = VirtualDOM.<
  lazy val ^ : VirtualDOM.VirtualDOMAttributes = VirtualDOM.^
}
