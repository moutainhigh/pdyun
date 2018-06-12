<template>
  <div>
    <div class="moneyListData">
      <span>流水号</span>
      <span>金额</span>
      <span>手续费</span>
      <span>类型</span>
      <span>状态</span>
      <span>创建时间</span>
    </div>
    <ul class="allOrderContent">
      <li v-for="item0 of zijinLists">
        <div>
          <span>{{item0.serialNo}}</span>
          <span>{{item0.money}}</span>
          <span>{{item0.fee}}</span>
          <span>{{item0.type==0?'充值':item0.type==1?'提现':'显示错误'}}</span>
          <span>{{item0.status==0?'处理中':item0.status==1?'成功':item0.status==2?'失败':'显示错误'}}</span>
          <span>{{item0.createTime}}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
      name: "money-all-orders",
      data(){
        return {
          zijinLists: [],
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/my/moneyManager',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        }).then(res=> {
          if (res.data.error_code ===0){
            this.zijinLists = res.data.data.moneyRecord;
          }
        }).catch(error => {
          ;
        })
      },

    }
</script>

<style scoped>
  .moneyListData {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .4rem;
    background: #ffffff;
    border-bottom: 1px solid #ddd;
  }
  .moneyListData span:first-child{
    width: 20%;
    text-align: left;
  }
  .moneyListData span:last-child{
    width: 20%;
  }
  .allOrderContent li > div {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .4rem;
  }
  .allOrderContent li > div span {
    width: 14%;
  }
  .allOrderContent li > div span:first-child {
    width: 22%;
    word-wrap:break-word
  }
  .allOrderContent li > div span:last-child {
    width: 20%;
  }
</style>
