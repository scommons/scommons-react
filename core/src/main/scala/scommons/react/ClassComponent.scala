package scommons.react

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.React.{Props, Self}
import io.github.shogowada.scalajs.reactjs.utils.Utils

trait ClassComponent[T] extends UiComponent[T] {

  protected def createClass[S](
    render: Self[T, S] => ReactElement,
    getInitialState: Self[T, S] => S = null,
    componentWillMount: Self[T, S] => Unit = null,
    componentDidMount: Self[T, S] => Unit = null,
    componentWillReceiveProps: (Self[T, S], Props[T]) => Unit = null,
    shouldComponentUpdate: (Self[T, S], Props[T], S) => Boolean =
    (self: Self[T, S], nextProps: Props[T], nextState: S) => {
      self.props.wrapped != nextProps.wrapped || self.state != nextState ||
        !Utils.shallowEqual(self.props.native, nextProps.native, React.WrappedProperty)
    },
    componentWillUpdate: (Self[T, S], Props[T], S) => Unit = null,
    componentDidUpdate: (Self[T, S], Props[T], S) => Unit = null,
    componentWillUnmount: Self[T, S] => Unit = null,
  ): ReactClass = {
    
    React.createClass[T, S](
      render = render,
      displayName = displayName,
      componentWillMount = componentWillMount,
      componentDidMount = componentDidMount,
      componentWillReceiveProps = componentWillReceiveProps,
      shouldComponentUpdate = shouldComponentUpdate,
      componentWillUpdate = componentWillUpdate,
      componentDidUpdate = componentDidUpdate,
      componentWillUnmount = componentWillUnmount,
      getInitialState = getInitialState
    )
  }
}
