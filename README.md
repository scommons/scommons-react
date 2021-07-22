
[![Build Status](https://travis-ci.com/scommons/scommons-react.svg?branch=master)](https://travis-ci.com/scommons/scommons-react)
[![Coverage Status](https://coveralls.io/repos/github/scommons/scommons-react/badge.svg?branch=master)](https://coveralls.io/github/scommons/scommons-react?branch=master)
[![scala-index](https://index.scala-lang.org/scommons/scommons-react/scommons-react-core/latest-by-scala-version.svg?targetType=Js)](https://index.scala-lang.org/scommons/scommons-react/scommons-react-core)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.29.svg)](https://www.scala-js.org)

## Scala Commons React
[Scala.js](https://www.scala-js.org) facades for common [React.js](https://reactjs.org) utilities and components.

It uses excellent [scalajs-reactjs](https://github.com/shogowada/scalajs-reactjs) binding/facade library.


### How to add it to your project

```scala
val scommonsReactVer = "1.0.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scommons.react" %%% "scommons-react-core" % scommonsReactVer,
  "org.scommons.react" %%% "scommons-react-dom" % scommonsReactVer,
  "org.scommons.react" %%% "scommons-react-redux" % scommonsReactVer,
  
  "org.scommons.react" %%% "scommons-react-test" % scommonsReactVer % "test",
  "org.scommons.react" %%% "scommons-react-test-dom" % scommonsReactVer % "test"
)
```

Latest `SNAPSHOT` version is published to [Sonatype Repo](https://oss.sonatype.org/content/repositories/snapshots/org/scommons/), just make sure you added
the proper dependency resolver to your `build.sbt` settings:
```scala
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
```

### How to use it

* Demo Application
  * [live demo](https://scommons.org/scommons-react/showcase/) => [How to Build and Run](showcase/README.md)
  * [AppMain](showcase/src/main/scala/scommons/react/showcase/app/ShowcaseReactApp.scala)
  * [AppState](showcase/src/main/scala/scommons/react/showcase/app/ShowcaseState.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/app/ShowcaseStateReducerSpec.scala)
  * [CounterActions](showcase/src/main/scala/scommons/react/showcase/app/counter/CounterActions.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/app/counter/CounterActionsSpec.scala)
  * [CounterController](showcase/src/main/scala/scommons/react/showcase/app/counter/CounterController.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/app/counter/CounterControllerSpec.scala)
  * [CounterPanel](showcase/src/main/scala/scommons/react/showcase/app/counter/CounterPanel.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/app/counter/CounterPanelSpec.scala)
  * [CounterState](showcase/src/main/scala/scommons/react/showcase/app/counter/CounterState.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/app/counter/CounterStateReducerSpec.scala)

* Components:
  * [ClassComponent](showcase/src/main/scala/scommons/react/showcase/ClassComponentDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/ClassComponentDemoSpec.scala)
  * [ErrorBoundary](showcase/src/main/scala/scommons/react/showcase/ErrorBoundaryDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/ErrorBoundaryDemoSpec.scala)
  * [FunctionComponent](showcase/src/main/scala/scommons/react/showcase/FunctionComponentDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/FunctionComponentDemoSpec.scala)
  * [React.memo](showcase/src/main/scala/scommons/react/showcase/ReactMemoDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/ReactMemoDemoSpec.scala)
  * [React.Fragment](showcase/src/main/scala/scommons/react/showcase/ReactFragmentDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/ReactFragmentDemoSpec.scala)

* React API
  * [React.createRef()](showcase/src/main/scala/scommons/react/showcase/ReactRefDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/ReactRefDemoSpec.scala)
  * [ReactDOM.createPortal()](showcase/src/main/scala/scommons/react/showcase/dom/ReactPortalDemo.scala)

* React Hooks:
  * [useState](showcase/src/main/scala/scommons/react/showcase/hooks/UseStateDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseStateDemoSpec.scala)
  * [useReducer](showcase/src/main/scala/scommons/react/showcase/hooks/UseReducerDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseReducerDemoSpec.scala)
  * [useMemo/useCallback](showcase/src/main/scala/scommons/react/showcase/hooks/UseMemoDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseMemoDemoSpec.scala)
  * [useContext](showcase/src/main/scala/scommons/react/showcase/hooks/UseContextDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseContextDemoSpec.scala)
  * [useRef](showcase/src/main/scala/scommons/react/showcase/hooks/UseRefDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseRefDemoSpec.scala)
  * [useEffect](showcase/src/main/scala/scommons/react/showcase/hooks/UseEffectDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseEffectDemoSpec.scala)
  * [useLayoutEffect](showcase/src/main/scala/scommons/react/showcase/hooks/UseLayoutEffectDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/hooks/UseLayoutEffectDemoSpec.scala)

### How to Build

To build and run all the tests use the following command:
```bash
sbt test
```

## Documentation

You can find more documentation [here](https://scommons.org/scommons-react)
