package scommons.react.showcase.app.counter

import scommons.react.showcase.app.counter.CounterActions._
import scommons.react.test.TestSpec

class CounterStateReducerSpec extends TestSpec {

  private val reduce = CounterStateReducer.apply _
  
  it should "return default state when state is None" in {
    //when & then
    reduce(None, "") shouldBe CounterState()
  }
  
  it should "set counter value when CounterChangedAction" in {
    //when & then
    reduce(Some(CounterState()), CounterChangedAction(123)) shouldBe {
      CounterState(123)
    }
  }
}
