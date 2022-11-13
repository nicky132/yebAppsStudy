import Vue from 'vue'
import Vuex from 'vuex'
import SockJs from "sockjs-client"
import Stomp from 'stompjs'
import { chatAdmin } from '../api/chat'
import { Notification } from 'element-ui';

Vue.use(Vuex)

const now = new Date();

const store = new Vuex.Store({
    state: {
        // 存储路由信息
        routes: [],
        // 会话列表
        sessions: {},
        currentAdmin: JSON.parse(window.sessionStorage.getItem('user')),
        // 存储其他用户的信息
        admins: [],
        // 当前选中的会话
        currentSession: null,
        // 过滤出只包含这个key的会话
        filterKey: '',
        stomp: null,
        isNew: {},
    },
    // mutations同步执行
    mutations: {
        // 初始化菜单路由
        initRoutes(state, data) {
            state.routes = data;
        },
        userInfo(state, user) {
            state.user = user;
        },
        initAdmin(state, admin) {
            state.currentAdmin = admin;
        },
        changeCurrentSession(state, currentSession) {
            state.currentSession = currentSession;
            Vue.set(state.isNew, state.currentAdmin.username + '#' + state.currentSession.username, "");
        },
        addMessage(state, msg) {
            let message = state.sessions[state.currentAdmin.username + '#' + msg.to];
            if (!message) {
                // sessions[state.currentAdmin.username + '#' + msg.to] = [];
                Vue.set(state.sessions, state.currentAdmin.username + '#' + msg.to, []);
            }
            state.sessions[state.currentAdmin.username + '#' + msg.to].push({
                content: msg.content,
                date: now,
                self: !msg.notSelf,
            })
        },
        INIT_DATA(state) {
            // 浏览器本地历史聊天记录
            let data = localStorage.getItem('vue-chat-session');
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        INIT_ADMINS(state, data) {
            state.admins = data
        }
    },
    // actions异步执行
    actions: {
        initData(context) {
            // 回话记录存储到localStorage
            context.commit('INIT_DATA')
            chatAdmin().then(res => {
                if (res) {
                    context.commit('INIT_ADMINS', res.data);
                }
            })
        },
        // WS连接
        connect(context) {
            context.state.stomp = Stomp.over(new SockJs('/ws/ep'));
            let token = window.sessionStorage.getItem('tokenStr')
            context.state.stomp.connect({
                'Auth-Token': token
            }, success => {
                // 订阅消息
                context.state.stomp.subscribe('/user/queue/chat', msg => {
                    // 消息接收
                    let receiveMsg = JSON.parse(msg.body);
                    if (!context.state.currentSession || receiveMsg.from != context.state.currentSession) {
                        Notification.info({
                            title: '【' + receiveMsg.fromNickName + '】发来一条消息.',
                            message: receiveMsg.content > 10 ? receiveMsg.content.substr(0, 10) : receiveMsg.content,
                            position: 'top-right',
                        })
                        Vue.set(context.state.isNew, context.state.currentAdmin.username + '#' + receiveMsg.from, "new");
                    }
                    receiveMsg.notSelf = true;
                    receiveMsg.to = receiveMsg.from;
                    context.commit('addMessage', receiveMsg);
                })
            }, err => { })
        },
    },
})

store.watch(function (state) {
    return state.sessions
}, function (val) {
    console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
}, {
    deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})

export default store;