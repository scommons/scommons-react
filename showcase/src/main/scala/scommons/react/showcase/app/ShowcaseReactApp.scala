package scommons.react.showcase.app

import io.github.shogowada.scalajs.reactjs.ReactDOM
import io.github.shogowada.scalajs.reactjs.redux.ReactRedux._
import io.github.shogowada.scalajs.reactjs.redux.Redux
import org.scalajs.dom.document
import scommons.react._
import scommons.react.showcase.ErrorBoundaryDemo
import scommons.react.showcase.app.counter.{CounterActions, CounterController}

object ShowcaseReactApp {

  def main(args: Array[String]): Unit = {
    val mountNode = document.getElementById("root")

    document.title = "scommons-react-showcase"

    val store = Redux.createStore(ShowcaseStateReducer.reduce)
    
    val counterActions = new CounterActions
    val counterController = new CounterController(counterActions)

    ReactDOM.render(
      <.Provider(^.store := store)(
        <(ErrorBoundaryDemo())()(
          <(counterController()).empty
        )
      ),
      mountNode
    )
  }
}
