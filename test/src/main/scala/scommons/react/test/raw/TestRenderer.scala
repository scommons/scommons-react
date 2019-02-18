package scommons.react.test.raw

import io.github.shogowada.scalajs.reactjs.elements.ReactElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * Facade for React TestRenderer.
  * 
  * @see https://reactjs.netlify.com/docs/test-renderer.html
  */
@JSImport("react-test-renderer", JSImport.Namespace)
@js.native
object TestRenderer extends js.Object {

  /**
    * To prepare a component for assertions, wrap the code rendering it and performing updates inside an act() call.
    * This makes your test run closer to how React works in the browser.
    */
  def act(block: js.Function0[Unit]): Unit = js.native

  def create(element: ReactElement): TestRenderer = js.native
}

@js.native
trait TestRenderer extends js.Object {

  val root: TestInstance = js.native

  def update(element: ReactElement): Unit = js.native
  
  def unmount(): Unit = js.native
}

@js.native
trait TestInstance extends js.Object {

  val `type`: js.Any = js.native
  val props: js.Dynamic = js.native
  val children: js.Array[TestInstance] = js.native
}