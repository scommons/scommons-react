package scommons.react

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.React.Self

import scala.scalajs.js

trait ClassComponent[T] extends UiComponent[T] {

  protected def createClass[S](
    render: Self[T, S] => ReactElement,
    getInitialState: Self[T, S] => S = null,
    componentDidMount: Self[T, S] => Unit = null,
    shouldComponentUpdate: (Self[T, S], Props, S) => Boolean = null,
    componentDidUpdate: (Self[T, S], Props, S) => Unit = null,
    componentWillUnmount: Self[T, S] => Unit = null,
    componentDidCatch: (Self[T, S], js.Object, js.Dynamic) => Unit = null
  ): ReactClass = {
    React.createClass(
      render = render,
      displayName = displayName,
      componentDidMount = componentDidMount,
      shouldComponentUpdate = shouldComponentUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      componentDidCatch = componentDidCatch,
      getInitialState = getInitialState
    )
  }
}
