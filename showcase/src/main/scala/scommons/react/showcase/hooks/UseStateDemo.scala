package scommons.react.showcase.hooks

import io.github.shogowada.scalajs.reactjs.events.MouseSyntheticEvent
import scommons.react._
import scommons.react.hooks._

object UseStateDemo extends FunctionComponent[Unit] {

  private case class DemoState(counter: Int)
  
  protected def render(props: Props): ReactElement = {
    val (counter1, setCounter1) = useState(0)
    val (state2, setState2) = useStateUpdater(DemoState(1))
    
    // example of lazy initialization
    val (state3, setState3) = useState(() => DemoState(2))
    val (counter4, setCounter4) = useStateUpdater(() => 3)

    <.div()(
      <.p()(s"counter1: $counter1" +
        s", counter2: ${state2.counter}" +
        s", counter3: ${state3.counter}" +
        s", counter4: $counter4"
      ),
      
      <.button(^.className := "counter1", ^.onClick := { (_: MouseSyntheticEvent) =>
        setCounter1(counter1 + 1)
      })("Increase counter1"),
      
      <.button(^.className := "counter2", ^.onClick := { (_: MouseSyntheticEvent) =>
        setState2(s => s.copy(counter = s.counter + 1))
      })("Increase counter2"),
      
      <.button(^.className := "counter3", ^.onClick := { (_: MouseSyntheticEvent) =>
        setState3(state3.copy(counter = state3.counter + 1))
      })("Increase counter3"),
      
      <.button(^.className := "counter4", ^.onClick := { (_: MouseSyntheticEvent) =>
        setCounter4(count => count + 1)
      })("Increase counter4")
    )
  }
}
