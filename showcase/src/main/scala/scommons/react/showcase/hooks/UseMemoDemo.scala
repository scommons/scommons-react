package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import scommons.react._
import scommons.react.hooks._

import scala.scalajs.js

case class UseMemoDemoProps(a: Int, b: String)

class UseMemoDemo(checkCall: () => Unit) extends FunctionComponent[UseMemoDemoProps] {

  protected def render(props: Props): ReactElement = {
    val a = props.wrapped.a
    val b = props.wrapped.b
    
    val result = useMemo({ () =>
      checkCall()
      
      s"a: $a, b: $b"
    }, List(a, b))

    // example of useCallback hook in terms of useMemo
    val onClick: js.Function1[MouseSyntheticEvent, Unit] = useMemo({ () =>
      _ => {
        println("onClick")
      }
    }, Nil)

    <.div(
      ^.onClick := onClick
    )(result)
  }
}
