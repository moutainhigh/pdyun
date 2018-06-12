<template>
    <div>
      <ul class="proTitle" >
        <li>商品名</li>
        <li>数量</li>
        <li>总额</li>
        <li>服务费</li>
        <li>手续费</li>
        <li>单价</li>
      </ul>
      <ul class="proDetaInfos" >
        <li v-for="(item,index) of proInfos" >
          <div class="curDetaiContent" @click="changeState(index)">
            <span>{{item.goodsName}}</span>
            <span>{{item.holdNum}}</span>
            <span>{{item.money}}</span>
            <span>{{item.serviceFee}}</span>
            <span>{{item.feeBuy}}</span>
            <span>{{item.buyPoint}}</span>
          </div>
          <div class="curHideInfos" v-show="initState == index">
            <ul>
              <li class="tabTitleC">
                <span :class="{active: curatve == 'guadan'}" @click="curActive('guadan')">挂单</span><span :class="{active: curatve == 'tihuo'}" @click="curActive('tihuo')">提货</span>
              </li>
            </ul>
            <ul class="gd_list_data_content" v-show="curatve == 'guadan'">
              <li class="">
                <p><span>流水号:</span><span>{{item.serialNo}}</span></p>
                <p><span>买入时间:</span><span>{{item.buyTime}}</span></p>
              </li>
              <li class="gdNumContent">
                <span>挂单数量:</span>
                <input type="number" v-model="hangNumber">
              </li>
              <li class="gdNumPrice">
                <span>挂单价格:</span>
                <input type="text" v-model="hangPrice"><span>( {{ minPrice }}</span> - <span>{{ maxPrice }} )</span>
              </li>
              <li>
                <p><button class="gdBtn" @click="hangOrder(item.id)">挂单</button></p>
              </li>
            </ul>
            <ul class="tihuo_list_data_content" v-show="curatve == 'tihuo'">
              <li class="">
                <p><span>流水号:</span><span>{{item.serialNo}}</span></p>
                <p><span>买入时间:</span><span>{{item.buyTime}}</span></p>
              </li>
              <li class="thNumContent">
                <span>提货数量:</span>
                <input type="number" v-model="tihuoNumber">
              </li>
              <li class="thAddr">
                <span>提货地址:</span>
                <select name="" id="" v-model="tihuoAddr">
                  <option disabled value="">请选择提货地址</option>
                  <option :value="addrItem.id" v-for="addrItem of thAddrList">{{addrItem.receiverName}}</option>
                </select>
              </li>
              <li>
                <p><button class="thBtn" @click="tihuoOrder(item.serialNo)">提货</button></p>
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
</template>

<script>
    export default {
      name: "my-buy-somthing",
      created(){
        /*初始化获取持仓数据列表*/
        this.$http({
          method: 'get',
          url: this.url + '/api/my/getMyTrade',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          },
          params: {
            status:1,
          }
        }).then(res => {
          if (res.data.error_code===0) {
            if (res.data.data.list !=''){
              this.proInfos = res.data.data.list;

            }else {
              this.$Toast('获取数据列表失败');
            }
          }
        });
        /*初始化获取挂单价格上下浮动比例*/
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/getUpAndDownPercent',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          },
        }).then(res => {
          if (res.data.error_code===0) {
            if (res.data.data.upAndDownPercent){
              this.upAndDownPercent = res.data.data.upAndDownPercent;
            }else {
              this.$Toast('获取挂单价格上下浮动比例失败');
            }
          }
        });
      },
      data(){
        return {
          proInfos: [],
          initState: -1,
          hangNumber: '',
          hangPrice: '',
          tihuoAddr: '', /*提货地址选择id*/
          tihuo: false,
          guadan: true,
          curatve: '', /*初始化的active状态*/
          basePrice: '',/*商品基准价*/
          minPrice: '',
          maxPrice: '',
          tihuoNumber: '',/*提货数量*/
          thAddrList: [],/*提货地址列表数据*/
          upAndDownPercent: '',/*上下浮动比例设置*/
        }
      },
      methods: {
        curActive(curJH){
          this.curatve = curJH;
          if (this.curatve == 'tihuo'){
            this.selAddr();
          }
        },
        changeState(index){
          this.curatve = 'guadan';
          this.hangNumber = '';
          this.hangPrice = '';
          this.initState = this.initState == index ? -1 : index;

          /*通过商品id 获取商品基准价*/
          this.$http({
            method:'get',
            url: this.url + '/api/sub/getSubGoodsById',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
            params:{
              id: this.proInfos[index].goodsId,
            },
          }).then(res => {
            if (res.data.error_code === 0){
              if (res.data.data != ''){
                this.basePrice = res.data.data.basePrice;
                this.minPrice = (this.basePrice - this.basePrice*(this.upAndDownPercent)).toFixed(2);
                this.maxPrice = (this.basePrice + this.basePrice*(this.upAndDownPercent)).toFixed(2);
              }else {
                this.$Toast('获取数据失败')
              }
            }
          });
        },
        hangOrder(proId){
          console.log(this.hangNumber);
          console.log(this.hangPrice >= this.minPrice + '-------' +this.minPrice);
          console.log(this.hangPrice <= this.maxPrice + '-------' +this.maxPrice);
          if (!this.hangNumber && (this.hangPrice > this.maxPrice || this.hangPrice < this.minPrice)){
              this.$Toast('挂单数量不能为空或者价格范围不对');
          }else {
            this.$http({
              method:'post',
              url: this.url + '/api/trade/entryOrders',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params:{
                id: proId,
                holdNum: this.hangNumber,
                buyPoint: this.hangPrice,
              }
            }).then(res => {
              if (res.data.error_code ===0){
                this.$Toast('挂单成功');
                this.initState = -1;
                this.hangNumber = '';
                /*重新刷新持仓列表*/
                this.$http({
                  method: 'get',
                  url: this.url + '/api/my/getMyTrade',
                  header: {
                    'Content-type': 'application/x-www-form-urlencoded'
                  },
                  params: {
                    'status':1,
                  }
                }).then(res => {
                  if (res.data.error_code===0) {
                    this.proInfos = res.data.data.list;
                  }
                })
              }else {
                this.$Toast(res.data.data.error);
              }
            })
          }
        },
        selAddr(){
          /*获取该用户的地址列表*/
          this.$http({
            method: 'get',
            url: this.url + '/api/my/getMyAddress',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
          }).then(res => {
            if (res.data.error_code===0) {
              if (res.data.data.addressList !=''){
                this.thAddrList = res.data.data.addressList;
              }else {
                this.$Toast('地址为空，请先到个人中心去完善地址信息');
              }
            }
          });
        },
        tihuoOrder(serialNo){
          /*点击提货的时候，要判断是不是有地址，没有地址的话，要去添加地址*/

          if (this.tihuoNumber != '' && this.tihuoAddr !=''){
            this.$http({
              method: 'post',
              url: this.url + '/api/sub/takeGoodsAway',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                serialNo:serialNo,
                goodsNum: this.tihuoNumber,
                addressId: this.tihuoAddr,
              }
            }).then(res => {
              if (res.data.error_code===0) {
                this.$Toast('提货成功');
                this.initState = -1;

                /*重新刷新持仓列表*/
                this.$http({
                  method: 'get',
                  url: this.url + '/api/my/getMyTrade',
                  header: {
                    'Content-type': 'application/x-www-form-urlencoded'
                  },
                  params: {
                    'status':1,
                  }
                }).then(res => {
                  if (res.data.error_code===0) {
                    this.proInfos = res.data.data.list;
                  }
                })
              }
            })
          }else {
            this.$Toast('提货数量不能为空或者提货地址为空')
          }
        }
      }
    }
</script>

<style scoped>
  .proTitle {
    padding: .4rem ;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #f5f2f2;
  }
  .proTitle li {
    width: 16%;
  }
  .proDetaInfos {
    background: #ffffff;
  }
  .proDetaInfos > li > div:first-child{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: .2rem .4rem;
  }
  .proDetaInfos li div span {
    width: 16%;
  }
  .proDetaInfos li:first-child {
    padding-top: 0;
  }
  .curHideInfos {
    padding: 0 .3rem;
    background: #fff;
   }
  .curHideInfos li p {
    line-height: 2;
  }
  .curHideInfos li input {
    width: 25% !important;
    background: #eee;
    padding: 0 .2rem;
    vertical-align: middle;
    border-radius: .1rem;
  }
  .curHideInfos li.gdNumContent,.curHideInfos li.gdNumPrice,.curHideInfos li.thNumContent,.curHideInfos li.thNumPrice {
    line-height: 2;
  }
  .curHideInfos li:last-child p {
    display: flex;
    justify-content: flex-end;
  }
  .curHideInfos li {
    text-align: left;
    margin-bottom: .1rem;
  }
  .gdBtn,.thBtn {
    background: url("./meImgs/gdbg.png") center center no-repeat;
    background-size: contain;
    padding: .1rem .5rem;
    border-radius: .2rem;
    border: none;
    color: #fff;
  }

  .curDetaiContent span{
    color: #974f02;
  }
  .tabTitleC {
    border-bottom: 2px solid #553009;
  }
  .tabTitleC span {
    display: inline-block;
    width: 20%;
    text-align: center;
    padding: .2rem 0;
    border-radius: .1rem;
    border: 1px solid #ddd;
    border-bottom: none;
    margin-bottom: -1px;
  }
  .tabTitleC span.active{
    background: #553009;
    color: #fff;
  }
</style>
