import service from "@/utils/request";

// 登录方法
export function login(data) {
    return service({
        url: '/login',
        method: 'POST',
        data: data
    })
}

// 获取用户详细信息
export function getInfo() {
    return service({
        url: '/admin/info',
        method: 'GET',
    })
}

// 退出登录
export function logout() {
    return service({
        url: '/logout',
        method: 'POST',
    })
}