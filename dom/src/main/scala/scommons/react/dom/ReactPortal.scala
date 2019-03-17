package scommons.react.dom

import org.scalajs.dom
import org.scalajs.dom.document
import scommons.react.ReactElement

object ReactPortal {

  def create(child: ReactElement, container: dom.Node = document.body): ReactElement = {
    raw.ReactDOM.createPortal(child, container)
  }
}
