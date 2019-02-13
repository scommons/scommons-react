package scommons.react.test.dom

import io.github.shogowada.scalajs.reactjs.ReactDOM
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import org.scalajs.dom.{Element, document}
import org.scalatest.{BeforeAndAfterEach, Suite}
import scommons.react.test.dom.raw.ReactTestUtils

trait TestDomSpec extends Suite with BeforeAndAfterEach {

  protected var domContainer: Element = _
  
  override protected def beforeEach(): Unit =  {
    domContainer = document.createElement("div")
    document.body.appendChild(domContainer)
  }

  override protected def afterEach(): Unit =  {
    document.body.removeChild(domContainer)
    domContainer = null
  }

  def render(element: ReactElement): Unit = {
    ReactTestUtils.act { () =>
      ReactDOM.render(element, domContainer)
    }
  }
  
  def fireEvent(block: => Unit): Unit = {
    ReactTestUtils.act { () =>
      block
    }
  }
}
