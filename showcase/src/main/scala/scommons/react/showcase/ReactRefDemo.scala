package scommons.react.showcase

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import org.scalajs.dom.HTMLInputElement
import scommons.react._
import scommons.react.dom._

object ReactRefDemo extends FunctionComponent[Unit] {
  
  protected def render(props: Props): ReactElement = {
    val inputRef = ReactRef.create[HTMLInputElement]
  
    <.div()(
      <.input(
        ^.`type` := "text",
        ^.reactRef := inputRef
      )(),
      
      <.button(
        ^.onClick := { _: MouseSyntheticEvent =>
          inputRef.current.focus()
        }
      )(
        "Focus the text input"
      )
    )
  }
}
