<template>
    <div>
      <div>
        <p class="moneyMC">
          <a href="javascript:;" @click="this.backTop">
            <img src="./meImgs/arrow_left.png" alt="">
          </a>
          <span>我的资金</span>
        </p>
      </div>
      <div class="money_wrapC">
        <!--<p class="listTitle"><img src="./meImgs/moneym_list.png" alt=""> <span>流水单</span></p>-->
        <ul class="moneyListTab">
          <li @click="allOrderClick('allOrder')" :class="{active: (currentTab=='allOrder'?true:false)}">
            <p><img src="./meImgs/all_order.png" alt=""></p><span>全部订单</span>
          </li>
          <li @click="allOrderClick('yufuhuokuan')" :class="{active:(currentTab=='yufuhuokuan'?true:false)}">
            <p><img src="./meImgs/yufu_huokuan.png" alt=""></p><span>预付货款</span></li>
          <li @click="allOrderClick('tuihuotuikuan')" :class="{active:(currentTab=='tuihuotuikuan'?true:false)}">
            <p><img src="./meImgs/tuihuo_tuikuan.png" alt=""></p><span>退货退款</span></li>
        </ul>
        <ul class="moneyUserInfo">
          <li>
            <p><span>剩余货款(元):</span><span>{{myInfos.money}}</span></p>
            <!--<p><span>奖励金额(元):</span><span>{{myInfos.returnMoney}}</span></p>-->
          </li>
          <li>
            <p><span>奖励金额(元):</span><span>{{myInfos.returnMoney}}</span></p>
            <p style="text-align: right;padding-right: .5rem"><span class="zhuanhuanBtn" @click="changeBtn">转换</span></p>
          </li>
        </ul>
      </div>
      <all-order :is="currentTab" keep-alive></all-order>
      <footer class="moneyMCFooter">
        <div>
          <span @click="moneyrecord">预付货款</span>
          <span>|</span>
          <span @click="moneyOut">货款退款</span>
        </div>
      </footer>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  import yufuhuokuan from './moneyYuFuProMoney';
  import tuihuotuikuan from './backProbackMoney';
  import allOrder from './moneyAllOrders';

  export default {
    name: "money-management",
    data(){
      return{
        myInfos: '',
        active:true,
        currentTab: 'allOrder' // currentTab 用于标识当前触发的子组件
      }
    },
    created(){
      store.commit('footHide');
      //获取用户个人信息
      this.$http({
        method:'get',
        url: this.url + '/api/my/getMyInfo',
        header: {
          'Content-type': 'application/x-www-form-urlencoded'
        }
      }).then(res=>{  //请求成功
        if(res.data.error_code===0){  //返回成功
          this.myInfos=res.data.data.myInfo;
        }else{  //返回失败
          res.data.data.error != ''? this.$Toast(res.data.data.error) : this.$Toast('获取个人信息失败');
          this.$router.push({
            path: '/login'
          });
        }
      })
    },
    methods:{
      allOrderClick(tab){
        this.currentTab = tab; // tab 为当前触发标签页的组件名
      },
      moneyrecord(){
        this.$router.push({
          path: '/moneyRecord'
        });
      },
      moneyOut(){
        this.$router.push({
          path: '/moneyOut'
        });
      },
      changeBtn(){
        this.$http({
          method: 'post',
          url: this.url + '/api/sub/converUserMoney',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        }).then(res => {
          if (res.data.error_code == 0){
            this.$Toast('转换成功');

            /*页面再次刷新*/
            this.$http({
              method:'get',
              url: this.url + '/api/my/getMyInfo',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              }
            }).then(res=>{  //请求成功
              if(res.data.error_code===0){  //返回成功
                this.myInfos=res.data.data.myInfo;
              }else{  //返回失败
                res.data.data.error != ''? this.$Toast(res.data.data.error) : this.$Toast('获取个人信息失败');
                this.$router.push({
                  path: '/login'
                });
              }
            })
          }else {
            res.data.data.error != '' ? this.$Toast(res.data.data.error) : this.$Toast('转换失败');
          }
        })
      }
    },
    components: {
      allOrder,yufuhuokuan,tuihuotuikuan
    }
  }
</script>

<style scoped>
  .money_wrapC {
    background: #ffffff;
  }
  .moneyMC {
    position: relative;
    background: #974f02;
    color: #fff;
    padding: .4rem;
    text-align: center;
    font-size: .5rem;
  }
  .moneyMC img {
    position: absolute;
    top: .4rem;
    left: .3rem;
    width: .3rem;
  }
  .moneyListTab li p img {
    width: .7rem;
  }
  .moneyUserInfo {
    text-align: left;
  }

  .moneyUserInfo > li {
    border-top: 1px solid #ddd;
    padding: .35rem ;
    display: flex;
  }
  .moneyUserInfo > li p {
    width: 50%;
    /*text-align: center;*/
  }
  .moneyUserInfo > li:last-child{
    border-bottom: 1px solid #ddd;
  }
  .moneyListTab {
    border-top: 1px solid #ddd;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    background: #fff;
  }
  .moneyListTab li {
    width: 33.33%;
    padding: .2rem;
  }
  .moneyListTab li.active {
    color: #974f02;
  }

  .moneyMCFooter {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    background: #974f02;
    color: #fff;
    font-size: .45rem;
  }
  .moneyMCFooter > div {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .4rem 0;
  }
  .zhuanhuanBtn {
    width: 2rem;
    text-align: right;
    background: #974f02;
    color: #fff;
    padding: .2rem .4rem;
    border-radius: .2rem ;
  }
</style>
