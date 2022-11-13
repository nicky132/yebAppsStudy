import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store"
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.css'
import { getInfo } from "@/api/sys/login";
import { initMenu } from "@/api/menu";

Vue.config.productionTip = false
Vue.use(ElementUI)

// 路由全局前置守卫
router.beforeEach((to, from, next) => {
    if (window.sessionStorage.getItem("tokenStr")) {
        // 初始化路由信息
        initMenu(router, store);
        // 拉取当前登录用户信息 
        if (!window.sessionStorage.getItem("user")) {
            return getInfo().then(res => {
                window.sessionStorage.setItem("user", JSON.stringify(res));
                store.commit('initAdmin', res);
                // router.replace({ path: '/home' })
                next({ ...to, replace: true });
            })
        }
        next();
    } else {
        if (to.path !== "/") {
            router.replace({ path: '/' });
        }
        next()
    }
})


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')