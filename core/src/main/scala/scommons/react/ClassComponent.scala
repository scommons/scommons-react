package scommons.react

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.React.{Props, Self}

import scala.scalajs.js

trait ClassComponent[T] extends UiComponent[T] {

  protected def createClass[S](
    render: Self[T, S] => ReactElement,
    getInitialState: Self[T, S] => S = null,
    componentDidMount: Self[T, S] => Unit = null,
    shouldComponentUpdate: (Self[T, S], Props[T], S) => Boolean = null,
    componentDidUpdate: (Self[T, S], Props[T], S) => Unit = null,
    componentWillUnmount: Self[T, S] => Unit = null,
    componentDidCatch: (Self[T, S], js.Object, js.Dynamic) => Unit = null
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
        if (shouldComponentUpdate != null) {
          shouldComponentUpdate(Self(native), Props(nextProps), React.stateFromNative(nextState))
        }
        else true
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
      }),
      componentDidCatch = {
        if (componentDidCatch != null) {
          js.ThisFunction.fromFunction3((native: js.Dynamic, error: js.Object, info: js.Dynamic) => {
            componentDidCatch(Self(native), error, info)
          })
        }
        else null
      }
    )
  }
}
