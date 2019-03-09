package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import org.scalajs.dom.raw.HTMLInputElement
import scommons.react._
import scommons.react.hooks._

object UseRefDemo extends FunctionComponent[Unit] {

  protected def render(props: Props): ReactElement = {
    val inputEl = useRef[HTMLInputElement](null)
  
    def onButtonClick(e: MouseSyntheticEvent): Unit = {
      inputEl.current.focus()
    }
    
    <.>()(
      <.input(^.reactRef := inputEl, ^.`type` := "text")(),
      <.button(^.onClick := onButtonClick _)("Focus the input")
    )
  }
}
