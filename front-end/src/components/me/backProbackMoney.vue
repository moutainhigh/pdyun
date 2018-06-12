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
    <ul class="thtkOrderContent">
      <li v-for="itemthtk of thtkzijinLists">
        <div>
          <span>{{itemthtk.serialNo}}</span>
          <span>{{itemthtk.money}}</span>
          <span>{{itemthtk.fee}}</span>
          <span>{{itemthtk.type==0?'充值':itemthtk.type==1?'提现':'显示错误'}}</span>
          <span>{{itemthtk.status==0?'处理中':itemthtk.status==1?'成功':itemthtk.status==2?'失败':'显示错误'}}</span>
          <span>{{itemthtk.createTime}}</span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
      name: "back-proback-money",
      data(){
        return {
          thtkzijinLists: [],
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/my/moneyManager',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          },
          params: {
            type: 1,
          }
        }).then(res=> {
          if (res.data.error_code ===0){
            this.thtkzijinLists = res.data.data.moneyRecord;
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
  .thtkOrderContent li > div {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .4rem;
  }
  .thtkOrderContent li > div span {
    width: 14%;
  }
  .thtkOrderContent li > div span:first-child {
    width: 22%;
    word-wrap:break-word
  }
  .thtkOrderContent li > div span:last-child {
    width: 20%;
  }
</style>
