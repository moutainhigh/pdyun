<template>
    <div>
      <div class="tuangou_wrap">
        <p><img src="./meImgs/arrow_left.png" alt="" @click="this.backTop"><span>团购</span></p>
      </div>
      <div class="tuangou_pro_wrap">
        <ul >
          <li class="tuangou_content" v-for="(item,index) of tuangouProInfos">
            <p>
              <img :src="item.imgLoadPath">
            </p>
            <div>
              <p class="tuangou_tit_name"><span>{{item.goodsName}}</span></p>
              <p><span>剩余量/总量：</span><span>{{item.subGoodsLeftNum}}/{{item.subGoodsNum}}</span></p>
              <p><span>{{item.subBuyNum==10?'组一':'组二'}}: </span><span>{{item.subGoodsSpec}}</span></p>
              <p class="tuangou_pro_price commonRed"><span>&yen;</span><span> {{item.goodsSubPrice}}</span></p>
            </div>
            <p class="check_icon"><i :class="{active: active == index}" @click="selCheck(index,item)"></i></p>
          </li>
        </ul>
      </div>

      <div v-show="curSta" class="tuangou_buy_wrap">
        <div class="tuangou_buy_info">
          <p><span>总数量 : </span><span class="commonRed">{{zuType}}</span></p>
          <p><span>总金额 : </span><span class="commonRed">{{totalMoney}}</span></p>
          <p><img src="./meImgs/purches_btn.png" @click="tuangouBuyBtn"></p>
        </div>
      </div>

    </div>
</template>

<script>
  import { Dialog } from 'vant';

  export default {
    name: "tuan_gou",
    data(){
      return {
        tuangouProInfos: [],
        active: -1,
        curSta: false,
        zuType: '',
        proTotal: '',
        proid: '',
        proname: '',
        subSerScale: '',
        totalMoney: '',
        danPrice: '',
      }
    },
    created(){
      //页面初始化的时候获取认购的产品接口
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
        this.tuangouProInfos = res.data.data.list;
        this.curSta = false;
      }).catch(error => {
        ;
      })
    },
    methods: {
      selCheck(index,item){
        this.active = this.active==index? -1 : index;
        if (this.active==index){
          this.curSta = true;
        }else {
          this.curSta = false;
        }
        this.zuType = item.subBuyNum;
        this.totalMoney = item.goodsSubPrice * item.subBuyNum ;
        this.proTotal = item.goodsSubPrice;
        this.proid = item.id;
        this.proname = item.goodsName;
      },
      tuangouBuyBtn(){
        //要先判断是否登录了
        let token = localStorage.getItem('value');
        if (token){
          //调取服务费的接口
          this.$http({
            method: 'get',
            url: this.url + '/api/sub/getScales',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            }
          }).then(res => {
            if (res.data.error_code ===0 ){
              // this.subSerScale = (res.data.data.scalesBean.subServiceScale*(this.zuType*this.proTotal)).toFixed(2);
              this.subSerScale = (res.data.data.scalesBean.subServiceScale*(this.totalMoney)).toFixed(2);
              Dialog.confirm({
                title: '认购',
              }).then(() => {
                //提交认购的接口需要的参数信息，调取认购的接口
                this.$http({
                  method: 'post',
                  url: this.url + '/api/sub/subMake',
                  header: {
                    'Content-type': 'application/x-www-form-urlencoded'
                  },
                  params: {
                    goodsId: this.proid,
                    goodsName: this.proname,
                    holdNum: this.zuType,
                    money: this.zuType * this.proTotal,
                    serviceFee: this.subSerScale,
                    buyPoint: this.proTotal,
                  }
                }).then(res => {
                  if (res.data.error_code === 0){
                    this.$Toast('认购产品成功');
                    //跳转到持仓页面
                    this.$router.push({
                      path: '/orderManagement',
                    })
                  }else {
                    /*this.$Toast('认购产品失败');*/
              this.$Toast(res.data.data.error);
                  }
                })
              }).catch(() => {
                // on cancel
              });
            }
          })
        }else {
          this.$router.push({
            path: '/login'
          })
        }

      }
    },
    components:{

    }
  }
</script>

<style scoped>
  .tuangou_wrap {
    position: relative;
    background: #974f02;
    color: #fff;
    padding: .3rem;
    text-align: center;
    font-size: .5rem;
  }
  .tuangou_pro_wrap {
    margin-bottom: 3rem;
    color: #666666;
  }
  .tuangou_wrap img {
    position: absolute;
    top: .4rem;
    left: .3rem;
    width: .3rem;
  }
  .tuangou_wrap a {
    color: #ffffff !important;
  }
  .tuangou_content {
    margin-top: 2px;
    padding: .2rem .4rem;
    background: #ffffff;
    display: flex;
    justify-content: flex-start;
    align-items: center;
  }
  .tuangou_content > p {
    width: 30%;
  }
  .tuangou_content > div {
    width: 70%;
    text-align: left;
    padding-left: .3rem;
    line-height: 1.8;
  }
  .tuangou_content > div > p.tuangou_tit_name {
    font-weight: bold;
    font-size: .42rem;
    color: #333333;
  }
  .check_icon {
    width: 15% !important;
  }
  .check_icon i {
    display: inline-block;
    background: url("./meImgs/unchecked.png") center center no-repeat;
    background-size: contain;
    width: .7rem;
    height: .7rem;
  }
  .check_icon i.active {
    display: inline-block;
    background: url("./meImgs/checked.png") center center no-repeat;
    background-size: contain;
    width: .7rem;
    height: .7rem;
  }
  .tuangou_buy_wrap {
    position: fixed;
    bottom: 1.3rem;
    left: 0;
    width: 100%;
    background: #ffffff;
    border-top: 1px solid #f5f5f5;
    color: #666666;
  }
  .tuangou_buy_info {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 2rem;
  }
  .tuangou_buy_info p {
    width: 30%;
  }
  .tuangou_buy_info p img {
    width: 2rem;
  }
  .commonRed {
    color: #e21918;
  }
  .tuangou_pro_price span:first-child{
    font-size: .5rem;
  }

</style>
