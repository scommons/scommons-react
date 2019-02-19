package scommons.react.raw

import io.github.shogowada.scalajs.reactjs.classes.ReactClass

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react", JSImport.Default)
object React extends js.Object {

  def memo(comp: ReactFunction): ReactClass = js.native
  
  def memo(comp: ReactFunction,
           areEqual: js.Function2[js.Dynamic, js.Dynamic, Boolean]): ReactClass = js.native
  
  def useState(initialState: js.Any): js.Array[js.Any] = js.native
  
  def useMemo(calculate: js.Function0[js.Any], inputs: js.Array[js.Any]): js.Any = js.native

}
