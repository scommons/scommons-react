package scommons.react.test.dom.raw

import org.scalajs.dom.Node

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react-dom", JSImport.Namespace)
object TestReactDOM extends js.Object {

  def findDOMNode(component: js.Any): Node = js.native

  /**
    * Remove a mounted React component from the DOM and clean up its event handlers and state.
    *
    * If no component was mounted in the container, calling this function does nothing.
    *
    * @param container DOM node where component is mounted
    * @return `true` if a component was unmounted and `false` if there was no component to unmount
    */
  def unmountComponentAtNode(container: Node): Boolean = js.native
}
