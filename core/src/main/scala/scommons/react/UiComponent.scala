package scommons.react

import io.github.shogowada.scalajs.reactjs

trait UiComponent[T] {

  type Props = reactjs.React.Props[T]

  def apply(): ReactClass = reactClass
  
  private lazy val reactClass: ReactClass = create()

  protected def create(): ReactClass

  protected def displayName: String = {
    val name = getClass.getName
    name.drop(name.lastIndexOf('.') + 1)
      .stripSuffix("$")
  }
}
