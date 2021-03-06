package scommons.react.raw

import io.github.shogowada.scalajs.reactjs.classes.ReactClass
import io.github.shogowada.scalajs.reactjs.elements.ReactElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("./scommons/react/raw/CreateClass.js", JSImport.Namespace)
object CreateClass extends js.Object {
  
  def create(
              displayName: String,
              render: js.ThisFunction0[js.Dynamic, ReactElement],
              getInitialState: js.ThisFunction0[js.Dynamic, js.Dynamic],
              componentDidMount: js.ThisFunction0[js.Dynamic, Unit],
              shouldComponentUpdate: js.ThisFunction2[js.Dynamic, js.Dynamic, js.Dynamic, Boolean],
              componentDidUpdate: js.ThisFunction2[js.Dynamic, js.Dynamic, js.Dynamic, Unit],
              componentWillUnmount: js.ThisFunction0[js.Dynamic, Unit],
              componentDidCatch: js.ThisFunction2[js.Dynamic, js.Object, js.Dynamic, Unit]
            ): ReactClass = js.native
}
