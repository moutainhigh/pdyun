<template>
    <div>
      <div>
        <p class="mmj_pro_detail_title"><router-link to="/mall"><img src="./mallImgs/arrow_left.png" alt=""></router-link><span>买卖街</span></p>
      </div>
      <div class="proDetailInfo">
        <p>
          <span  v-for="(item,index) in tabs" :class="{active:index == num}" @click="tab(index)">{{item}}</span>
        </p>
      </div>
      <div class="proListInfo" ref="proListInfo">
        <div v-show="productIntroInfoIsshow" class="productIntroInfo">
          <p><img :src="imgPath" alt=""></p>
          <ul class="userBuyInfo">
            <li>
              <p class="pro_detail_name">{{goodStatistics.goodsName}}</p>
              <p><span>商品代码: </span><span>{{proCode}}</span></p>
            </li>
            <li class="pro_detail_care">
              <!--<p><img src="./mallImgs/star_sc.png" alt=""></p>-->
              <p><span class="starState" :class="{active: shouCState}" @click="starXZ"></span></p>
              <span :class="{active: shouCState}">收藏</span>
            </li>
          </ul>
          <ul class="pro_detail_list">
            <li>
              <p><span>今日销量: </span><span>{{goodStatistics.todayTotalNum}}</span></p>
              <p><span>今日销售金额: </span><span>{{goodStatistics.todayTotalMoney}}</span></p>
            </li>
            <li>
              <p><span>平均价格: </span><span>{{goodStatistics.averagePrice}}</span></p>
              <p><span>首单价格: </span><span>{{goodStatistics.firstPrice}}</span></p>
            </li>
            <li>
              <p><span>最高价格: </span><span>{{goodStatistics.maxPrice}}</span></p>
              <p><span>最低价格: </span><span>{{goodStatistics.minPrice}}</span></p>
            </li>
            <li>
              <p><span>待售数量: </span><span>{{goodStatistics.forSell}}</span></p>
              <p><span>库存总量: </span><span>{{goodStatistics.totalLeftNum}}</span></p>
            </li>
          </ul>
          <div class="pro_detail_data_wrap">
            <p><span>价格</span><span>数量</span><span>选择</span></p>
            <ul class="pro_detail_list_data">
              <li v-for="(gdItem,index) of gdListInfo" >
                <span>{{gdItem.buyPoint.toFixed(2)}}</span>
                <span>{{gdItem.totalHoldNum}}</span>
                <!--<p><span class="xzImgContent" :class="{curActive:curActive == index}" @click="selXZ(index)"></span></p>-->
                <p><input type="checkbox"  @click="selXZ(index,gdItem.buyPoint)"  ref="curInput" ></p>
              </li>
            </ul>
          </div>
          <div class="pro_detail_buy_wrap">
            <p class="buy_num_wrap"><span>购买数量</span><input type="number" v-model="gdNumber"></p>
            <p><img src="./mallImgs/pro_buy_btn.png" @click="buyGDPro"></p>
          </div>
        </div>
        <div v-show="detailContentIsshow" class="detailContent" >
          <p><img :src="proDetailImgInfo" alt=""></p>
        </div>
      </div>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  export default {
    name: "productInfo",
    created(){
      store.commit('footShow');

      this.initData();/*调用初始化页面的数据*/

      /*进入页面的时候要判断当前的产品是否被收藏*/
      this.$http({
        method:'get',
        url: this.url + '/api/sub/judgeGoodsStore',
        header: {
          'Content-type': 'application/x-www-form-urlencoded',
        },
        params: {
          goodsId: this.routerParams,
        },
      }).then(res => {
        if (res.data.error_code === 0){
          if (res.data.data != ''){
            this.shouCState = res.data.data.exist;
          }
        }else {
          this.$Toast('请求数据失败')
        }
      });
    },
    data(){
      return {
        test: false,
        tabs: ["商品","详情"],
        num: 0,
        productIntroInfoIsshow:true,
        proIntroduceIsshow:false,
        detailContentIsshow:false,
        goodStatistics: {},
        proCode: '',
        imgPath: '',
        gdListInfo: [],
        active: false,
        curActive: -1,
        gdNumber: '',
        gdListPrice: [],/*购买挂单产品金额数组*/
        routerParams: '',
        shouCState: '',/*页面初始化的收藏状态*/
        proDetailImgInfo: '',
      }
    },
    methods:{
      initData(){
        /*获取父路由跳转带过来的参数*/
        this.routerParams = this.$route.query.goodsId;
        this.proCode = this.$route.query.proCode;
        this.imgPath = this.$route.query.imgPath;

        this.$http({
          method:'get',
          url: this.url + '/api/sub/getHangDetail',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            goodsId: this.routerParams,
          },
        }).then(res => {
          if (res.data.error_code === 0){
            this.goodStatistics = res.data.data.goodStatistics;
            if (res.data.data.list != ''){
              this.gdListInfo = res.data.data.list;
            }else {
              this.gdListInfo = [];
              this.$Toast('还没有挂单的产品');
            }
          }else {
            this.$Toast('请求数据失败')
          }
        });
      },
      tab(index) {
        if(index==0){
          this.productIntroInfoIsshow=true;
          this.proIntroduceIsshow=false;
          this.detailContentIsshow=false
        }else if(index==1){
          this.productIntroInfoIsshow=false;
          this.proIntroduceIsshow=false;
          this.detailContentIsshow=true;
          this.$http({
            method:'get',
            url: this.url + '/api/sub/getGoodsDetailImg',
            header: {
              'Content-type': 'application/x-www-form-urlencoded',
            },
            params: {
              goodsId: this.routerParams,
            },
          }).then(res => {
            if (res.data.error_code === 0){
              if (res.data.data.imgDetailPath){
                this.proDetailImgInfo = res.data.data.imgDetailPath;
              }else {
                this.$Toast('还没有详情信息');
              }
            }
          })
        }
        this.num = index;
      },
      selXZ(index){

        // this.curActive = this.curActive == index? -1 : index;


        if (!this.$refs.curInput[index].checked){
        // if (this.gdListPrice[index]){
          this.gdListPrice[index] = '';/*从指定位置删除一个元素*/
        }else {
          this.gdListPrice[index]=this.gdListInfo[index].buyPoint.toFixed(2);/*从指定位置删除0个元素,并添加一个元素*/
        }



        console.log(JSON.stringify(this.gdListPrice));

      },
      starXZ(){
        if (!this.shouCState) {
          /*当是收藏状态的时候，要调取接口添加到收藏列表中*/
          this.$http({
            method:'post',
            url: this.url + '/api/sub/goodsStore',
            header: {
              'Content-type': 'application/x-www-form-urlencoded',
            },
            params: {
              goodsId: this.routerParams,
            },
          }).then(res => {
            if (res.data.error_code === 0){
              if (res.data.data.error){
                this.$Toast(res.data.data.error);
                this.shouCState = this.shouCState;
              }else {
                this.$Toast('添加收藏成功');
                this.shouCState = !this.shouCState;
              }
            }else {
              this.$Toast('添加收藏失败');
            }
          })
        }else {
          /*当是取消收藏状态的时候，要调取接口添加到收藏列表中*/
          this.$http({
            method:'post',
            url: this.url + '/api/sub/goodsStoreCancel',
            header: {
              'Content-type': 'application/x-www-form-urlencoded',
            },
            params: {
              goodsId: this.routerParams,
            },
          }).then(res => {
            if (res.data.error_code === 0){
              if (res.data.data.error){
                this.$Toast(res.data.data.error);
                this.shouCState = this.shouCState;
              }else {
                this.$Toast('取消收藏成功');
                this.shouCState = !this.shouCState;
              }
            }else {
              this.$Toast('取消收藏失败');
            }
          })
        }
      },
      buyGDPro(){
        /*当点击购买挂单产品按钮的时候，要先判断挂单数量不能为空，挂单规格不能为空*/

        // for(var i = 0;i < this.$refs.curInput.length;i++){
        //   if(this.$refs.curInput[i].checked){
        //     this.gdListPrice.push()
        //   }
        // }


        if (this.gdNumber !=''){
          if (this.gdListPrice != ''){
            this.$http({
              method: 'post',
              url: this.url + '/api/sub/buyHangGoodsNew',
              header: {
                'Content-type': 'application/x-www-form-urlencoded',
              },
              params: {
                goodsId: this.routerParams,
                buyNum: this.gdNumber,
                buyPoints: this.gdListPrice.toString(),
              },
            }).then(res => {
              if (res.data.error_code === 0){

                this.$Toast('购买挂单商品成功');

                this.initData();/*重新渲染一下页面*/

                this.gdNumber = '';

              }else {
                res.data.data.error =! ''?this.$Toast(res.data.data.error): this.$Toast('购买商品失败');
              }
            })
          }else {
            this.$Toast('还没有选择挂单的产品');
          }
        }else {
          this.$Toast('挂单数量不能为空');
        }
      }
    }
  }
</script>

<style scoped>
  .mmj_pro_detail_title {
    background: #974f02;
    color: #fff;
    font-size: .45rem;
    padding: .4rem;
    text-align: center;
    position: relative;
  }
  .mmj_pro_detail_title img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .3rem;
  }
  .proDetailInfo {
    position: relative;
    background: #fff;
    padding-top: .3rem;
  }
  .proDetailInfo img {
    position: absolute;
    top: .3rem;
    left: .3rem;
    width: .4rem;
  }
  .proDetailInfo p {
    display: flex;
    justify-content: space-around;
    width: 60%;
    align-items: center;
    margin: auto;
  }
  .proDetailInfo p span {
    width: 20%;
    padding-bottom: .3rem;
    font-weight: bold;
  }
  .proDetailInfo p span.active {
    color: #974f02;
    border-bottom: 2px solid #974f02;
  }
  .proListInfo {
    background: #fff;
  }
  .detailContent {
    text-align: left;
    padding: .1rem;
    background: #fff;
  }
  .detailContent > p > img {
    width: 100%;
  }
  .productIntroInfo {
    text-align: left;
  }
  .productIntroInfo > p > img {
    width: 100%;
  }
  .userBuyInfo {
    line-height: 1.8;
    border-bottom: 1px solid #ddd;
    padding: 0 .4rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .userBuyInfo img {
    width: .5rem;
  }
  .userBuyInfo li.pro_detail_care {
    line-height: 1.2;
    text-align: center;
  }
  .pro_detail_name {
    font-size: .45rem;
    font-weight: bold;
  }
  .pro_detail_list {
    padding: .4rem;
    border-bottom: 1px solid #ddd;
  }
  .pro_detail_list li {
    display: flex;
  }
  .pro_detail_list li p {
    width: 49%;
    color: #999;
    line-height: 1.8;
  }
  .pro_detail_list li p:nth-child(2) span:first-child {
    display: inline-block;
    width: 60%;
    text-align: right;
    margin-right: .15rem;
  }
  .pro_detail_data_wrap > p {
    border-bottom: 1px solid #ddd;
  }
  .pro_detail_data_wrap > p span{
    padding: .3rem 0;
    display: inline-block;
    width: 33.3%;
    text-align: center;
    font-size: .4rem;
    font-weight: bold;
    color: #555;
  }
  .pro_detail_list_data li {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .25rem 0;
    border-bottom: 1px solid #ddd;
  }
  .pro_detail_list_data li * {
    width: 33.3%;
    text-align: center;
  }
  .pro_detail_list_data li > p img {
    width: .6rem;
  }
  .pro_detail_buy_wrap {
    margin-top: .3rem;
    display: flex;
    justify-content: space-between;
    padding: .4rem;
    background: #fff;
    padding-bottom: 1rem;
  }
  .pro_detail_buy_wrap p {
    margin: 0 .2rem;
  }
  .pro_detail_buy_wrap p img {
    width: 2rem;
  }
  .buy_num_wrap input{
    width: 2.5rem;
    border: 1px solid #ddd;
    background: #eeeeee;
    margin-left: .2rem;
    padding: .1rem;
  }
  .xzImgContent {
    display: inline-block;
    background: url('./mallImgs/pro_unchecked.png') center center no-repeat;
    background-size: contain;
    width: .5rem !important;
    height: .5rem;
  }
  .xzImgContent.curActive {
    display: inline-block;
    background: url('./mallImgs/pro_checked.png') center center no-repeat;
    background-size: contain;
    width: .5rem !important;
    height: .5rem;
  }
  .starState {
    display: inline-block;
    background: url('./mallImgs/star_sc.png') center center no-repeat;
    background-size: contain;
    width: .5rem !important;
    height: .5rem;
  }
  .starState.active {
    display: inline-block;
    background: url('./mallImgs/star_over.png') center center no-repeat;
    background-size: contain;
    width: .5rem !important;
    height: .5rem;
  }
  .pro_detail_care > span.active {
    color: #553006;
  }
</style>
