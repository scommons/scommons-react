
module.exports = {
  stats: {
    children: false // disable child plugin/loader logging
  },
  
  module: {
    rules: [{
      test: /\.js$/i,
      loader: 'babel-loader',
      options: {
        presets: ['@babel/preset-env']
      },
      exclude: /node_modules/
    }]
  },

  resolve: {
    modules: [
      './node_modules',
      '.'
    ]
  }
}
