package scommons.react

import io.github.shogowada.scalajs.reactjs.ReactDomVirtualDOM
import io.github.shogowada.scalajs.reactjs.VirtualDOM._

package object dom {

  implicit class ReactDomVirtualDOMAttributes(attributes: VirtualDOMAttributes)
    extends ReactDomVirtualDOM.EventVirtualDOMAttributes(attributes)
}
