package scommons.react.test

import scommons.react._

import scala.scalajs.js

object TestErrorBoundary extends ClassComponent[Unit] {

  private case class TestErrorBoundaryState(error: Option[js.Object] = None)

  protected def create(): ReactClass = createClass[TestErrorBoundaryState](
    getInitialState = { _ =>
      TestErrorBoundaryState()
    },
    componentDidCatch = { (self, error, _) =>
      self.setState(TestErrorBoundaryState(Option(error)))
    },
    render = { self =>
      self.state.error match {
        case None => self.props.children
        case Some(error) =>
          <.div()(
            s"$error"
          )
      }
    }
  )
}
