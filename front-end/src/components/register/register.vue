<template>
  <div id="reg_bg">
    <div class="reg_logo_wrap">
      <img src="./registerImgs/login_logo.png" alt="">
    </div>
    <div class="reg_content">
      <ul>
        <li>
          <div class="reg_mobile_bg">
            <img src="./registerImgs/register_invite.png" alt="">
            <input type="text" placeholder="请输入姓名"  v-model="uname">
          </div>
        </li>
        <li>
          <div class="reg_mobile_bg">
            <img src="./registerImgs/register_mobile.png" alt="">
            <input type="text" placeholder="请输入手机号" maxlength='11' v-model="mobile" oninput="this.value=this.value.replace(/\D/g,'')" >
          </div>
        </li>
        <li>
          <div class="reg_mobile_bg">
            <img src="./registerImgs/login_pwd.png" alt="">
            <input @focus="regPwdF" type="password" placeholder="请输入密码" maxlength="18" v-model="regPwd">
          </div>
        </li>
        <li>
          <div class="reg_mobile_bg">
            <img src="./registerImgs/login_pwd.png" alt="">
            <input type="password" placeholder="请确认密码" v-model="confirmPwd">
          </div>
        </li>
        <li>
          <div class="reg_code_bg">
            <img class="regCode" src="./registerImgs/register_code.png" alt="">
            <input type="text" placeholder="请输入验证码" v-model="valiCodR">
            <input id="send" class="sendCode" type="button" @click="sendCode" :value="sendC">
          </div>
        </li>
        <li>
          <div class="reg_invite_bg">
            <img class="regInvitMobile" src="./registerImgs/register_invite.png" alt="">
            <input type="text" :placeholder="recmPhonR?recmPhonR:'请输入推荐人手机号'" maxlength='11'  v-model="recmPhonR" >
          </div>
        </li>
        <li>
          <p class="cus_tel">客服电话: 400-071-2007</p>
        </li>
        <li>
          <input type="hidden" v-model="userId">
        </li>
      </ul>
      <div class="reg_agreement_wrap">
        <p class="regAgrC"><i class="regSel " :class="{check:check}" @click="selZhong"></i><span>我已阅读并同意签署</span><router-link to="/regAgreement"><span class="colorG"> 《品道注册协议》</span></router-link></p>
        <!--<button class="regNextBtn" @click="valiyC">下一步</button>-->
        <button class="regNextBtn" @click="valiyC">完成注册</button>
        <p>已有账号？<router-link to="/login"><span class="colorG">立即登录</span></router-link></p>
      </div>
    </div>
  </div>
</template>

<script>
  import store from '@/vuex/index';
    export default {
      name: "register",
      created(){
        store.commit('footHide');
      },
      mounted(){
        //获取地址栏中
        var userid = window.location.href.split('?')[1];
        if (userid){
          this.userId = userid.split('&')[0].split('=')[1];
          this.recmPhonR = userid.split('&')[1].split('=')[1];
        }
      },
      data(){
        return{
          uname: '',//用户名
          mobile: '', //手机号
          valiCodR: '', //验证码
          regPwd: '', //注册密码
          confirmPwd: '', //确认密码
          recmPhonR: '', //推荐人手机号
          check: false, //注册协议
          sendC: '发送验证码',
          userId: '', //
          times: 60,// 时间设置60秒
          timer: null,
        }
      },
      methods:{
        regPwdF(){
          this.$Toast('密码必须包含至少一个字母和一个数字的6-18位字母和数字组合');
        },
        valiyC(){
          if(this.uname == ''){
            this.$Toast('请输入姓名');
          }else if(this.mobile=='' || !(/^1[3|4|5|7|8][0-9]{9}$/.test(this.mobile))){
            this.$Toast('请输入11位中国大陆手机号码');
            return false;
          }else if(this.regPwd == '' || !(/^[a-zA-Z0-9]{6,18}$/.test(this.regPwd))){//注册密码
            this.$Toast('密码格式不正确');
            return false;
          }else if(this.confirmPwd=='' || this.confirmPwd != this.regPwd){//确认密码和密码要保持一致
            this.$Toast('请输入确认密码或确认密码不一致');
            return false;
          }else if(this.valiCodR==''){
            this.$Toast('请输入6位数字短信验证码');
            return false;
          }else if(this.recmPhonR==''){
            this.$Toast('推荐号码不能为空');
            return false;
          }else if(!this.check){//
            this.$Toast('请阅读注册协议');
            return false;
          }else{
            //验证完后，要把用户输入的信息发送到后端，跟后端的接口对接
            this.$http({
              method: 'post',
              url: this.url+'/api/user/register',
              headers: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'chnName': this.uname,
                'mobile': this.mobile,
                'password': this.regPwd,
                'verifyCode': this.valiCodR,
                'refereePhone': this.recmPhonR,
                'userId': this.userId,
              }
            })
            .then(res => {
              if(res.data.error_code === 0){//说明请求成功,需要跳转到登录界面
                this.$Toast('注册成功，请登录');
                this.$router.push({
                  path: '/login'
                })
              }else{
                this.$Toast(res.data.data.error);
              }
            })
            .catch(error => {
                console.log(error);
              }
            );
          }
        },
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
              url: this.url + '/api/sub/validatecode/reg_'+this.mobile,
              headers: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
            }).then(res =>{
              let data = res.data.error_code;
              if (data === 0){
                this.$Toast('验证码发送成功');
              }else {
                this.$Toast(res.data.data.error);
              }
            },error=>{
              console.log(error)
            })
          }else {
            this.$Toast('手机号码不能为空')
          }
        },
        selZhong(){
          this.check = !this.check;
        }
      }
    }
</script>

<style scoped>
  .cus_tel {
    font-size: .3rem;
    width: 70%;
    margin: .1rem auto;
    text-align: left;
  }
  #reg_bg {
    position: fixed;
    width: 100%;
    height: 100%;
    background: url("./registerImgs/login_bg.png") center center no-repeat;
    background-size: 100% auto;
    z-index: -100;
  }
  .reg_logo_wrap {
    width: 50%;
    margin: 1.5rem auto .3rem auto;
  }
  .reg_logo_wrap img {
    width: 3rem;
  }
  .reg_content img {
    width: .45rem;
    vertical-align: middle;
  }
  .reg_content ul li >div {
    width: 80%;
    margin: auto;
    border: 1px solid #eee;
    border-radius: .5rem;
    height: 1rem;
    line-height: 1rem;
    text-align: left;
    padding-left: .5rem;
  }
  .reg_mobile_bg img{
    width: .35rem;
  }
  .reg_mobile_bg img {
    margin-right: .1rem;
  }
  .reg_content > ul li {
    margin-bottom: .2rem;
  }
  .regAgrC i{
    display: inline-block;
    width: .3rem;
    height: .3rem;
    background: url(./registerImgs/register_nocheck.png) center center no-repeat;
    background-size: contain;
    margin-right: .1rem;
    margin-top: -.1rem;
  }
  .regAgrC i.check {
    background: url(./registerImgs/register_checked.png) center center no-repeat;
    background-size: contain;
  }
  .reg_agreement_wrap {
    margin:1.5rem auto auto auto;
    width: 80%;
    font-size: .25rem;
    letter-spacing: 1px;
  }
  .regNextBtn {
    width: 100%;
    padding: .2rem;
    background: #974f02;
    color: #fff;
    border-radius: .5rem;
    margin-top: .2rem;
    margin-bottom: .4rem;
    letter-spacing: 1px;
    font-size: .45rem;
  }
  .colorG {
    color: #974f02;
  }
  .reg_code_bg {
    position: relative;
  }
  .sendCode {
    position: absolute;
    top: 0;
    right: 0;
    padding: 0 .2rem;
    border-radius: .5rem;
    background: #974f02;
    color: #fff;
  }
</style>
