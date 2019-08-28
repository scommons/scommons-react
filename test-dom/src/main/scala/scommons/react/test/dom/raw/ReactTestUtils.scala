package scommons.react.test.dom.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * @see https://reactjs.org/docs/test-utils.html
  */
@JSImport("react-dom/test-utils", JSImport.Namespace)
@js.native
object ReactTestUtils extends js.Object {

  /**
    * @see [[scommons.react.test.dom.raw.Simulate]]
    */
  val Simulate: Simulate with js.Dynamic = js.native

  /**
    * To prepare a component for assertions, wrap the code rendering it and performing updates inside an act() call.
    * This makes your test run closer to how React works in the browser.
    */
  def act(block: js.Function0[Unit]): Unit = js.native
}
