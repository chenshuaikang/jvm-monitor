//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

//前端API访问接口
let api = {
    jvm: {
        runtime: {
            get: '/runtime/get',
        },
        class: {
            get: '/class/get',
        },
        memory: {
            get: '/memory/get',
        },
        thread: {
            get: '/thread/get'
        },
        gc: {
            get: '/gc/get',
            getPools: '/gc/getPools'
        },
    },

    redis: {
        dbsize: {
            get: '/dbsize/get',
        },
        redismemory: {
            get: '/redismemory/get'
        },
        redisinfo: {
            get: '/redisinfo/get'
        },
        commandinfo: {
            get: '/commandinfo/get'
        },
        kbpsinfo: {
            get: '/rediskpbs/get'
        }
    }

}