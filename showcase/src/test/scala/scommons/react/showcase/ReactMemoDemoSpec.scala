package scommons.react.showcase

import scommons.react.test.TestSpec
import scommons.react.test.raw.TestRenderer
import scommons.react.test.util.TestRendererUtils

class ReactMemoDemoSpec extends TestSpec with TestRendererUtils {

  it should "not re-render component if props are the same" in {
    //given
    var isRendered = false
    val comp = new ReactMemoDemo({ () =>
      isRendered = true
    })
    val props = ReactMemoDemoProps(List("test"))
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    isRendered shouldBe true
    isRendered = false
    
    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := props)())
    }
    
    //then
    isRendered shouldBe false
  }
  
  it should "not re-render component if props values hasn't changed" in {
    //given
    var isRendered = false
    var areEqualCalled = false
    val comp = new ReactCustomMemoDemo({ () =>
      isRendered = true
    }, { () =>
      areEqualCalled = true
    })
    val props = ReactMemoDemoProps(List("test"))
    val renderer = createTestRenderer(<(comp())(^.wrapped := props)())
    isRendered shouldBe true
    areEqualCalled shouldBe false
    isRendered = false
    areEqualCalled = false
    val newProps = props.copy(values = List("test"))
    System.identityHashCode(props) should not be System.identityHashCode(newProps)
    
    //when
    TestRenderer.act { () =>
      renderer.update(<(comp())(^.wrapped := newProps)())
    }
    
    //then
    isRendered shouldBe false
    areEqualCalled shouldBe true
  }
}
