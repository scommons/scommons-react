package scommons.react

trait UiComponent[T] {

  def apply(): ReactClass = reactClass
  
  private lazy val reactClass: ReactClass = create()

  protected def create(): ReactClass

  protected def displayName: String = {
    val name = getClass.getName
    name.drop(name.lastIndexOf('.') + 1)
      .stripSuffix("$")
  }
}
