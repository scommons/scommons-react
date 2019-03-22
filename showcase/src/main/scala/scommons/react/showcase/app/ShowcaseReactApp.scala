package scommons.react.showcase.app

import io.github.shogowada.scalajs.reactjs.ReactDOM
import org.scalajs.dom.document
import scommons.react._

object ShowcaseReactApp {

  def main(args: Array[String]): Unit = {
    val mountNode = document.getElementById("root")

    document.title = "scommons-react-showcase"

    ReactDOM.render(
      <(ShowcaseReactAppRoot())()(
        <.p()(
          "Hello World!"
        )
      ),
      mountNode
    )
  }
}

object ShowcaseReactAppRoot extends ClassComponent[Unit] {

  protected def create(): ReactClass = createClass[Unit](
    render = { self =>
      self.props.children
    }
  )
}
