package scommons.react.hooks

import scommons.react.raw

import scala.scalajs.js

trait UseReducer {
  
  def useReducer[S, A](reducer: (S, A) => S, init: => S): (S, js.Function1[A, Unit]) = {
    extractData(raw.React.useReducer(
      reducer.asInstanceOf[(js.Any, js.Any) => js.Any],
      ().asInstanceOf[js.Any],
      _ => {
        init.asInstanceOf[js.Any]
      }
    ))
  }
  
  private def extractData[S, A](data: js.Array[js.Any]): (S, js.Function1[A, Unit]) = {
    val value = data(0).asInstanceOf[S]
    val dispatch = data(1).asInstanceOf[js.Function1[A, Unit]]
    
    (value, dispatch)
  }
}
