package scommons.react.raw

import io.github.shogowada.scalajs.reactjs.classes.ReactClass

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("react", JSImport.Default)
object React extends js.Object {

  val Fragment: ReactClass = js.native
  
  def memo(comp: ReactClass): ReactClass = js.native
  def memo(comp: ReactClass, areEqual: js.Function2[js.Dynamic, js.Dynamic, Boolean]): ReactClass = js.native
  
  def createContext(defaultValue: js.Any): NativeContext = js.native
  def createRef(): NativeRef = js.native
  
  //////////////////////////////////////////////////////////////////////////////
  // hooks

  def useState(initialState: js.Any): js.Array[js.Any] = js.native
  
  def useReducer(reducer: js.Function2[js.Any, js.Any, js.Any],
                 initialArg: js.Any,
                 init: js.Function1[js.Any, js.Any]): js.Array[js.Any] = js.native
  
  def useContext(context: NativeContext): js.Any = js.native
  def useRef(initialValue: js.Any): NativeRef = js.native
  
  def useMemo(calculate: js.Function0[js.Any], dependencies: js.Array[js.Any]): js.Any = js.native
  
  def useEffect(didUpdate: js.Function0[js.Any]): Unit = js.native
  def useEffect(didUpdate: js.Function0[js.Any], dependencies: js.Array[js.Any]): Unit = js.native
  
  def useLayoutEffect(didUpdate: js.Function0[js.Any]): Unit = js.native
  def useLayoutEffect(didUpdate: js.Function0[js.Any], dependencies: js.Array[js.Any]): Unit = js.native
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
