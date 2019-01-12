package scommons.react

import io.github.shogowada.scalajs.reactjs.classes.ReactClass

trait UiComponent[T] {

  type PropsType = T

  def apply(): ReactClass = reactClass
  
  private lazy val reactClass: ReactClass = create()

  protected def create(): ReactClass
}
