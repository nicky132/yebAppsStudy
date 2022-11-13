import service from "@/utils/request";

// 获取所求职位信息
export function postList() {
    return service({
        url: '/system/basic/pos',
        method: 'GET',
    })
}

// 添加职位信息
export function addPost(data) {
    return service({
        url: '/system/basic/pos',
        method: 'POST',
        data: data
    })
}

// 删除职位信息
export function delPost(data) {
    return service({
        url: '/system/basic/pos/' + data,
        method: 'DELETE',
    })
}


// 批量删除职位信息
export function delBatch(data) {
    return service({
        url: '/system/basic/pos/' + data,
        method: 'DELETE'
    })
}

// 修改职位信息
export function editPost(data) {
    return service({
        url: '/system/basic/pos',
        method: 'PUT',
        data: data,
    })
}

