<template>
  <div >
    <div class="mailheader">认购页面</div>
    <div class="pro_wrap">
      <ul class="pro_list_content">
        <li style="position: relative"  v-for="(item,index) of proInfos">
          <!--<router-link to="/subProductInfo">-->
            <div class="pro_img_content"><img :src='item.imgLoadPath' alt=""></div>
            <dl class="pro_detail">
              <dt>{{item.goodsName}}</dt>
              <dd class="pro_num"><i></i><span>数量:</span><span> {{item.subGoodsSpec}}</span></dd>
              <dd class="pro_price"><i></i><span>单价:</span><span>{{item.goodsSubPrice}}</span> </dd>
            </dl>
          <!--</router-link>-->
          <span class="sel_circle"  :class="{active: active == index}" @click="selCh(index,item)" ></span>
        </li>
      </ul>
    </div>
    <sub_common_infos v-bind="cdzInfos" :panelShow="curSta"></sub_common_infos>
  </div>
</template>

<script>
  import store from '@/vuex/index';//引入vuex状态管理器
  import Vue from 'vue';
  import {Toast} from 'vant';
  import sub_common_infos from './subCommonInfos';
  import Sub_product_info from "./subProductInfo";
  Vue.use(Toast);


  export default {
    name: "subscribe",
    components: {
      Sub_product_info,
      sub_common_infos
    },
    data(){
      return {
        active: -1,
        curSta: false,
        proInfos: [],
        cdzInfos: {},
      }
    },
    created: function () {
      store.commit('footShow');
      this.$http({
        method: 'get',
        url: this.url + '/api/sub/getSubGoods',
        header: {
          'Content-type': 'application/x-www-form-urlencoded'
        },
        params: {
          status: '1',
        }
      }).then(res => {
        this.proInfos = res.data.data.list;
        this.curSta = false;
      }).catch(error => {
        ;
      })
    },
    methods: {
      selCh(index,item) {
        this.active = this.active==index? -1 : index;
        if (this.active==index){
          this.curSta = true;
        }else {
          this.curSta = false;
        }
        this.cdzInfos.index = index;
        this.cdzInfos.num = item.subBuyNum;
        this.cdzInfos.totalPrice = item.goodsSubPrice;
        this.cdzInfos.proname = item.goodsName;
        this.cdzInfos.id = item.id;
      }
    },
  }
</script>

<style scoped>
  .processMeter {
    padding: .3rem;
    text-align: left;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }
  .dcBg {
    width: 80%;
    background: #178f4a;
    border-radius: .3rem;
    height: .6rem;
    position: relative;
  }
  .scBg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: #e21918;
    border-radius: .3rem;
  }
  .mailheader {
    background: #2eae64;
    text-align: center;
    color: #fff;
    padding: .2rem ;
  }
  .proTypeName li {
    color: #fff;
    overflow: hidden;
    text-overflow: ellipsis;
    -o-text-overflow:ellipsis;
    white-space: nowrap;
  }

  .yjgBtn button {
    color: #fff;
  }
  .xznum input {
    background: none;
    text-align: center;
  }
  .xznum span {
    margin-right: .2rem;
  }
  .proListC li {
    border-bottom: 1px solid #ddd;
    color: #333;
    margin-bottom: .1rem;
    padding: .1rem;
  }
  .proListC li:last-child {
    border-bottom: none;
  }
  .pro_list_content  {
    display: flex;
    justify-content: space-between;
    width: 100%;
    margin-top: .3rem;
  }
  .pro_list_content li {
    width: 49%;
    background: #fff;
    margin-bottom: .2rem;
  }
  .pro_list_content li div.pro_img_content {
    width: 96%;
    margin: auto;
  }
  .pro_list_content {
    width: 100%;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    align-items: center;
  }
  .pro_detail {
    padding: .2rem .4rem;
    line-height: 2;
    text-align: left;
    position: relative;
  }
  .pro_detail dt {
    font-weight: bold;
  }
  .pro_detail dd {
    font-size: .3rem;
  }
  .pro_detail dd.pro_uname i{
    background: url("./subscribImgs/product_name.png") center center no-repeat;
    background-size: contain;
  }
  .pro_detail dd.pro_num i{
    background: url("./subscribImgs/product_num.png") center center no-repeat;
    background-size: contain;
  }
  .pro_detail dd.pro_price i{
    background: url("./subscribImgs/product_price.png") center center no-repeat;
    background-size: contain;
  }
  .pro_detail dd i {
    width: .35rem;
    height: .35rem;
    display: inline-block;
    margin-right: .2rem;
  }
  .sel_circle {
    position: absolute;
    width: .6rem;
    height: .6rem;
    bottom: .4rem;
    right: .3rem;
    background: url("./subscribImgs/product_nocheck.png") center center no-repeat;
    background-size: contain;
  }
  .sel_circle.active {
    background: url("./subscribImgs/product_checked.png") center center no-repeat;
    background-size: contain;
  }
</style>
