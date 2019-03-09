package scommons.react.hooks

import scommons.react.ReactRef
import scommons.react.raw.React

import scala.scalajs.js

trait UseRef {

  def useRef[T](initialValue: T): ReactRef[T] = {
    ReactRef[T](React.useRef(initialValue.asInstanceOf[js.Any]))
  }
}
