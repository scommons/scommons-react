package scommons.react.showcase.hooks

import scommons.react._
import scommons.react.hooks._
import scommons.react.showcase.hooks.UseContextDemoApp._

object UseContextDemoApp {

  case class ContextObj(id: Int, login: String)
  
  val Context1 = ReactContext(defaultValue = 1)
  val Context2 = ReactContext(defaultValue = ContextObj(2, "user"))
  val Context3 = ReactContext(defaultValue = "defValue")
}

class UseContextDemoApp(value1: Int, value2: ContextObj) extends FunctionComponent[Unit] {
  
  protected def render(props: Props): ReactElement = {
    <(Context1.Provider)(^.contextValue := value1)(
      <(Context2.Provider)(^.contextValue := value2)(
        props.children
      )
    )
  }
}

class UseContextDemo(checkRender: () => Unit) extends FunctionComponent[Unit] {

  protected def render(props: Props): ReactElement = {
    checkRender()
    val value1 = useContext(Context1)
    val value2 = useContext(Context2)
    val value3 = useContext(Context3)

    <.div()(s"#1: $value1, #2: $value2, #3: $value3")
  }
}
