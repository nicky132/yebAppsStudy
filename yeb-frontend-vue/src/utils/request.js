import axios from 'axios'
import router from "@/router";
import {Message} from "element-ui";

const service = axios.create({
    baseURL: '/yeb',
    timeout: 5000
})

// 请求拦截器
service.interceptors.request.use(config => {
    // 拉取用户token信息
    let tokenStr = window.sessionStorage.getItem('tokenStr');
    // token存在，请求携带这个token
    if (tokenStr) {
        config.headers['Authorization'] = tokenStr;
    }
    return config;
},error => {
    Message.error(error.data.message);
})

// 响应拦截器
service.interceptors.response.use(success => {
    if (success.status && success.status === 200) {
        // 业务逻辑错误
        if (success.data.code === 500 || success.data.code === 401 || success.data.code === 403) {
            Message.error(success.data.message);
            return;
        }
        // 响应成功
        // if (success.data.message) {
        //     Message.success(success.data.message);
        // }
        return success.data;
    }
}, error => {
    if (error.response.code === 404 || error.response.code === 504) {
        Message.error('服务器被吃了o(╯□╰)o！');
    } else if (error.response.code === 403) {
        Message.error('权限不足，请联系管理员！');
    } else if (error.response.code === 401) {
        Message.error('尚未登录，请先登录！');
        router.push('/login');
    } else {
        if (error.response.data.message) {
            Message.error(error.response.data.message);
        } else {
            Message.error('未知错误！');
        }
    }
    return;
})

export default service