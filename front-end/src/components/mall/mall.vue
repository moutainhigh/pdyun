<template>
  <div style="background: #fff">
    <div class="mailheader">
      <router-link to="/home">
        <img src="./mallImgs/arrow_left.png">
      </router-link>
      <span>买卖街</span>
    </div>
    <div class="pros_nav_wrap">
      <p class="pros_nav_name">
        <span v-for="(proItem,index) of proType" :class="{active: active == index}" @click="selProInfo(index)">{{proItem.typeName}}</span>
      </p>
    </div>
    <ul class="proListC">
      <li class="mmj_list_wrap" v-for="gxItem of gxProListInfos">
        <router-link :to="{path: 'mall/productInfo',query: { goodsId: gxItem.id, imgPath: gxItem.imgLoadPath, proCode: gxItem.goodsCode }}">
          <p>
          <img :src="gxItem.imgLoadPath" >
          </p>
          <div>
            <p class="mmj_pro_tit_name"><span>{{gxItem.goodsName}}</span></p>
            <p><span>商品代码：</span><span>{{gxItem.goodsCode}}</span></p>
            <p><span>最新价：</span><span>{{gxItem.sellPointNew}}</span></p>
            <p><span>卖出价：</span><span>{{gxItem.buyPointMin}}</span></p>
          </div>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
  import store from '@/vuex/index';//引入vuex状态管理器
    export default {
      name: "mall",
      data(){
        return {
          active: -1,
          proType: [], /*商城产品的类别*/
          gxProListInfos: [],
        }
      },
      created(){
        store.commit('footShow');

        /* 一进入页面的时候，初始化获取所以的产品*/
        this.$http({
          method:'get',
          url: this.url + '/api/sub/getSubGoods',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            status: 2
          }
        }).then(res => {
          if (res.data.error_code === 0){
            if (res.data.data.list != ''){
              this.gxProListInfos = res.data.data.list;
            }else {
              this.$Toast('产品列表数据为空')
            }
          }
        });
        /*页面初始化的时候获取商城产品的类别*/
        this.$http({
          method:'get',
          url: this.url + '/api/sub/findGoodsType',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
        }).then(res => {
          if (res.data.error_code === 0) {
            if (res.data.data.goodsTypeList !=''){
              this.proType = res.data.data.goodsTypeList;
            }else {
              this.$Toast('请求数据为空');
            }
          } else {
            this.$Toast('请求数据失败');
          }
        })
      },
      methods: {
        selProInfo(index){
          this.active = index;

          this.$http({
            method:'get',
            url: this.url + '/api/sub/getSubGoods',
            header: {
              'Content-type': 'application/x-www-form-urlencoded',
            },
            params: {
              status: 2,
              goodsTypeId: this.proType[index].id
            }
          }).then(res => {
            if (res.data.error_code === 0){
              if (res.data.data.list != ''){
                this.gxProListInfos = res.data.data.list;
              }else {
                this.gxProListInfos = [];
                this.$Toast('产品列表数据为空');
              }
            }
          })
        }
      },
    }
</script>

<style scoped>
  .mailheader {
    background: #974f02;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: .4rem ;
    color: #fff;
    text-align: center;
    font-size: .45rem;
    position: relative;
  }
  .mailheader img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .3rem;
  }
  .pros_nav_wrap {
    width: 100%;
    overflow-x: auto;
    border-bottom: 1px solid #ddd;
  }
  .pros_nav_name {
    width: 140%;
    white-space: nowrap;
  }
  .pros_nav_name span {
    display: inline-block;
    width: 15%;
    text-align: center;
    padding: .3rem;
    font-size: .42rem;
  }
  .pros_nav_name span.active {
    color: #974f02;
  }
  li.mmj_list_wrap  a{
    margin-top: 2px;
    padding: .2rem .4rem;
    background: #ffffff;
    display: flex;
    justify-content: flex-start;
    border-bottom: 1px solid #f5f5f5;
  }
  li.mmj_list_wrap a > p {
    width: 30%;
  }
  li.mmj_list_wrap a > div {
    width: 70%;
    text-align: left;
    padding-left: .3rem;
    line-height: 1.8;
  }
  li.mmj_list_wrap a > div > p.mmj_pro_tit_name {
    font-weight: bold;
    font-size: .42rem;
  }




</style>
