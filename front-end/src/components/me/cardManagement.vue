<template>
    <div>
      <p class="bindCardMC">
        <a href="javascript:;" @click="this.backTop">
          <img src="./meImgs/arrow_left.png" alt="">
        </a>
        <span>填写银行卡信息</span>
      </p>
      <p class="alertContent">为了保证资金安全，只能绑定认证用户本人的银行卡，暂不支持信用卡。</p>
      <div>
        <ul class="userBankInfo">
          <li><span>持卡人姓名:</span><input id="holdCardName" type="text" :disabled="!initS" placeholder="持卡人姓名" maxlength="10" v-model="cardName"></li>
          <li><span>身份证号:</span><input type="text"  placeholder="身份证号" maxlength="18" v-model="idCode"></li>
          <li><span>银行卡号:</span><input type="text" placeholder="银行卡号"  v-model="bankCard"></li>
          <li class="recordInfos">
            <span>开户银行:</span>
            <select v-model="kaiHuBank" >
              <option disabled value="">请选择开户行</option>
              <!--<option value="1100">工商银行</option>-->
              <!--<option value="1101">农业银行</option>-->
              <option value="1102">招商银行</option>
              <option value="1103">兴业银行</option>
              <!--<option value="1104">中信银行</option>-->
              <!--<option value="1107">中国银行</option>-->
              <!--<option value="1108">交通银行</option>-->
              <option value="1109">浦发银行</option>
              <option value="1110">民生银行</option>
              <!--<option value="1111">华夏银行</option>-->
              <!--<option value="1112">光大银行</option>-->
              <!--<option value="1113">北京银行</option>-->
              <option value="1114">广发银行</option>
              <!--<option value="1115">南京银行</option>-->
              <!--<option value="1116">上海银行</option>-->
              <!--<option value="1117">杭州银行</option>-->
              <!--<option value="1118">宁波银行</option>-->
              <!--<option value="1119">邮储银行</option>-->
              <!--<option value="1120">浙商银行</option>-->
              <!--<option value="1121">平安银行</option>-->
              <!--<option value="1122">东亚银行</option>-->
              <!--<option value="1123">渤海银行</option>-->
              <!--<option value="1124">北京农商行</option>-->
              <!--<option value="1127">浙江泰隆商业银行</option>-->
              <option value="1106">建设银行</option>
            </select>
          </li>
          <li><span>预留手机号:</span></li>
          <li class="ylPhoneTwoSel">
              <input type="radio" name="selXZ" checked ><span>使用注册手机号 </span> <span> {{ mobile}}</span>（不可修改）
          </li>
        </ul>
        <div class="bankBtn">
          <button @click="qeurenBank" v-show="initS">确认</button>
          <button @click="updateBankInfo" v-show="!initS">修改信息</button>
        </div>
      </div>
    </div>
</template>

<script>
  import Vue from 'vue';
  import {Toast} from 'vant';
  Vue.use(Toast);
  import store from '@/vuex/index';
  import {isCardNo} from './valifyIDCard';
  export default {
    name: "card-management",
    data(){
      return{
        mobile: localStorage.getItem('userMobile'),
        cardName: '',//持卡人
        idCode: '',//身份证号
        bankCard: '',//银行卡号
        kaiHuBank: '',//开户行
        initS: true,
      }
    },
    created(){
      store.commit('footShow');
      //接收router跳转时带的参数
      this.$http({
        method: 'get',
        url: this.url + '/api/my/getBankCard',
        header: {
          'Content-type': 'application/x-www-form-urlencoded'
        }
      }).then(res => {
        if (res.data.error_code === 0){
          if (res.data.data.cardInfo === ''){//需要绑定银行卡的
            this.initS = true;
          }else {
            this.initS = false;
            this.cardName = res.data.data.cardInfo.chnName;
            this.idCode = res.data.data.cardInfo.idCard;
            this.bankCard = res.data.data.cardInfo.bankAccount;
            this.kaiHuBank = res.data.data.cardInfo.bankName;
            this.mobile = res.data.data.cardInfo.phone
          }
        }
      })
    },
    methods: {
      qeurenBank(){
        //首先要先做各个输入框的验证，验证成功后再发请求
        var reg = /^[\u4e00-\u9fa5]{2,4}$/i;//身份证姓名的验证只能是2-4个汉字！
        if (!reg.test(this.cardName)){
          Toast('请输入真实姓名，只能是2-4个汉字！');
          return false;
        }else if(this.idCode == '') {
          Toast('身份证不能为空');
          return false;
        }else if (!isCardNo(this.idCode)){
          Toast('您输入的身份证号码不正确，或者年龄不符合18岁到60之间');
          return false;
        }else if(this.bankCard == '' || this.bankCard == null){
          Toast('卡号不能为空');
          return false;
        }else if(this.kaiHuBank == null || this.kaiHuBank == ''){
          Toast('开户行不能为空');
          return false;
        }else{
          this.$http({
            method: 'post',
            url: this.url + '/api/my/addBankCard',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
            params: {
              chnName: this.cardName,//持卡人
              bankAccount: this.bankCard,//卡号
              bankName: this.kaiHuBank,//开户行
              phone: this.mobile,//手机号码
              idCard: this.idCode,//身份证号码
            }
          }).then(res => {
            if(res.data.error_code === 0){
              Toast('银行卡绑定成功');
              this.$router.push({
                path: '/me'
              })
            }
          }).catch(error => {
            ;
          })
        }
      },
      updateBankInfo(){
        //首先要先做各个输入框的验证，验证成功后再发请求
        var reg = /^[\u4e00-\u9fa5]{2,4}$/i;//身份证姓名的验证只能是2-4个汉字！
        if (!reg.test(this.cardName)){
          Toast('请输入真实姓名，只能是2-4个汉字！');
          return false;
        }else if(this.idCode == '') {
          Toast('身份证不能为空');
          return false;
        }else if (!isCardNo(this.idCode)){
          Toast('您输入的身份证号码不正确，请重新输入');
          return false;
        }else if(this.bankCard == '' || this.bankCard == null){
          Toast('卡号不能为空');
          return false;
        }else if(this.kaiHuBank == null || this.kaiHuBank == ''){
          Toast('开户行不能为空');
          return false;
        }else{
          this.$http({
            method: 'post',
            url: this.url + '/api/my/updateBankCard',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
            params: {
              'chnName': this.cardName,//持卡人
              'bankAccount': this.bankCard,//卡号
              'bankName': this.kaiHuBank,//开户行
              'phone': this.mobile,//手机号码
              'idCard': this.idCode,//身份证号码
            }
          }).then(res => {
            if(res.data.error_code === 0){
              Toast('银行卡修改成功');
              this.$router.push({
                path: '/me'
              })
            }
          }).catch(error => {
            ;
          })
        }
      },
    }
  }
</script>

<style scoped>
  .bindCardMC {
    background: #974f02;
    color: #fff;
    text-align: center;
    position: relative;
    padding: .4rem;
    font-size: .45rem;
  }
  .bindCardMC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    color: #fff;
    width: .3rem;
  }

  .alertContent {
    background: yellow;
    color: red;
    padding: .1rem;
    font-size: .2rem;
  }
  .userBankInfo {
    padding: .1rem .2rem;
  }
  .userBankInfo input{
    margin-left: 15%;
    width: 50%;
  }
  .userBankInfo li > select {
    margin-left: 12%;
    width: 50%;
  }
  .userBankInfo li {
    padding: .25rem 0;
    border-bottom: 1px solid #dddddd;
    text-align: left;
    width: 100%;
  }
  .userBankInfo li > span {
    display: inline-block;
    width: 25%;
    text-align: right;
  }

  .bankBtn {
    width: 60%;
    margin: 1rem auto;
  }
  .ylPhoneTwoSel {
    margin-left: .8rem !important;
    font-size: .35rem;
  }

  .ylPhoneTwoSel input{
    width: .5rem !important;
    vertical-align: bottom;
    margin-right: .3rem;
    margin-left: 0 !important;
  }
  .bankBtn button {
    width: 100%;
    background: #974f02;
    color: #fff;
    text-align: center;
    border-radius: .5rem;
    padding: .2rem;
  }

  .srYZCode input {
    width: 2.5rem !important;
    margin-left: .8rem;
    padding: .1rem;
  }
</style>
