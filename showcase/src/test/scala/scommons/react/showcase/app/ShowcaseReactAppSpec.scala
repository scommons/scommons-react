package scommons.react.showcase.app

import org.scalajs.dom
import scommons.react.test.TestSpec
import scommons.react.test.dom.util.TestDOMUtils

class ShowcaseReactAppSpec extends TestSpec with TestDOMUtils {

  it should "render app in dom" in {
    //given
    domContainer.setAttribute("id", "root")
    
    //when
    ShowcaseReactApp.main(Array[String]())

    //then
    dom.document.title shouldBe "scommons-react-showcase"
    
    assertDOMElement(domContainer, <.div(^.id := "root")(
      <.p()(
        "Welcome to the React Counter showcase example App." +
          " Use buttons bellow to increase/decrease the counter:"
      ),
      <.p()("0"),
      <.button()("+"),
      <.button()("-")
    ))
  }
}
