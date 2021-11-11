package scommons.react.redux.task

import scommons.react.redux.Action

trait TaskAction extends Action {

  def task: AbstractTask
}
