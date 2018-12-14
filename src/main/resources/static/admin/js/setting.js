//设置全局表单提交格式
Vue.http.options.emulateJSON = true;

// Vue实例
var vm = new Vue({
    el: '#app',
    data() {
        return {
            //实体类
            entity: {
                user: {
                    id: '',
                    username: '',
                    password: '',
                    email: ''
                },
                add: {
                    id: '',
                    username: '',
                    email: '',
                    nickname: '',
                    password: '',
                    checkPass: '',
                }
            },
            editor: {
                user: {
                    id: '',
                    username: '',
                    password: '',
                    email: ''
                },
            },

            //一些额外的配置属性
            config: {
                defaultActive: '10',

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

        addUser() {
            console.log(this.entity.add);
            if (this.entity.add.username == '' || this.entity.add.username == null) {
                this.$message({
                    type: 'info',
                    message: '用户名称不能为空',
                    duration: 6000
                });
            } else if (this.entity.add.email == '' || this.entity.add.email == null) {
                this.$message({
                    type: 'info',
                    message: '用户邮箱不能为空',
                    duration: 6000
                });
            } else if (this.entity.add.password == '' || this.entity.add.password == null) {
                this.$message({
                    type: 'info',
                    message: '密码不能为空',
                    duration: 6000
                });
            } else if (this.entity.add.password != this.entity.add.checkPass) {
                this.$message({
                    type: 'error',
                    message: '两次输入的密码不一致',
                    duration: 6000
                });
            } else if (this.entity.add.password.length < 6) {
                this.$message({
                    type: 'error',
                    message: '请重新输入密码，密码长度在6位及以上',
                    duration: 6000
                });
                this.clearPwd();
            } else {
                this.entity.add.id = 0;
                this.$http.post('/setting/adduser', JSON.stringify(this.entity.add)).then(result=>{
                    if(result.body.success)
                {
                    this.$message({
                        type: 'success',
                        message: result.body.info,
                        duration: 6000
                    });
                    //window.location.reload();
                    this.$refs.add.resetFields(); //清空校验状态
                    this.entity.add.username = '';
                    this.entity.add.nickname = '';
                    this.entity.add.email = '';
                    this.entity.add.password = '';
                    this.entity.add.checkPass = '';
                }else{
                    this.$message({
                        type: 'info',
                        message: result.body.info,
                        duration: 6000
                    });
                    //window.location.reload();
                }
            });
            }
        },

        clearPwd() {
            this.$refs.user.resetFields(); //清空校验状态
            this.entity.user.checkPass = '';
            this.entity.user.password = '';
        },

        init() {
            //已登录用户名
            this.$http.get('/admin/getName').then(result=>{
                this.config.token.name = result.bodyText;
        });
        },

    },
    // 生命周期函数
    created() {
        this.init();
    },
});
