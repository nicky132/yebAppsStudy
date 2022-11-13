import service from "@/utils/request"
export function initMenu(router, store) {
    if (store.state.routes.length > 0) {
        return;
    }
    service({
        url: '/system/cfg/menu',
        method: 'GET',
    }).then(response => {
        if (response) {
            // 格式化router
            let fmtRoutes = formatRoutes(response.data);
            router.addRoutes(fmtRoutes);
            // 存入vuex
            store.commit('initRoutes', fmtRoutes);
            // 连接websocket
            store.dispatch('connect');
        }
    })
}

// 路由格式化
export function formatRoutes(routes) {
    let fmtRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            iconCls,
            children,
        } = router;
        // 递归
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            children: children,
            component(resolve) {
                if (component.startsWith('Home')) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith('Emp')) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sal')) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith('Per')) {
                    require(['../views/per/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sta')) {
                    require(['../views/sta/' + component + '.vue'], resolve);
                } else if (component.startsWith('Sys')) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                }
            }
        }
        fmtRoutes.push(fmRouter);
    })
    return fmtRoutes;
}