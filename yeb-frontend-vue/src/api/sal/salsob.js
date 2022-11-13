import service from '@/utils/request'

// 查询所有工资账套
export function salsobList() {
    return service({
        url: '/salary/sob',
        method: 'GET',
    })
}

// 添加工资账套
export function addSalSob(data) {
    return service({
        url: '/salary/sob',
        method: 'POST',
        data: data,
    })
}

// 修改工资账套
export function editSalSob(data) {
    return service({
        url: '/salary/sob',
        method: 'PUT',
        data: data,
    })
}

// 删除工资账套
export function delSalSob(data) {
    return service({
        url: '/salary/sob/' + data,
        method: 'DELETE',
    })
}