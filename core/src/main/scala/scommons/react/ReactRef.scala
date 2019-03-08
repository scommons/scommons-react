package scommons.react

import scommons.react.raw.NativeRef

sealed trait ReactRef[T] {

  private[react] def native: NativeRef

  def current: T = native.current.asInstanceOf[T]
}

object ReactRef {

  def create[T]: ReactRef[T] = new ReactRef[T] {
    val native: NativeRef = raw.React.createRef()
  }
}
