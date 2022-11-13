import service from '@/utils/request'


export function chatAdmin() {
    return service({
        url: '/chat/admin',
        method: 'GET',
    })
}