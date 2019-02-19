package scommons.react.showcase.hooks

import scommons.react._
import scommons.react.hooks._

case class UseMemoDemoProps(a: Int, b: String)

class UseMemoDemo(checkCall: () => Unit) extends FunctionComponent[UseMemoDemoProps] {

  protected def render(props: Props): ReactElement = {
    val a = props.wrapped.a
    val b = props.wrapped.b
    
    val result = useMemo({ () =>
      checkCall()
      
      s"a: $a, b: $b"
    }, a, b)

    <.div()(result)
  }
}
