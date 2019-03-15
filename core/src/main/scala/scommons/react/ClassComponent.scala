package scommons.react

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.React.{Props, Self}
import io.github.shogowada.scalajs.reactjs.utils.Utils

import scala.scalajs.js

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
      componentWillMount = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        if (componentWillMount != null) {
          componentWillMount(Self(native))
        }
      }),
      componentDidMount = js.ThisFunction.fromFunction1((native: js.Dynamic) => {
        if (componentDidMount != null) {
          componentDidMount(Self(native))
        }
      }),
      componentWillReceiveProps = js.ThisFunction.fromFunction2((native: js.Dynamic, nextProps: js.Dynamic) => {
        if (componentWillReceiveProps != null) {
          componentWillReceiveProps(Self(native), Props(nextProps))
        }
      }),
      shouldComponentUpdate = js.ThisFunction.fromFunction3((native: js.Dynamic, nextProps: js.Dynamic, nextState: js.Dynamic) => {
        shouldComponentUpdate(Self(native), Props(nextProps), React.stateFromNative(nextState))
      }),
      componentWillUpdate = js.ThisFunction.fromFunction3((native: js.Dynamic, nextProps: js.Dynamic, nextState: js.Dynamic) => {
        if (componentWillUpdate != null) {
          componentWillUpdate(Self(native), Props(nextProps), React.stateFromNative(nextState))
        }
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
