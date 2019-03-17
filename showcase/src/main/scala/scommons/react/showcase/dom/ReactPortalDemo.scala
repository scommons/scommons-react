package scommons.react.showcase.dom

import scommons.react._
import scommons.react.dom.ReactPortal

case class ReactPortalDemoProps(show: Boolean)

object ReactPortalDemo extends FunctionComponent[ReactPortalDemoProps] {

  protected def render(props: Props): ReactElement = {
    val data = props.wrapped

    <.>()(
      if (data.show) Some(
        ReactPortal.create(
          <.div(^.className := "portal-content")(
            props.children
          )
          //, containerNode) // by default will append to document.body element
        )
      )
      else None
    )
  }
}
