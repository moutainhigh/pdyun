<template>
    <div>
      <p class="orderMC">
        <a href="javascript:;" @click="this.backTop">
          <img src="./meImgs/arrow_left.png" alt="">
        </a>
        <span>订单管理</span>
      </p>
      <ul class="buy_sell_Con">
        <li @click="toggleTab('mybuyp')" :class="{active: (currentTab == 'mybuyp'?true:false)}">
          <img src="./meImgs/order_buy.png" alt=""><span>我持仓的</span>
        </li>
        <li @click="toggleTab('mygdp')" :class="{active: (currentTab == 'mygdp'?true:false)}">
          <img src="./meImgs/order_guadan.png" alt=""><span>我挂单的</span>
        </li>
        <li @click="toggleTab('mysellp')" :class="{active: (currentTab == 'mysellp'?true:false)}">
          <img src="./meImgs/order_out.png" alt=""><span>我卖出的</span>
        </li>
      </ul>
      <!-- 子组件，显示不同的 tab-->
      <!-- is 特性动态绑定子组件-->
      <!-- keep-alive 将切换出去的组件保留在内存中-->
      <mybuyp :is="currentTab" keep-alive></mybuyp>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  import mybuyp from './myBuySomthing';
  import mysellp from './mySellSomthing';
  import mygdp from './myGuaDanSomthing';

  export default {
    name: "order-management",
    data(){
      return {
        currentTab: 'mybuyp' // currentTab 用于标识当前触发的子组件
      }
    },
    created(){
      store.commit('footShow');
    },
    components: {// 声明子组件
      mybuyp,mygdp,mysellp
    },
    methods: {
      toggleTab: function(tab) {
        this.currentTab = tab; // tab 为当前触发标签页的组件名
      }
    }
  }
</script>

<style scoped>
  .orderMC {
    background: #974f02;
    color: #fff;
    padding: .4rem;
    text-align: center;
    font-size: .45rem;
    position: relative;
  }
  .orderMC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .65rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .order_searchC > div {
    width: 80%;
    background: #974f02;
    margin: auto;
    padding: .2rem;
    display: flex;
    justify-content: space-between;
  }
  .order_searchC img {
    width: .6rem;
  }
  .buy_sell_Con {
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: #f6f6f6;
    color: #333;
    border-bottom: 1px solid #ddd;
  }
  .buy_sell_Con li {
    display: flex;
    justify-content: space-around;
    align-items: center;
    flex-direction: column;
    padding: .3rem;
    font-size: .4rem;
  }
  .buy_sell_Con img {
    width: 1.6rem;
    margin-bottom: .3rem;
  }
  .buy_sell_Con li.active {
    color: #974f02;
  }
</style>
