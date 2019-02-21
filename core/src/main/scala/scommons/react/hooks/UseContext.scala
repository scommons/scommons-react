package scommons.react.hooks

import scommons.react.ReactContext
import scommons.react.raw.React

trait UseContext {

  def useContext[T](ctx: ReactContext[T]): T = {
    React.useContext(ctx.native).asInstanceOf[T]
  }
}
