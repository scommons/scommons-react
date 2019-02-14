package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import scommons.react._
import scommons.react.hooks._

object UseStateDemo extends FunctionComponent[Unit] {

  protected def render(props: Props): ReactElement = {
    val (counter1, setCounter1) = useState(0)
    val (counter2, setCounter2) = useState(() => 0)

    <.div()(
      <.p()(s"counter1: $counter1, counter2: $counter2"),
      
      <.button(^.className := "counter1", ^.onClick := { (_: MouseSyntheticEvent) =>
        setCounter1(counter1 + 1)
      })("Increase counter1"),
      
      <.button(^.className := "counter2", ^.onClick := { (_: MouseSyntheticEvent) =>
        setCounter2(count => count + 1)
      })("Increase counter2")
    )
  }
}
