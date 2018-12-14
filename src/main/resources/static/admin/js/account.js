//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

// Vue实例
var vm = new Vue({
    el: '#app',
    data() {
        return {
            //实体类
            entity: {
                query: {
                    username: ''
                },
            },
            //一些额外的配置属性
            config: {
                defaultActive: '11',

                //===========侧边栏===========
                name: '',
                isCollapse: false,
                side_close_flag: true,

                token: {name: ''},
            },
        }
    },
    methods: {

        //===============侧边栏&&顶栏================
        //顶栏触发事件
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        //打开侧边栏
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        //关闭侧边栏
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        //侧边栏触发事件
        handleSideSelect(key, keyPath) {
        },
        query(){
            console.log(this.entity.query);
            if(null==this.entity.query.username){
                this.$message({
                    type: 'info',
                    message: '用户名称不能为空',
                    duration: 6000
                });
            }else if(this.entity.query.username==''){
                this.$message({
                    type: 'info',
                    message: '用户名称不能为空',
                    duration: 6000
                });
            }else {
                this.$http.post('/account/query', JSON.stringify(this.entity.query)).then(result=>{
                    if(result.body.success){
                    this.$message({
                        type: 'success',
                        message: '测试浏览成功',
                        duration: 6000
                    });
                }else{
                    this.$message({
                        type: 'info',
                        message: result.body.info,
                        duration: 6000
                    });
                }
            });
            }
        },
        init(){
            //已登录用户名
            this.$http.get('/admin/getName').then(result => {
                this.config.token.name = result.bodyText;
        });
        },
    },
    // 生命周期函数
    created() {
        this.init();
    },
});
