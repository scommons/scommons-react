package scommons.react.showcase

import scommons.react._

case class FunctionComponentDemoProps(values: List[String])

object FunctionComponentDemo extends FunctionComponent[FunctionComponentDemoProps] {

  protected def render(props: Props): ReactElement = {
    val data = props.wrapped

    <.div(^.className := "root")(
      data.values.zipWithIndex.map { case (value, index) =>
        <.div(^.key := s"$index")(value)
      },
      props.children
    )
  }
}
