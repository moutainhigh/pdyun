<template>
    <div>
      <ul class="gdProTitle">
        <li>名称</li>
        <li>挂单数量</li>
        <li>买入价</li>
        <li>挂单价</li>
        <li class="hangTimeTitle">挂单时间</li>
        <li>操作</li>
      </ul>
      <ul class="gdProDetaInfos">
        <li v-for="(item,index) of gdProInfos">
          <div class="curDetaiContent" >
            <div @click="changeState(index)">
              <span>{{item.goodsName}}</span>
              <span>{{item.holdNum}}</span>
              <span>{{item.buyPoint}}</span>
              <span>{{item.sellPoint}}</span>
              <span class="hangTimeC">{{item.hangTime}}</span>
            </div>
            <p><span class="chexiaoBtn" @click="revocation(item.serialNo)">撤销</span></p>
          </div>
          <ul class="curHideInfos" v-show="initState == index">
            <li>
              <p><span>流水号:</span><span>{{item.serialNo}}</span></p>
              <p><span>买入时间:</span><span>{{item.buyTime}}</span></p>
            </li>
          </ul>
        </li>
      </ul>
    </div>
</template>

<script>
    export default {
      name: "my-gua-dan-something",
      data(){
        return{
          gdProInfos:[],
          initState: -1,
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/my/getMyTrade',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          },
          params: {
            'status':2,
          }
        }).then(res => {
          if (res.data.error_code===0) {
            this.gdProInfos = res.data.data.list;
          }
        })
      },
      methods: {
        changeState(index){
          this.initState = this.initState == index ? -1 : index;
        },
        revocation(serialNum){
          this.$http({
            method: 'post',
            url: this.url + '/api/sub/cancelHangTrade',
            header: {
              'Content-type': 'application/x-www-form-urlencoded'
            },
            params: {
              serialNo:serialNum,
            }
          }).then(res => {
            if (res.data.error_code===0) {
              if (!res.data.data.error){
                this.$Toast('撤销成功');

                /*重新刷新挂单列表*/
                this.$http({
                  method: 'get',
                  url: this.url + '/api/my/getMyTrade',
                  header: {
                    'Content-type': 'application/x-www-form-urlencoded'
                  },
                  params: {
                    'status':2,
                  }
                }).then(res => {
                  if (res.data.error_code===0) {
                    this.gdProInfos = res.data.data.list;
                  }
                })
              }
            }else {
              this.$Toast(res.data.data.error);
            }
          })
        }
      }
    }
</script>

<style scoped>
  .gdProTitle {
    padding: .4rem 0 ;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: #f5f2f2;
  }
  .gdProTitle > li {
    width: 15%;
  }
  .gdProTitle > li.hangTimeTitle {
    width: 25%;
  }
  .gdProDetaInfos {
    background: #ffffff;
  }
  .gdProDetaInfos > li div{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: .2rem 0;
  }
  .gdProDetaInfos > li > div span {
    display: inline-block;
    width: 15%;
  }
  .gdProDetaInfos > li:first-child {
    padding-top: 0;
  }
  .curDetaiContent {
    display: flex;
    padding: 0 .2rem !important;
  }
  .curDetaiContent > div {
    width: 78%;
  }
  .curDetaiContent p {
    width: 16.5%;
  }
  .curDetaiContent p span.chexiaoBtn {
    display: inline-block;
    width: 70%;
    padding: .1rem;
    border: 1px solid #ddd;
    border-radius: .1rem;
  }
  .curHideInfos {
    padding: 0 .3rem;
    background: #dddddd;
  }
  .curHideInfos li {
    padding: .1rem;
    text-align: left;
  }
  .curHideInfos > li > p > span {

  }
  .curDetaiContent span{
    color: #974f02;
  }
  .hangTimeC {
    width: 26% !important;
  }
</style>
