package scommons.react

import io.github.shogowada.scalajs.reactjs
import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import scommons.react.raw.ReactFunction

import scala.scalajs.js

trait FunctionComponent[T] extends UiComponent[T] {

  type Props = reactjs.React.Props[T]

  protected def name: String = {
    val name = getClass.getName
    name.drop(name.lastIndexOf('.') + 1)
      .stripSuffix("$")
  }
  
  protected def render(props: Props): ReactElement

  override def apply(): ReactFunction = {
    super.apply().asInstanceOf[ReactFunction]
  }
  
  override protected def create(): ReactClass = {
    val render0: js.Function1[js.Dynamic, ReactElement] = { props =>
      render(reactjs.React.Props(props))
    }

    // set function name, using approach found here:
    //   http://marcosc.com/2012/03/dynamic-function-names-in-javascript/
    //
    val fn = new js.Function("render", "return function " + name + "(props){ return render(props) };")
      .call(null, render0)

    fn.asInstanceOf[ReactClass]
  }
}
