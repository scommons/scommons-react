package scommons.react.showcase

import scommons.react._

case class ReactMemoDemoProps(values: List[String])

class ReactMemoDemo(checkRender: () => Unit) extends FunctionComponent[ReactMemoDemoProps] {
  /**
    * React.memo is a higher order component. It's similar to React.PureComponent but for
    * function components instead of classes.
    */
  override protected def create(): ReactClass = {
    ReactMemo(super.create())
  }
  
  protected def render(props: Props): ReactElement = {
    checkRender()
    <.div.empty
  }
}

class ReactCustomMemoDemo(checkRender: () => Unit,
                          checkAreEqual: () => Unit
                         ) extends FunctionComponent[ReactMemoDemoProps] {
  /**
    * By default it will only shallowly compare complex objects in the props object.
    * If you want control over the comparison, you can also provide a custom comparison
    * function as the second argument.
    */
  override protected def create(): ReactClass = {
    ReactMemo[Props](super.create(), { (prevProps, nextProps) =>
      checkAreEqual()
      prevProps.wrapped == nextProps.wrapped
    })
  }

  protected def render(props: Props): ReactElement = {
    checkRender()
    <.div.empty
  }
}
