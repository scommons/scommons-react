const merge = require("webpack-merge")

const generatedConfig = require('./scalajs.webpack.config')
const commonBabelConfig = require("./sc-babel.webpack.config.js")

module.exports = merge(generatedConfig, commonBabelConfig)
