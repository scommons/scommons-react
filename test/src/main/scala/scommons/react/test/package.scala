package scommons.react

package object test {

  type ShallowInstance = test.raw.ShallowInstance
  type ShallowRendererUtils = test.util.ShallowRendererUtils
  
  lazy val TestRenderer = test.raw.TestRenderer
  type TestInstance = test.raw.TestInstance
  type TestRendererUtils = test.util.TestRendererUtils
}
