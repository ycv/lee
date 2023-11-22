import Vue from 'vue'
import Router from 'vue-router'
import Login from "@/views/Login";
import Home from "@/views/Home";
import FriendChat from "@/views/chat/FriendChat";
import HrInfo from "@/views/HrInfo";

Vue.use(Router)

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
        hidden: true
    },


    {
        path: '/home',
        name: 'Home',
        component: Home,
        hidden: true,
        meta: {
            roles: ['admin', 'user']
        },
        children: [
            {
                path: '/chat',
                name: '在线聊天',
                component: FriendChat,
                hidden: true
            }, {
                path: '/hrinfo',
                name: '个人中心',
                component: HrInfo,
                hidden: true
            }
        ]
    },


    {
        path: '/emp',
        name: '员工资料',
        component: Home,
        hidden: true,
        children: [
            {
                path: '/emp/basic',
                name: '基本资料',
                component: () => import('@/views/emp/EmpBasic'),
                hidden: true
            }
        ]
    },

    {
        path: '/per',
        name: '人事管理',
        component: Home,
        hidden: true,
        children: [
            {
                path: '/per/emp',
                name: '员工资料13',
                component: () => import('@/views/per/PerEmp'),
                hidden: true
            },
            {
                path: '/per/ec',
                name: '员工奖惩23',
                component: () => import('@/views/per/PerEc'),
                hidden: true
            },
            {
                path: '/per/train',
                name: '报表清单',
                component: () => import('@/views/per/PerTrain'),
                hidden: true
            }
        ]
    },

    {
        path: '/sta',
        name: '统计管理',
        component: Home,
        hidden: true,
        children: [

            {
                path: '/sta/all',
                name: '股票RSI列表',
                component: () => import('@/views/sta/StaAll'),
                hidden: true
            },

            {
                path: '/sta/score',
                name: '历史数据对比',
                component: () => import('@/views/sta/StaScore'),
                hidden: true
            },

            {
                path: '/sta/pers',
                name: '人事信息统计',
                component: () => import('@/views/sta/StaPers'),
                hidden: true
            },

            {
                path: '/sta/record',
                name: '全量历史记录',
                component: () => import('@/views/sta/StaRecord'),
                hidden: true
            }

        ]
    },

    {
        path: '*',
        redirect: '/home'
    },


    //测试---测试

    {
        path: '/demo/demo',
        name: 'demo',
        component: () => import('@/views/demo/demo'),
        hidden: true
    },
    {
        path: '/demo/Input',
        name: '测试',
        component: () => import('@/views/demo/Input'),
        hidden: true
    },
    {
        path: '/demo/quote',
        name: '测试报价',
        component: () => import('@/views/demo/quote'),
        hidden: true
    },
    {
        path: '/demo/person',
        name: '测试数据',
        component: () => import('@/views/demo/person'),
        hidden: true
    },


]


const router = new Router({
    routes
})


export default router