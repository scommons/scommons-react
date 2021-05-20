package scommons.react.showcase

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import org.scalajs.dom.raw.HTMLInputElement
import scommons.react._

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
