module.exports = {
  entry: 'src/main/ts/index.tsx',
  devtool: 'sourcemaps',
  cache: true,
  debug: true,
  output: {
    path: __dirname,
    filename: './src/main/resources/static/built/bundle.js'
  },
  resolve: {
    extensions: [".ts", ".tsx", ".js", ".json"]
  },
  module: {
    loaders: [
      { test: /\.tsx?$/, loader: "awesome-typescript-loader" },
      { enforce: "pre", test: /\.js$/, loader: "source-map-loader" }    	
    ]
  },
  externals: {
    "react": "React",
    "react-dom": "ReactDOM",
    "rest": "Rest"
  }
}