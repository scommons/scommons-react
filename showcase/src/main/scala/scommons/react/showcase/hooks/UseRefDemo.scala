package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import org.scalajs.dom.raw.HTMLInputElement
import scommons.react._
import scommons.react.hooks._

object UseRefDemo extends FunctionComponent[Unit] {

  protected def render(props: Props): ReactElement = {
    val inputEl = useRef[HTMLInputElement](null)
  
    <.>()(
      <.input(
        ^.reactRef := inputEl,
        ^.`type` := "text"
      )(),
      
      <.button(
        ^.onClick := { _: MouseSyntheticEvent =>
          inputEl.current.focus()
        }
      )("Focus the input")
    )
  }
}
