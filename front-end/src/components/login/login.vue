<template>
  <div id="login_bg">
    <div class="login_logo_wrap"><img src="./loginImgs/login_logo.png" alt=""></div>
    <div class="login_content">
      <ul>
        <li>
          <div class="login_uname_bg">
            <img src="./loginImgs/login_username.png" alt="">
            <input type="text" placeholder="请输入手机号" v-model="uPhone" maxlength="11">
          </div>
        </li>
        <li>
          <div class="login_pwd_bg">
            <img class="loginPwd" src="./loginImgs/login_pwd.png" alt="">
            <input type="password" placeholder="请输入登录密码" v-model="uPwd" maxlength="18">
          </div>
        </li>
      </ul>
      <div class="login_btn_wrap">
        <button @click="userlogin">登录</button>
        <p class="new_user_login">
          <router-link to="/register">
            <span>新用户注册</span>
          </router-link>
          <router-link to="/forgetPwd">
            <span>忘记密码？</span>
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue';
  import {Toast} from 'vant';
  Vue.use(Toast);
  import store from '@/vuex/index';//引入vuex状态管理器
    export default {
      name: "login",
      created(){//页面加载还没出来的时候
        store.commit('footHide');
      },
      data(){
        return {
          uPhone: '',
          uPwd: '',
        }
      },
      methods:{
        userlogin(){
          if(this.uPhone=='' || !(/^1[3|4|5|7|8][0-9]{9}$/.test(this.uPhone))) {
            Toast('请输入11位中国大陆手机号码');
            return false;
          }else if(this.uPwd == '' || !(/^[a-zA-Z0-9]{6,18}$/.test(this.uPwd))){
            Toast('密码格式不正确');
            return false;
          }else {
            this.$http({
              method: 'post',
              url: this.url + '/api/user/login',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'mobile': this.uPhone,
                'password': this.uPwd,
              }
            }).then(res=>{
              if(res.data.error_code === 0){//请求成功后
                localStorage.setItem('name',res.data.data.cookie.name);
                localStorage.setItem('value',res.data.data.cookie.value);
                localStorage.setItem('userId',res.data.data.user.id);
                localStorage.setItem('userMobile',res.data.data.user.mobile);//把用户的手机号码存储到
                store.commit('setUserVal',res.data.data.cookie.value);
                this.$router.push({
                  path: '/home'
                });
              }else {//请求成功，但是不完全正常
                Toast(res.data.data.error);
              }
            }).catch(error=>{
              // alert(error);
            })
          }
        },
      },
    }
</script>

<style scoped>
  #login_bg {
    position: fixed;
    width: 100%;
    height: 100%;
    background: url("./loginImgs/login_bg.png") center center no-repeat;
    background-size: 100% auto;
    z-index: -100;
  }
  .login_logo_wrap {
    width: 50%;
    margin: 0 auto;
    margin-top: 2.5rem;
    margin-bottom: 1rem;
  }
  .login_logo_wrap img {
    width: 3rem;
  }
  .login_content img {
    width: .5rem;
    vertical-align: middle;
  }
  .login_uname_bg img {
    margin-right: .1rem;
  }
  .login_content > ul li {
    margin-bottom: .5rem;
  }
  .login_content ul li >div {
    width: 80%;
    margin: auto;
    border: 1px solid #eee;
    border-radius: .5rem;
    height: 1rem;
    line-height: 1rem;
    text-align: left;
    padding-left: .5rem;
  }
  .login_pwd_bg img.loginPwd {
    width: .4rem;
  }
  .login_btn_wrap {
    width: 80%;
    margin: auto;
    margin-top: 2rem;
  }
  .login_btn_wrap button {
    width: 100%;
    background: #974f02;
    color: #fff;
    padding: .2rem;
    border-radius: .5rem;
    letter-spacing: 2px;
    font-size: .45rem;
  }
  .new_user_login {
    margin-top: .5rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: .35rem;
  }
</style>
