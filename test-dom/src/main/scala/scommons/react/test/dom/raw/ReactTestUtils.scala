package scommons.react.test.dom.raw

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * @see https://reactjs.org/docs/test-utils.html
  */
@JSImport("react-dom/test-utils", JSImport.Namespace, "React.addons.TestUtils")
@js.native
object ReactTestUtils extends js.Object {

  type Instance = js.Object with js.Dynamic

  /**
    * @see [[scommons.react.test.dom.raw.Simulate]]
    */
  val Simulate: Simulate with js.Dynamic = js.native

  /**
    * Render a React element into a detached DOM node in the document. This function requires a DOM.
    */
  def renderIntoDocument(element: js.Object): Instance = js.native

  /**
    * Returns `true` if `element` is any React element.
    */
  def isElement(element: js.Any): Boolean = js.native

  /**
    * Returns `true` if `element` is a React element whose type is of a React `componentClass`.
    */
  def isElementOfType(element: js.Any, componentClass: js.Object): Boolean = js.native

  /**
    * Returns `true` if `instance` is a DOM component (such as a `div` or `span`).
    */
  def isDOMComponent(instance: js.Any): Boolean = js.native

  /**
    * Returns `true` if `instance` is a user-defined component, such as a class or a function.
    */
  def isCompositeComponent(instance: js.Any): Boolean = js.native

  /**
    * Returns `true` if `instance` is a component whose type is of a React componentClass.
    */
  def isCompositeComponentWithType(instance: js.Any, componentClass: js.Object): Boolean = js.native

  /**
    * Traverse all components in `tree` and accumulate all components where `test(component)` is `true`.
    * This is not that useful on its own, but it's used as a primitive for other test utils.
    */
  def findAllInRenderedTree(tree: js.Any,
                            test: js.Function1[Instance, Boolean]): js.Array[Instance] = js.native

  /**
    * Finds all DOM elements of components in the rendered tree that are DOM components with the class name
    * matching `className`.
    */
  def scryRenderedDOMComponentsWithClass(tree: js.Any, className: String): js.Array[Instance] = js.native

  /**
    * Like `scryRenderedDOMComponentsWithClass()` but expects there to be one result, and returns that one result,
    * or throws exception if there is any other number of matches besides one.
    */
  def findRenderedDOMComponentWithClass(tree: js.Any, className: String): Instance = js.native

  /**
    * Finds all DOM elements of components in the rendered tree that are DOM components with the tag name
    * matching `tagName`.
    */
  def scryRenderedDOMComponentsWithTag(tree: js.Any, tagName: String): js.Array[Instance] = js.native

  /**
    * Like `scryRenderedDOMComponentsWithTag()` but expects there to be one result, and returns that one result,
    * or throws exception if there is any other number of matches besides one.
    */
  def findRenderedDOMComponentWithTag(tree: js.Any, tagName: String): Instance = js.native

  /**
    * Finds all instances of components with type equal to `componentClass`.
    */
  def scryRenderedComponentsWithType(tree: js.Any, componentClass: js.Object): js.Array[Instance] = js.native

  /**
    * Same as `scryRenderedComponentsWithType()` but expects there to be one result and returns that one result,
    * or throws exception if there is any other number of matches besides one.
    */
  def findRenderedComponentWithType(tree: js.Any, componentClass: js.Object): Instance = js.native
}
