<template>
  <div>
    <header class="forgetPwdHeader" >
      <!--<router-link to="/login">-->
        <img src="./forgetPwdImgs/arrow_left.png" alt="" @click="this.backTop">
      <!--</router-link>-->
      <span>忘记密码</span>
    </header>
    <div class="rorget_content">
      <ul>
        <li>
          <div class="rorget_mobile_bg">
            <img src="./forgetPwdImgs/register_mobile.png" alt="">
            <input type="text" placeholder="请输入手机号" maxlength='11' v-model="mobile" oninput="this.value=this.value.replace(/\D/g,'')" >
          </div>
        </li>
        <li>
          <div class="rorget_code_bg">
            <img class="yzCode" src="./forgetPwdImgs/register_code.png" alt="">
            <input type="text" placeholder="请输入验证码" v-model="valiCodR">
            <input id="send" class="sendCode" type="button" @click="sendCode" :value="sendC">
          </div>
        </li>
        <li>
          <div class="_mobile_bg">
            <img src="./forgetPwdImgs/login_pwd.png" alt="">
            <input type="password" placeholder="请输入密码" maxlength="18" v-model="regPwd">
          </div>
        </li>
        <li>
          <div class="reg_mobile_bg">
            <img src="./forgetPwdImgs/login_pwd.png" alt="">
            <input type="password" placeholder="请确认密码" v-model="confirmPwd">
          </div>
        </li>
        <li>
          <p style="font-size: .2rem">*密码必须包含至少一个字母和一个数字的6-18位字母和数字组合</p>
        </li>
      </ul>
      <button class="forgetPwdNextBtn" @click="finishedUpdate()">完成</button>
    </div>
  </div>
</template>

<script>
  import store from '@/vuex/index';
  import Vue from 'vue';
  import {Toast} from 'vant';
  Vue.use(Toast);
    export default {
      name: "forget-pwd",
      data(){
        return {
          mobile: '',
          valiCodR: '', //验证码
          sendC: '发送验证码',
          regPwd: '' ,//密码
          confirmPwd: '',//确认密码
          times: 60,// 时间设置60秒
          timer: null,
        }
      },
      created(){
        store.commit('footShow');
      },
      methods:{
        sendCode(){
          // 计时开始
          if (this.mobile !=''){
            let send = document.getElementById('send');
            this.timer = setInterval( () => {
              this.times--;

              if (this.times <= 0) {
                send.value = '发送验证码';
                clearInterval(this.timer);
                send.disabled = false;
                this.times = 60;
              } else {
                send.value = this.times + '秒后重试';
                send.disabled = true;
              }
            }, 1000);
            //这个方法用了调用发送验证码的接口
            this.$http({
              method: 'get',
              url: this.url + '/api/sub/validatecode/forgetPwd_'+this.mobile,
              headers: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
            }).then(res =>{
              let data = res.data.error_code;
              if (data === 0){
                Toast('验证码发送成功');
              }else {
                Toast(res.data.data.error);
              }
            },error=>{
              console.log(error)
            })
          }else {
            this.$Toast('手机号码不能为空')
          }
        },
        finishedUpdate(){//当点击完成按钮是的验证
          if(this.mobile=='' || !(/^1[3|4|5|7|8][0-9]{9}$/.test(this.mobile))){
            Toast('请输入11位中国大陆手机号码');
            return false;
          }else if(this.regPwd == '' || !(/^[a-zA-Z0-9]{6,18}$/.test(this.regPwd))){//注册密码
            Toast('密码格式不正确');
            return false;
          }else if(this.confirmPwd=='' || this.confirmPwd != this.regPwd){//确认密码和密码要保持一致
            Toast('请输入确认密码或确认密码不一致');
            return false;
          }else if(this.valiCodR==''){
            Toast('请输入6位数字短信验证码');
            return false;
          }else{
            //验证完后，要把用户输入的信息发送到后端，跟后端的接口对接
            this.$http({
              method: 'post',
              url: this.url+'/api/user/forgetPassword',
              headers: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'mobile': this.mobile,
                'verifyCode': this.valiCodR,
                'newPwd': this.regPwd,
              }
            })
              .then(res => {
                if(res.data.error_code === 0){//说明请求成功,需要跳转到登录界面
                  Toast('密码重置成功，请登录');
                  this.$router.push({
                    path: '/login'
                  })
                }else {
                  Toast(res.data.data.error);
                }
              })
              .catch(error => {
                  console.log(error);
              });
          }
        }
      }
    }
</script>

<style scoped>
  .forgetPwdHeader {
    background: #974f02;
    color: #fff;
    font-size: .45rem;
    padding: .4rem;
    text-align: center;
    position: relative;
  }
  .forgetPwdHeader img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .3rem;
  }
  .rorget_content {
    margin: 2rem auto;
  }
  .rorget_content ul li > div img {
    width: .45rem;
    vertical-align: middle;
  }
  .rorget_mobile_bg img{
    width: .35rem !important;
  }
  .rorget_content ul li > div {
    width: 80%;
    margin: auto;
    border: 1px solid #fff;
    border-radius: .5rem;
    height: 1rem;
    line-height: 1rem;
    text-align: left;
    padding-left: .5rem;
    background: #fff;
  }
  .rorget_content > ul li {
    margin-bottom: .5rem;
  }
  .forgetPwdNextBtn {
    width: 80%;
    padding: .2rem;
    background: #974f02;
    color: #fff;
    border-radius: .5rem;
    margin-top: .2rem;
    margin-bottom: .4rem;
    letter-spacing: 1px;
    font-size: .45rem;
  }
  .rorget_code_bg {
    position: relative;
  }
  .sendCode {
    position: absolute;
    right: 0;
    top:0;
    background: #974f02;
    color: #fff;
    padding: 0 .2rem;
    border-radius: .5rem;
  }
</style>
