package scommons.react.showcase

import io.github.shogowada.scalajs.reactjs.React.Props
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import scommons.react._
import scommons.react.raw.React

import scala.scalajs.js

object ReactMemoDemo {

  /**
    * React.memo is a higher order component. It's similar to React.PureComponent but for
    * function components instead of classes.
    *
    * By default it will only shallowly compare complex objects in the props object.
    * If you want control over the comparison, you can also provide a custom comparison
    * function as the second argument.
    */
  def withMemo[T](component: FunctionComponent[T]): ReactClass = {
    React.memo(component())
  }

  def withCustomMemo[T](component: FunctionComponent[T])
                       (areEqual: (Props[T], Props[T]) => Boolean): ReactClass = {
    
    React.memo(component(), { (prev: js.Dynamic, next: js.Dynamic) =>
      areEqual(Props(prev), Props(next))
    })
  }
}
