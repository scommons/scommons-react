package scommons.react.raw

import io.github.shogowada.scalajs.reactjs.classes.ReactClass

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react", JSImport.Default)
object React extends js.Object {

  val Fragment: ReactClass = js.native
  
  def memo(comp: ReactFunction): ReactClass = js.native
  def memo(comp: ReactFunction,
           areEqual: js.Function2[js.Dynamic, js.Dynamic, Boolean]): ReactClass = js.native
  
  def createContext(defaultValue: js.Any): NativeContext = js.native
  def createRef(): NativeRef = js.native
  
  //////////////////////////////////////////////////////////////////////////////
  // hooks

  def useState(initialState: js.Any): js.Array[js.Any] = js.native
  def useMemo(calculate: js.Function0[js.Any], inputs: js.Array[js.Any]): js.Any = js.native
  def useContext(context: NativeContext): js.Any = js.native
  def useRef(initialValue: js.Any): NativeRef = js.native
}

@js.native
trait NativeContext extends js.Object {

  val Provider: ReactClass = js.native
}

@js.native
trait NativeRef extends js.Object {

  def current: js.Any = js.native
  def current_=(value: js.Any): Unit = js.native
}
