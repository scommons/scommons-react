package scommons.react

package object test {

  @deprecated("Will be removed soon, use TestInstance instead", "0.5.1")
  type ShallowInstance = test.raw.ShallowInstance

  @deprecated("Will be removed soon, use TestRendererUtils instead", "0.5.1")
  type ShallowRendererUtils = test.util.ShallowRendererUtils
  
  lazy val TestRenderer = test.raw.TestRenderer
  type TestInstance = test.raw.TestInstance
  type TestRendererUtils = test.util.TestRendererUtils
}
