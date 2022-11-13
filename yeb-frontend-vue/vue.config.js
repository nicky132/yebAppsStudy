let proxyObj = {}

proxyObj['/yeb'] = {
    // websocket
    ws: false,
    // 目标地址
    target: 'http://localhost:8081',
    // 发送请求头的host设置为target
    changeOrigin: true,
    // 不重写请求地址
    pathRewrite: {
        '^/': '/',
    }
}

proxyObj['/ws'] = {
    // websocket
    ws: true,
    target: 'ws://localhost:8081/yeb',
}

module.exports = {
    lintOnSave: false,   // 关闭eslint检查
    devServer: {
        host: 'localhost',
        port: 8080,
        proxy: proxyObj
    }
}