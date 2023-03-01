module.exports = {
  chainWebpack: config => {
    config.module
      .rule('images')
      .use('url-loader')
      .loader('url-loader')
      .tap(options => {
        options.limit = -1
        return options
      })
    config
      .plugin('html')
      .tap(options => {
        options[0].title = "스마트 가스 안전제어";
        return options;
      })
  },
  filenameHashing: false,
  publicPath: '/sgsc'
}