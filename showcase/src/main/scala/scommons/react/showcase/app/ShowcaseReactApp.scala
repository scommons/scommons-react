package scommons.react.showcase.app

import io.github.shogowada.scalajs.reactjs.ReactDOM
import org.scalajs.dom.document
import scommons.react._
import scommons.react.showcase.ErrorBoundaryDemo

object ShowcaseReactApp {

  def main(args: Array[String]): Unit = {
    val mountNode = document.getElementById("root")

    document.title = "scommons-react-showcase"

    ReactDOM.render(
      <(ErrorBoundaryDemo())()(
        <.p()(
          "Hello World!"
        )
      ),
      mountNode
    )
  }
}
