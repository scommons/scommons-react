package scommons.react.showcase.app

import scommons.react.redux.task.{AbstractTask, FutureTask}
import scommons.react.showcase.app.counter.CounterActions.CounterChangeAction
import scommons.react.showcase.app.counter.CounterState
import scommons.react.test.TestSpec

import scala.concurrent.Future

class ShowcaseStateReducerSpec extends TestSpec {

  it should "return initial state" in {
    //when
    val result = ShowcaseStateReducer.reduce(None, "")
    
    //then
    inside(result) {
      case ShowcaseState(currentTask, counterState) =>
        currentTask shouldBe None
        counterState shouldBe CounterState()
    }
  }
  
  it should "set currentTask when TaskAction" in {
    //given
    val currTask = mock[AbstractTask]
    val state = ShowcaseState(Some(currTask), CounterState())
    val task = FutureTask("test task", Future.successful(0))
    
    //when
    val result = ShowcaseStateReducer.reduce(Some(state), CounterChangeAction(task))
    
    //then
    result.currentTask shouldBe Some(task)
  }
}
