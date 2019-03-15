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
              componentWillMount: js.ThisFunction0[js.Dynamic, Unit],
              componentDidMount: js.ThisFunction0[js.Dynamic, Unit],
              componentWillReceiveProps: js.ThisFunction1[js.Dynamic, js.Dynamic, Unit],
              shouldComponentUpdate: js.ThisFunction2[js.Dynamic, js.Dynamic, js.Dynamic, Boolean],
              componentWillUpdate: js.ThisFunction2[js.Dynamic, js.Dynamic, js.Dynamic, Unit],
              componentDidUpdate: js.ThisFunction2[js.Dynamic, js.Dynamic, js.Dynamic, Unit],
              componentWillUnmount: js.ThisFunction0[js.Dynamic, Unit]
            ): ReactClass = js.native
}
