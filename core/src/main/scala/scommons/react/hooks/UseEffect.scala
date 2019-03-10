package scommons.react.hooks

import scommons.react.raw

import scala.scalajs.js

trait UseEffect {
  
  def useEffect(didUpdate: () => js.Any): Unit = {
    raw.React.useEffect(didUpdate)
  }
  
  def useEffect(didUpdate: () => js.Any, dependencies: List[js.Any]): Unit = {
    raw.React.useEffect(didUpdate, js.Array(dependencies: _*))
  }
}
