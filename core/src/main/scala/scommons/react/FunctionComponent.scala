package scommons.react

import io.github.shogowada.scalajs.reactjs

import scala.scalajs.js

trait FunctionComponent[T] extends UiComponent[T] {

  protected def render(props: Props): ReactElement

  override protected def create(): ReactClass = {
    val render0: js.Function1[js.Dynamic, ReactElement] = { props =>
      render(reactjs.React.Props(props))
    }

    // set function name, using approach found here:
    //   http://marcosc.com/2012/03/dynamic-function-names-in-javascript/
    //
    val fn = new js.Function("render", "return function " + displayName + "(props){ return render(props) };")
      .call(null, render0)

    fn.asInstanceOf[js.Dynamic].displayName = displayName
    fn.asInstanceOf[ReactClass]
  }
}
