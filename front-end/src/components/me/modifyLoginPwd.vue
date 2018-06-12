<template>
    <div>
      <header class="modifyPwdHeader">
        <router-link to="/accountSet">
          <img src="./meImgs/arrow_left.png" alt="">
        </router-link>
        <span>修改密码</span>
      </header>
      <div class="modify_content">
        <ul>
          <li>
            <div class="modify_code_bg">
              <img class="yzCode" src="./meImgs/login_pwd.png" alt="">
              <input type="password" placeholder="请输入原密码" v-model="initPwd" maxlength="18">
            </div>
          </li>
          <li>
            <div class="modify_mobile_bg">
              <img src="./meImgs/login_pwd.png" alt="">
              <input type="password" placeholder="请输入密码" maxlength="18" v-model="regPwd">
            </div>
          </li>
          <li>
            <div class="reg_mobile_bg">
              <img src="./meImgs/login_pwd.png" alt="">
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
  import store from '../../vuex';
    export default {
      name: "modify-login-pwd",
      created(){
          store.commit('footShow');
      },
      data(){
        return {
          initPwd: '',
          confirmPwd: '',
          regPwd: '',
        }
      },
      methods: {
        finishedUpdate(){
          if (this.initPwd == '' || !(/^[a-zA-Z0-9]{6,18}$/.test(this.initPwd))){
            this.$Toast('原始密码格式不正确');
            return false;
          }else if(this.regPwd == '' || !(/^[a-zA-Z0-9]{6,18}$/.test(this.regPwd))){//注册密码
            this.$Toast('密码格式不正确');
            return false;
          }else if(this.confirmPwd=='' || this.confirmPwd != this.regPwd){//确认密码和密码要保持一致
            this.$Toast('请输入确认密码或确认密码不一致');
            return false;
          }else {
            this.$http({
              method: 'post',
              url: this.url+'/api/user/updatePassword',
              headers: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'oldPwd': this.initPwd,
                'newPwd': this.regPwd,
              }
            })
              .then(res => {
                if(res.data.error_code === 0){//说明请求成功,需要跳转到登录界面
                  this.$Toast('密码修改成功，请登录');
                  this.$router.push({
                    path: '/login'
                  })
                }else {
                  this.$Toast(res.data.data.error);
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
  .modifyPwdHeader {
    background: #974f02;
    color: #fff;
    font-size: .45rem;
    padding: .4rem;
    text-align: center;
    position: relative;
  }
  .modifyPwdHeader img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .3rem;
  }
  .modify_content {
    margin: 2rem auto;
  }
  .modify_content ul li > div img {
    width: .45rem;
    vertical-align: middle;
  }
  .modify_content ul li > div {
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
  .modify_content > ul li {
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
  }
</style>
