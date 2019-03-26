package scommons.react.showcase

import scommons.react._

import scala.scalajs.js

object ErrorBoundaryDemo extends ClassComponent[Unit] {
  
  private case class ErrorBoundaryDemoState(error: Option[js.Object] = None,
                                            info: Option[js.Dynamic] = None)
  
  protected def create(): ReactClass = createClass[ErrorBoundaryDemoState](
    getInitialState = { _ =>
      ErrorBoundaryDemoState()
    },
    componentDidCatch = { (self, error, info) =>
      self.setState(ErrorBoundaryDemoState(Option(error), Option(info)))
    },
    render = { self =>
      self.state.error match {
        case None => self.props.children
        case Some(error) =>
          <.div()(
            s"Error: $error",
            s"Info: ${self.state.info.map(_.componentStack).getOrElse("")}"
          )
      }
    }
  )
}
