package scommons.react

import scommons.react.raw.NativeRef

import scala.scalajs.js

sealed trait ReactRef[T] {

  private[react] def native: NativeRef

  def current: T = native.current.asInstanceOf[T]
  def current_=(value: T): Unit = native.current = value.asInstanceOf[js.Any]
}

object ReactRef {

  private[react] def apply[T](ref: NativeRef): ReactRef[T] = new ReactRef[T] {
    val native: NativeRef = ref
  }
  
  def create[T]: ReactRef[T] = apply(raw.React.createRef())
}
