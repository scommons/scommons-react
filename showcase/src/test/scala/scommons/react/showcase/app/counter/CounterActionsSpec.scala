package scommons.react.showcase.app.counter

import scommons.react.redux.task.FutureTask
import scommons.react.showcase.app.counter.CounterActions._
import scommons.react.test.dom.AsyncTestSpec

class CounterActionsSpec extends AsyncTestSpec {

  it should "dispatch CounterChangedAction when companyCreate" in {
    //given
    val actions = new CounterActions
    val dispatch = mockFunction[Any, Any]
    val expectedRes = 3

    //then
    dispatch.expects(CounterChangedAction(expectedRes))
    
    //when
    val CounterChangeAction(FutureTask(message, future)) =
      actions.changeCounter(dispatch, 2, 1)
    
    //then
    message shouldBe "Changing Counter"
    future.map { res =>
      res shouldBe expectedRes
    }
  }
}
