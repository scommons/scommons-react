package scommons.react.hooks

import scommons.react.raw.React

import scala.scalajs.js

trait UseMemo {

  def useMemo[T](calculate: () => T, inputs: js.Any*): T = {
    React.useMemo(calculate.asInstanceOf[() => js.Any], js.Array(inputs: _*)).asInstanceOf[T]
  }
}
