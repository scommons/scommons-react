package scommons.react.hooks

import scommons.react.raw

import scala.scalajs.js

trait UseState {
  
  def useState[T](initialState: T): (T, js.Function1[T, Unit]) = {
    extractData(raw.React.useState(initialState.asInstanceOf[js.Any]))
  }
  
  def useState[T](initialState: () => T): (T, js.Function1[T, Unit]) = {
    extractData(raw.React.useState(initialState))
  }
  
  def useStateUpdater[T](initialState: T): (T, js.Function1[js.Function1[T, T], Unit]) = {
    extractUpdater(raw.React.useState(initialState.asInstanceOf[js.Any]))
  }
  
  def useStateUpdater[T](initialState: () => T): (T, js.Function1[js.Function1[T, T], Unit]) = {
    extractUpdater(raw.React.useState(initialState))
  }
  
  private def extractData[T](data: js.Array[js.Any]): (T, js.Function1[T, Unit]) = {
    val value = data(0).asInstanceOf[T]
    val setState = data(1).asInstanceOf[js.Function1[T, Unit]]
    
    (value, setState)
  }
  
  private def extractUpdater[T](data: js.Array[js.Any]): (T, js.Function1[js.Function1[T, T], Unit]) = {
    val value = data(0).asInstanceOf[T]
    val setState = data(1).asInstanceOf[js.Function1[js.Function1[T, T], Unit]]
    
    (value, setState)
  }
}
