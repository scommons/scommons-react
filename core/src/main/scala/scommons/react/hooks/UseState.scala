package scommons.react.hooks

import scommons.react.hooks.UseState.SetState
import scommons.react.raw

import scala.scalajs.js

trait UseState {
  
  def useState[T](initialState: T): (T, SetState[T]) = {
    getStateData(raw.React.useState(initialState.asInstanceOf[js.Any]))
  }
  
  def useState[T](initialState: () => T): (T, SetState[T]) = {
    getStateData(raw.React.useState(initialState))
  }
  
  private def getStateData[T](data: js.Array[js.Any]): (T, SetState[T]) = {
    val value = data.head.asInstanceOf[T]
    val setState = data(1).asInstanceOf[js.Function1[js.Any, Unit]]
    
    value -> new SetState[T] {
      override def apply(value: T): Unit = setState(value.asInstanceOf[js.Any])
      override def apply(updater: T => T): Unit = setState(updater)
    }
  }
}

object UseState {

  sealed trait SetState[T] {
    def apply(value: T): Unit
    def apply(updater: T => T): Unit
  }
}
