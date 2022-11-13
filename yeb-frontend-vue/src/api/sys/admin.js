import service from '@/utils/request'

// 获取所有操作员
export function adminList(data) {
    if (data != null) {
        return service({
            url: '/system/admin?keyword=' + data,
            method: 'GET',
        })
    } else {
        return service({
            url: '/system/admin',
            method: 'GET',
        })
    }
}

// 更新操作员
export function editAdmin(data) {
    return service({
        url: '/system/admin',
        method: 'PUT',
        data: data,
    })
}
// 删除操作员
export function delAdmin(data) {
    return service({
        url: '/system/admin/' + data,
        method: 'DELETE',
    })
}

// 更新操作员角色
export function updateAdminRole(data) {
    return service({
        url: '/system/admin/updateAdminRole?adminId=' + data,
        method: 'PUT',
    })
}