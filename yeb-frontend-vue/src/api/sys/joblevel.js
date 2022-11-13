import service from "@/utils/request";

// 获取所求职位信息
export function jobLevelList() {
    return service({
        url: '/system/basic/joblevel',
        method: 'GET',
    })
}

// 添加职位信息
export function addJobLevel(data) {
    return service({
        url: '/system/basic/joblevel',
        method: 'POST',
        data: data
    })
}

// 删除职位信息
export function delJobLevel(data) {
    return service({
        url: '/system/basic/joblevel/' + data,
        method: 'DELETE',
    })
}


// 批量删除职位信息
export function delBatch(data) {
    return service({
        url: '/system/basic/joblevel/' + data,
        method: 'DELETE'
    })
}

// 修改职位信息
export function editJobLevel(data) {
    return service({
        url: '/system/basic/joblevel',
        method: 'PUT',
        data: data,
    })
}

