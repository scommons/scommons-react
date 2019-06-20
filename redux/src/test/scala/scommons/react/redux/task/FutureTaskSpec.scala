package scommons.react.redux.task

import org.scalamock.scalatest.AsyncMockFactory
import org.scalatest.{AsyncFlatSpec, Matchers, Succeeded}

import scala.concurrent.{ExecutionContext, Future}
import scala.scalajs.concurrent.JSExecutionContext
import scala.util.{Success, Try}

class FutureTaskSpec extends AsyncFlatSpec
  with Matchers
  with AsyncMockFactory {

  implicit override val executionContext: ExecutionContext = JSExecutionContext.queue

  it should "call future.onComplete when onComplete" in {
    //given
    val future = Future.successful(())
    val task = FutureTask("test message", future)
    val f = mockFunction[Try[_], Unit]
    
    //then
    f.expects(Success(()))
    
    //when
    task.onComplete(f)

    future.map(_ => Succeeded)
  }
}
