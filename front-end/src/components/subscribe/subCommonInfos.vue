<template>
    <div>
      <div v-if="panelShow" class="buy">
        <p>您选购了1件商品</p>
        <div class="buyChild">
          <div class="allNumC">
            <img src="./subscribImgs/product_all_num.png" alt="">
            总数量：<span class="totalN">{{num}}</span>
          </div>
          <div class="totalM">
            <img src="./subscribImgs/product_all_money.png" alt="">
            总金额：<span class="allMoney">{{num*totalPrice}}</span>
          </div>
          <div class="buyBtn" >
            <img src="./subscribImgs/product_buy_all.png" alt="" @click="buy()">
          </div>
        </div>
      </div>
      <div class="hidProInfo" v-show="currentState">
        <div class="hidHeader">
          <img src="./subscribImgs/1511172136562.jpg" alt="">
          <div>
            <p>{{proname}}</p>
            <p>总计金额：<span class="totalMoney">{{num*totalPrice}}</span></p>
          </div>
          <div class="closeIcon"><img src="./subscribImgs/icon_close.png" alt="" @click="closeCur()"></div>
        </div>
        <ul class="hidUlList">
          <li><span>数量：</span><span>{{num}}</span></li>
          <li><span>单价：</span><span>{{totalPrice}}</span></li>
          <li><span>服务费：</span><span>{{subSerScale}}</span></li>
        </ul>
        <button type="button" class="qrBtnBuy" @click="quRenBuyPro()">认购</button>
      </div>
      <div class="hidShadowC" v-show="currentState"></div>
    </div>
</template>

<script>
  import store from '@/vuex/index';//引入vuex状态管理器
  import Vue from 'vue';
  import {Toast} from 'vant';
  Vue.use(Toast);
    export default {
      name: "sub-common-infos",
      data(){
        return {
          currentState: false,
          subSerScale: ''
        }
      },
      props:['num','totalPrice','panelShow','proname','id'],
      methods: {
        test(){
          console.log();
        },
        buy(){
          //要先判断是否登录了
          let token = localStorage.getItem('value');
          if (token){
            //如果是已经登录的状态了,
            this.currentState = true;
            //调取服务费的接口
            this.$http({
              method: 'get',
              url: this.url + '/api/sub/getScales',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              }
            }).then(res => {
              if (res.data.error_code ===0 ){
                this.subSerScale = (res.data.data.scalesBean.subServiceScale*(this.num*this.totalPrice)).toFixed(2);
              }
            }).catch(error => {
              ;
            })
          }else {
            this.$router.push({
              path: '/login'
            })
          }
        },
        quRenBuyPro(){
          this.$http({
            method: 'post',
            url: this.url + '/api/sub/subMake',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
            params: {
              'goodsId': this.id,
              'goodsName': this.proname,
              'holdNum': this.num,
              'money': this.num * this.totalPrice,
              'serviceFee': this.subSerScale,
              'buyPoint': this.totalPrice,
            }
          }).then(res => {
            if (res.data.error_code === 0){
              Toast('认购产品成功');
              //跳转到持仓页面
              this.$router.push({
                path: '/orderManagement',
              })
            }else {
              Toast(res.data.data.error);
            }
          }).catch(error => {
            ;
          })
        },
        closeCur(){
          this.currentState = false;
        }

      }
    }
</script>

<style scoped>
  html,body{
    height:100%
  }
  .buy{
    position: fixed;
    bottom: 1.3rem;
    width: 100%;
    left: 0;
    background: #fff;
    text-align: left;
    padding: .2rem .3rem;
    border-top: 1px solid #ddd;
    font-size: .3rem;
  }
  .buyChild {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .allNumC img,.totalM img {
    width: .35rem;
    margin-right: .1rem;
  }
  .buyBtn img {
    width: 3rem;
  }
  .hidProInfo {
    background: #fff;
    position: fixed;
    bottom: 0;
    z-index: 9999;
    left: 0;
    width: 100%;
  }
  .totalMoney {
    font-size: .5rem;
    color: #e21918;
  }
  .hidHeader {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding: 0 .5rem;
    position: relative;
  }
  .hidUlList {
    text-align: left;
    padding: .5rem;
    line-height: 2;
  }
  .qrBtnBuy {
    font-size: .5rem;
    background: #178f4a;
    color: #fff;
    width: 100%;
    padding: .3rem 0 ;
  }

  .hidShadowC {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.5);
  }
  .hidHeader img {
    width: 3rem;
    margin-top: -.3rem;
    margin-right: .5rem;
  }
  .hidHeader > div {
    line-height: 2;
    text-align: left;
  }
  .closeIcon {
    position: absolute;
    top: .2rem;
    right: 0;
  }
  .closeIcon img {
    width: .5rem;
  }
</style>
