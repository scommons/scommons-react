package scommons.react.showcase.hooks

import scommons.react._
import scommons.react.hooks._

case class UseEffectDemoProps(a: Int, b: String)

class UseEffectDemo(afterEveryRender: () => Unit,
                    onlyOnce: () => () => Unit,
                    whenDependenciesChange: () => () => Unit
                   ) extends FunctionComponent[UseEffectDemoProps] {

  protected def render(props: Props): ReactElement = {
    val a = props.wrapped.a
    val b = props.wrapped.b
    
    useEffect({ () =>
      afterEveryRender()
    })
    
    useEffect({ () =>
      onlyOnce()
    }, Nil)
    
    useEffect({ () =>
      whenDependenciesChange()
    }, List(a, b))

    <.div()(s"a: $a, b: $b")
  }
}
