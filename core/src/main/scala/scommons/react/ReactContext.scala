package scommons.react

import scommons.react.raw.NativeContext

import scala.scalajs.js

sealed trait ReactContext[T] {

  private[react] def native: NativeContext

  def Provider: ReactClass = native.Provider
}

object ReactContext {

  def apply[T](defaultValue: T): ReactContext[T] = new ReactContext[T] {
    val native: NativeContext = raw.React.createContext(defaultValue.asInstanceOf[js.Any])
  }
}
