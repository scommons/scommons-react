package scommons.react.showcase.hooks

import scommons.react._
import scommons.react.hooks._

case class UseLayoutEffectDemoProps(a: Int, b: String)

class UseLayoutEffectDemo(afterEveryRender: () => Unit,
                          onlyOnce: () => () => Unit,
                          whenDependenciesChange: () => () => Unit
                         ) extends FunctionComponent[UseLayoutEffectDemoProps] {

  protected def render(props: Props): ReactElement = {
    val a = props.wrapped.a
    val b = props.wrapped.b
    
    useLayoutEffect({ () =>
      afterEveryRender()
    })
    
    useLayoutEffect({ () =>
      onlyOnce()
    }, Nil)
    
    useLayoutEffect({ () =>
      whenDependenciesChange()
    }, List(a, b))

    <.div()(s"a: $a, b: $b")
  }
}
