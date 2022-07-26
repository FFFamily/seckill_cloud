// vue.config.js

module.exports = {
  configureWebpack: {
    plugins: [
      require('unplugin-vue-components/webpack')({
        /* options */
      })
    ]
  }
}
