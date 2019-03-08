package scommons.react.showcase

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import org.scalajs.dom.raw.HTMLInputElement
import scommons.react._

object ReactRefDemo extends UiComponent[Unit] {
  
  private case class ReactRefDemoState(textInput: ReactRef[HTMLInputElement])
  
  protected def create(): ReactClass = React.createClass[Unit, ReactRefDemoState](
    getInitialState = { _ =>
      ReactRefDemoState(ReactRef.create[HTMLInputElement])
    },
    render = { self =>
  
      def handleClick(e: MouseSyntheticEvent): Unit = {
        self.state.textInput.current.focus()
      }
      
      <.div()(
        <.input(
          ^.`type` := "text",
          ^.reactRef := self.state.textInput
        )(),
        
        <.button(
          ^.onClick := handleClick _
        )(
          "Focus the text input"
        )
      )
    }
  )
}
