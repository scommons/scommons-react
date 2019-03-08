package scommons.react.showcase

import scommons.react._

case class ReactFragmentDemoProps(values: List[String])

object ReactFragmentDemo extends FunctionComponent[ReactFragmentDemoProps] {
  
  protected def render(props: Props): ReactElement = {
    val data = props.wrapped
    
    // top level React.Fragment
    <.>()(
      data.values.zipWithIndex.map {
        case (value, index) =>
          // example of passing key to React.Fragment
          <.>(^.key := s"$index")(
            <.div()(s"Item #${index + 1}"),
            <.div()(value)
          )
      }
    )
  }
}
