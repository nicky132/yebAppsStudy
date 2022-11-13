import service from '@/utils/request'

// 查询部门信息列表
export function depList() {
    return service({
        url: '/system/basic/department',
        method: 'GET'
    })
}

// add department
export function addDep(data) {
    return service({
        url: '/system/basic/department',
        method: 'POST',
        data: data
    })
}

// remove department
export function delDep(data) {
    return service({
        url: '/system/basic/department/' + data,
        method: 'DELETE'
    })
}
