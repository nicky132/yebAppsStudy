import service from '@/utils/request'

// 查询所有员工的工资账套
export function empsalList(currentPage, size) {
    return service({
        url: '/?currentPage=' + currentPage + '&size=' + size,
        method: 'GET',
    })
}

// 查询所有工资账套
export function salList() {
    return service({
        url: '/salaries',
        method: 'GET',
    })
}

// 更新员工资账套
export function editEmpSal(eId, sId) {
    return service({
        url: '/?eId=' + eId + '&sId=' + sId,
        method: 'PUT',
    })
}
