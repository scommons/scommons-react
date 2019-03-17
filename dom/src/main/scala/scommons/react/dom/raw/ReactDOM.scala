package scommons.react.dom.raw

import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react-dom", JSImport.Namespace)
object ReactDOM extends js.Object {

  def createPortal(child: ReactElement, container: dom.Node): ReactElement = js.native
}
