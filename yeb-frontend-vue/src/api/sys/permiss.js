import service from '@/utils/request'

// 查询角色列表
export function roleList() {
    return service({
        url: '/system/basic/role',
        method: 'GET',
    })
}

// 查询菜单列表
export function allMenus() {
    return service({
        url: '/system/cfg/',
        method: 'GET',
    })
}

// 查询角色对应的菜单列表
export function menusWithRole(data) {
    return service({
        url: '/mid/' + data,
        method: 'GET',
    })
}

export function updateRoles(data) {
    return service({
        url: '/updateMenuRoles?rId=' + data,
        method: 'PUT',
    })
}

// add role
export function addRole(data) {
    return service({
        url: '/system/basic/role',
        method: 'POST',
        data: data,
    })
}

// deltet role
export function delRole(data) {
    return service({
        url: '/system/basic/role/' + data,
        method: 'DELETE',
    })
}