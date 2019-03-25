package scommons.react

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.React.{Props, Self}
import io.github.shogowada.scalajs.reactjs.utils.Utils

import scala.scalajs.js

trait ClassComponent[T] extends UiComponent[T] {

  protected def createClass[S](
    render: Self[T, S] => ReactElement,
    getInitialState: Self[T, S] => S = null,
    componentDidMount: Self[T, S] => Unit = null,
    shouldComponentUpdate: (Self[T, S], Props[T], S) => Boolean =
    (self: Self[T, S], nextProps: Props[T], nextState: S) => {
      self.props.wrapped != nextProps.wrapped || self.state != nextState ||
        !Utils.shallowEqual(self.props.native, nextProps.native, React.WrappedProperty)
    },
    componentDidUpdate: (Self[T, S], Props[T], S) => Unit = null,
    componentWillUnmount: Self[T, S] => Unit = null,
  ): ReactClass = {
    
    raw.CreateClass.create(
      displayName = displayName,
      render = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        render(Self(native))
      }),
      getInitialState = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        if (getInitialState != null) {
          React.stateToNative(getInitialState(Self(native)))
        } else {
          React.stateToNative(())
        }
      }),
      componentDidMount = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        if (componentDidMount != null) {
          componentDidMount(Self(native))
        }
      }),
      shouldComponentUpdate = js.ThisFunction.fromFunction3((native: js.Dynamic, nextProps: js.Dynamic, nextState: js.Dynamic) => {
        shouldComponentUpdate(Self(native), Props(nextProps), React.stateFromNative(nextState))
      }),
      componentDidUpdate = js.ThisFunction.fromFunction3((native: js.Dynamic, prevProps: js.Dynamic, prevState: js.Dynamic) => {
        if (componentDidUpdate != null) {
          componentDidUpdate(Self(native), Props(prevProps), React.stateFromNative(prevState))
        }
      }),
      componentWillUnmount = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        if (componentWillUnmount != null) {
          componentWillUnmount(Self(native))
        }
      })
    )
  }
}
