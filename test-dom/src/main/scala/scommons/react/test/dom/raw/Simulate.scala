package scommons.react.test.dom.raw

import scommons.react.test.dom.raw.Simulate.undefined

import scala.scalajs.js

/**
  * Example:
  *
  * val button = findRenderedDOMComponentWithTag(comp, "button")
  * ReactTestUtils.Simulate.click(button, js.Dynamic.literal(
  *   clientX = 1,
  *   clientY = 2
  * ))
  *
  * <br><b>Note</b>
  * <br>You will have to provide any event property that you're using in your component
  * (e.g. altKey, buttons, etc...) as React is not creating any of these for you.
  */
//noinspection AccessorLikeMethodIsUnit
@js.native
trait Simulate extends js.Object {

  // Animation Events
  def animationStart(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def animationEnd(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def animationIteration(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Clipboard Events
  def copy(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def cut(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def paste(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Composition Events
  def compositionEnd(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def compositionStart(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def compositionUpdate(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Focus Events
  def focus(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def blur(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Form Events
  def change(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def input(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def submit(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Image Events
  def load(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  // onError conflicts with Media Events. Conflicts are treated specially at the bottom of this trait.
  // def error(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Keyboard Events
  def keyDown(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def keyPress(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def keyUp(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Media Events
  def abort(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def canPlay(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def canPlayThrough(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def durationChange(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def emptied(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def encrypted(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def ended(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  // onError conflicts with Image Events. Conflicts are treated specially at the bottom of this trait.
  // def error(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def loadedData(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def loadedMetadata(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def loadStart(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def pause(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def play(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def playing(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def progress(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def rateChange(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def seeked(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def seeking(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def stalled(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def suspend(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def timeUpdate(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def volumeChange(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def waiting(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Mouse Events
  def click(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def contextMenu(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def doubleClick(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def drag(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragEnd(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragEnter(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragExit(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragLeave(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragOver(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def dragStart(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def drop(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseDown(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseEnter(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseLeave(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseMove(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseOut(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseOver(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def mouseUp(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Selection Events
  def select(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Touch Events
  def touchCancel(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def touchEnd(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def touchMove(node: js.Object, eventData: js.Object = undefined): Unit = js.native
  def touchStart(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Transition Events
  def transitionEnd(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // UI Events
  def scroll(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Wheel Events
  def wheel(node: js.Object, eventData: js.Object = undefined): Unit = js.native

  // Events that conflicted with other events
  def error(node: js.Object, eventData: js.Object = undefined): Unit = js.native
}

object Simulate {

  private val undefined: js.Object = ().asInstanceOf[js.Object]
}
