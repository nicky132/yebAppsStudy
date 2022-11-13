import service from '@/utils/request'

// 更新用户信息
export function updateInfo(data) {
    return service({
        url: '/admin/info',
        method: 'PUT',
        data: data
    })
}

// 更新用户密码
export function updateUserPwd(data) {
    return service({
        url: '/admin/pwd',
        method: 'PUT',
        data: data
    })
}