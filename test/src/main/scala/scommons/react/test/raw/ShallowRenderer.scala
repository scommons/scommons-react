package scommons.react.test.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * You can think of the shallowRenderer as a "place" to render the component you're testing,
  * and from which you can extract the component's output.
  *
  * @see https://reactjs.org/docs/shallow-renderer.html
  */
@JSImport("react-test-renderer/shallow", JSImport.Default)
@js.native
class ShallowRenderer extends js.Object {

  /**
    * `shallowRenderer.render()` is similar to `ReactDOM.render()` but it doesn't require DOM
    * and only renders a single level deep. This means you can test components isolated
    * from how their children are implemented.
    */
  def render(element: js.Object): Unit = js.native

  /**
    * After `shallowRenderer.render()` has been called, you can use `shallowRenderer.getRenderOutput()`
    * to get the shallowly rendered output.
    *
    * <p>You can then begin to assert facts about the output.
    */
  def getRenderOutput(): ShallowRenderer.ComponentInstance = js.native
}

object ShallowRenderer {

  type ComponentInstance = js.Object with js.Dynamic

  def renderAndGetOutput(element: js.Object): ComponentInstance = {
    val shallowRenderer = new ShallowRenderer
    shallowRenderer.render(element)
    shallowRenderer.getRenderOutput()
  }
}
