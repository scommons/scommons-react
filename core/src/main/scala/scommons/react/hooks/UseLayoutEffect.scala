package scommons.react.hooks

import scommons.react.raw

import scala.scalajs.js

trait UseLayoutEffect {
  
  def useLayoutEffect(didUpdate: () => js.Any): Unit = {
    raw.React.useLayoutEffect(didUpdate)
  }
  
  def useLayoutEffect(didUpdate: () => js.Any, dependencies: List[js.Any]): Unit = {
    raw.React.useLayoutEffect(didUpdate, js.Array(dependencies: _*))
  }
}
