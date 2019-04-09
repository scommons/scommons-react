package scommons.react.showcase

import scommons.react._

case class ClassComponentDemoProps(propValue: String)

case class ClassComponentDemoState(stateValue: String)

class ClassComponentDemo(
  componentDidMount: (ClassComponentDemoProps, ClassComponentDemoState) => ClassComponentDemoState,
  shouldComponentUpdate: (ClassComponentDemoProps, ClassComponentDemoState, ClassComponentDemoProps, ClassComponentDemoState) => Boolean,
  componentDidUpdate: (ClassComponentDemoProps, ClassComponentDemoState, ClassComponentDemoProps, ClassComponentDemoState) => Unit,
  componentWillUnmount: (ClassComponentDemoProps, ClassComponentDemoState) => Unit,
  rendered: (ClassComponentDemoProps, ClassComponentDemoState) => Unit
) extends ClassComponent[ClassComponentDemoProps] {
  
  protected def create(): ReactClass = createClass[ClassComponentDemoState](
    getInitialState = { self =>
      ClassComponentDemoState(s"initial: ${self.props.wrapped.propValue}")
    },
    componentDidMount = { self =>
      val newState = componentDidMount(self.props.wrapped, self.state)
      if (newState != self.state) {
        self.setState(newState)
      }
    },
    shouldComponentUpdate = { (self, nextProps, nextState) =>
      shouldComponentUpdate(self.props.wrapped, self.state, nextProps.wrapped, nextState)
    },
    componentDidUpdate = { (self, prevProps, prevState) =>
      componentDidUpdate(self.props.wrapped, self.state, prevProps.wrapped, prevState)
    },
    componentWillUnmount = { self =>
      componentWillUnmount(self.props.wrapped, self.state)
    },
    render = { self =>
      rendered(self.props.wrapped, self.state)
      
      val data = self.props.wrapped
  
      <.div()(
        s"${data.propValue}",
        s"${self.state.stateValue}",
        self.props.children
      )
    }
  )
}
