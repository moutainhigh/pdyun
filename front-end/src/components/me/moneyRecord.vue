<template>
    <div class="">
      <p class="recordC">
        <a href="javascript:;" @click="this.backTop">
          <img src="./meImgs/arrow_left.png" alt="">
        </a>
        <span>预付货款</span>
      </p>
      <div class="recordDetail">
        <div class="recordInfos">
          <span>充值金额：</span><input class="enterMoney" type="text" placeholder="请输入充值金额" v-model="money"/>

        </div>
        <div class="recordInfos">
          <span>银行卡号：</span><input class="enterMoney" type="text" placeholder="请输入银行卡号" v-model="bankAccount"/>
        </div>
        <div class="recordInfos">
          <span>开户银行：</span>
          <select v-model="bankType">
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
        </div>
        <div class="recordInfos">
          <span>持卡人姓名：</span><input class="enterMoney" maxlength="10" type="text" placeholder="请输入姓名" v-model="chnName"/>
        </div>
        <div class="recordInfos">
          <span>身份证号：</span><input class="enterMoney" type="text" maxlength="18" placeholder="请输入身份证号" v-model="idCard"/>
        </div>
        <div class="recordInfos">
          <span>预留手机号：</span><input class="enterMoney" type="text" disabled placeholder="请输入银行预留手机号" v-model="mobile"/>
        </div>
        <div>
          <input class="recordBtn" type="button" value="确认充值" @click="moneyrecord"/>
        </div>
        <form id="payForm" action="https://newpay.ips.com.cn/psfp-entry/gateway/payment.do" method="post" style="display: none;">
          <input type="hidden" placeholder="" name="pGateWayReq" value=""/>
        </form>
      </div>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  import {isCardNo} from './valifyIDCard';
    export default {
        name: "money-record",
        data(){
          return {
            money: '',
            pGateWayReq: '',
            payUrl: '',
            bankAccount: '',
            bankType: '',
            chnName: '',
            idCard: '',
            mobile: localStorage.getItem('userMobile'),
          };
        },
        methods: {
          moneyrecord(){
            //首先要先做各个输入框的验证，验证成功后再发请求
            var reg = /^[\u4e00-\u9fa5]{2,4}$/i;//身份证姓名的验证只能是2-4个汉字！
            if (!this.money){
              this.$Toast('请输入金额，不能为空');
              return false;
            }else if(this.bankAccount == '' || this.bankAccount == null){
              this.$Toast('卡号不能为空');
              return false;
            }else if(this.bankType == null || this.bankType == ''){
              this.$Toast('开户行不能为空');
              return false;
            }else if (!reg.test(this.chnName)){
              this.$Toast('请输入真实姓名，只能是2-4个汉字！');
              return false;
            }else if(this.idCard == '') {
              this.$Toast('身份证不能为空');
              return false;
            }else if (!isCardNo(this.idCard)){
              this.$Toast('您输入的身份证号码不正确，请重新输入');
              return false;
            }else if( !this.mobile){
              this.$Toast('您输入的手机号不能为空，请重新输入');
              return false;
            }else {
              this.$http({
                method: 'post',
                url: this.url + '/api/moneyRecord/huanxun/gateway',
                header: {
                  'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                  money: this.money,
                  bankAccount: this.bankAccount,
                  bankType: this.bankType,
                  chnName: this.chnName,
                  idCard: this.idCard,
                  mobile: this.mobile
                }
              }).then(res => {
                if (res.data.error_code === 0) {
                  var data = res.data.data;
                  $('input[name="pGateWayReq"]').val(data.pGateWayReq);
                  jQuery('#payForm').submit();

                } else if (res.data.error_code === 50) {
                  this.$router.push({
                    path: '/cardManagement',
                  })
                } else {
                  this.$Toast(res.data.data.error);
                }
              }).catch(error => {
                ;
              })
            }
          }
        },
        created(){
          store.commit('footShow');
          this.$http({
            method: 'get',
            url: this.url + '/api/my/getBankCard',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            }
          }).then(res => {
            var data = res.data.data.cardInfo;
            if (res.data.error_code === 0) {
              if (data == ''){
                this.$router.push({
                  path: '/cardManagement',
                })
              }else {
                this.bankAccount = data.bankAccount;
                this.chnName = data.chnName;
                this.idCard = data.idCard;
                this.bankType = data.bankName;
              }
            } else {
              this.$Toast("系统繁忙");
            }
          }).catch(error => {
            ;
          })
        }
    }
</script>

<style scoped>
  .recordC {
    background: #974f02;
    color: #fff;
    padding: .4rem;
    text-align: center;
    font-size: .45rem;
    position: relative;
  }
  .recordC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .recordDetail {
    padding-top: 1rem;
  }
  .recordInfos {
    padding: .2rem;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .recordInfos span {
    width: 25%;
    text-align: right;
  }
  .recordInfos  input,.recordInfos  select{
    width: 50%;
    text-align: left;
  }
  .recordBtn {
    margin-top: 1rem;
    width: 60%;
    background: #974f02;
    color: #fff;
    border-radius: .6rem;
    padding: .25rem 0;
  }
  .enterMoney {
    border: 1px solid #dddddd;
    padding: .1rem;
    border-radius: .3rem;
  }
</style>
