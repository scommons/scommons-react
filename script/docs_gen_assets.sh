#!/bin/sh

set -e

sbt "project scommons-react-showcase" clean fullOptJS::webpack
cp "showcase/target/scala-2.12/scalajs-bundler/main/scommons-react-showcase-opt-library.js" "docs/showcase/assets/scommons-react-showcase-opt-library.js"
cp "showcase/target/scala-2.12/scalajs-bundler/main/scommons-react-showcase-opt-loader.js" "docs/showcase/assets/scommons-react-showcase-opt-loader.js"
cp "showcase/target/scala-2.12/scalajs-bundler/main/scommons-react-showcase-opt.js" "docs/showcase/assets/scommons-react-showcase-opt.js"
