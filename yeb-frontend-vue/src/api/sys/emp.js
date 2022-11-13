import service from '@/utils/request'

// 查询所有员工信息
export function empList(currentPage, pageSize, empName) {
    if (empName == null || empName == '') {
        return service({
            url: '/employee/basic?currentPage=' + currentPage + '&size=' + pageSize,
            method: 'GET',
        })
    } else {
        return service({
            url: '/employee/basic?currentPage=' + currentPage + '&size=' + pageSize + '&name=' + empName,
            method: 'GET',
        })
    }

}