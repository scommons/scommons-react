package scommons.react

import io.github.shogowada.scalajs.reactjs.React.Props

import scala.scalajs.js

object ReactMemo {

  def apply(comp: ReactClass): ReactClass = {
    raw.React.memo(comp)
  }
  
  def apply[T](comp: ReactClass, areEqual: (Props[T], Props[T]) => Boolean): ReactClass = {
    raw.React.memo(comp, { (prev: js.Dynamic, next: js.Dynamic) =>
      areEqual(Props(prev), Props(next))
    })
  }
}
