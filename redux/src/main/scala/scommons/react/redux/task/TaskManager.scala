package scommons.react.redux.task

import scommons.react._
import scommons.react.hooks._

import scala.scalajs.js
import scala.scalajs.js.{Error, JavaScriptException}
import scala.util.{Failure, Success, Try}

case class TaskManagerProps(startTask: Option[AbstractTask])

/**
  * Handles status of running tasks.
  */
object TaskManager extends FunctionComponent[TaskManagerProps] {

  var uiComponent: UiComponent[TaskManagerUiProps] = _
  
  var errorHandler: PartialFunction[Try[_], (Option[String], Option[String])] = PartialFunction.empty
  
  private case class TaskManagerState(taskCount: Int = 0,
                                      status: Option[String] = None,
                                      error: Option[String] = None,
                                      errorDetails: Option[String] = None)

  protected def render(compProps: Props): ReactElement = {
    val props = compProps.wrapped
    val (state, setState) = useStateUpdater(() => TaskManagerState())
    
    if (uiComponent == null) {
      throw JavaScriptException(Error("TaskManager.uiComponent is not specified"))
    }
    
    useEffect({ () =>
      props.startTask.foreach { task =>
        onTaskStart(setState, task)
      }
    }, List(props.startTask match {
      case None => js.undefined
      case Some(task) => task.asInstanceOf[js.Any]
    }))
    
    <(uiComponent())(^.wrapped := TaskManagerUiProps(
      showLoading = state.taskCount > 0,
      status = state.status,
      onHideStatus = { () =>
        setState(_.copy(status = None))
      },
      error = state.error,
      errorDetails = state.errorDetails,
      onCloseErrorPopup = { () =>
        setState(_.copy(error = None, errorDetails = None))
      }
    ))()
  }

  private def onTaskStart(setState: js.Function1[js.Function1[TaskManagerState, TaskManagerState], Unit],
                          task: AbstractTask): Unit = {

    task.onComplete { value: Try[_] =>
      onTaskFinish(setState, task, value)
    }

    setState(s => s.copy(
      taskCount = s.taskCount + 1,
      status = Some(s"${task.message}...")
    ))
  }

  private def onTaskFinish(setState: js.Function1[js.Function1[TaskManagerState, TaskManagerState], Unit],
                           task: AbstractTask,
                           value: Try[_]): Unit = {

    val durationMillis = System.currentTimeMillis() - task.startTime
    val statusMessage = s"${task.message}...Done ${formatDuration(durationMillis)} sec."

    def defaultErrorHandler(value: Try[_]): (Option[String], Option[String]) = value match {
      case Success(_) => (None, None)
      case Failure(e) => (Some(e.toString), Some(printStackTrace(e)))
    }

    val (error, errorDetails) = errorHandler.applyOrElse(value, defaultErrorHandler)

    setState(s => s.copy(
      taskCount = s.taskCount - 1,
      status = Some(statusMessage),
      error = error,
      errorDetails = errorDetails
    ))
  }

  private[task] def formatDuration(durationMillis: Long): String = {
    "%.3f".format(durationMillis / 1000.0)
  }

  private[task] def printStackTrace(x: Throwable): String = {
    val sb = new StringBuilder(x.toString)
    val trace = x.getStackTrace
    for (t <- trace) {
      sb.append("\n\tat&nbsp").append(t)
    }

    val cause = x.getCause
    if (cause != null) {
      printStackTraceAsCause(sb, cause, trace)
    }

    sb.toString
  }

  /**
    * Print stack trace as a cause for the specified stack trace.
    */
  private def printStackTraceAsCause(sb: StringBuilder,
                                     cause: Throwable,
                                     causedTrace: Array[StackTraceElement]): Unit = {

    // Compute number of frames in common between this and caused
    val trace = cause.getStackTrace
    var m = trace.length - 1
    var n = causedTrace.length - 1
    while (m >= 0 && n >= 0 && trace(m) == causedTrace(n)) {
      m -= 1
      n -= 1
    }

    val framesInCommon = trace.length - 1 - m
    sb.append("\nCaused by: " + cause)

    for (i <- 0 to m) {
      sb.append("\n\tat&nbsp").append(trace(i))
    }

    if (framesInCommon != 0) {
      sb.append("\n\t...&nbsp").append(framesInCommon).append("&nbspmore")
    }

    // Recurse if we have a cause
    val ourCause = cause.getCause
    if (ourCause != null) {
      printStackTraceAsCause(sb, ourCause, trace)
    }
  }
}
