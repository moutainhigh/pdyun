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
      <ul class="czhkOrderContent">
        <li v-for="itemyf of yufuzijinLists">
          <div>
            <span>{{itemyf.serialNo}}</span>
            <span>{{itemyf.money}}</span>
            <span>{{itemyf.fee}}</span>
            <span>{{itemyf.type==0?'充值':itemyf.type==1?'提现':'显示错误'}}</span>
            <span>{{itemyf.status==0?'处理中':itemyf.status==1?'成功':itemyf.status==2?'失败':'显示错误'}}</span>
            <span>{{itemyf.createTime}}</span>
          </div>
        </li>
      </ul>
    </div>
</template>

<script>
    export default {
      name: "money-yu-fu-pro-money",
      data(){
        return {
          yufuzijinLists: [],
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
            type: 0,
          }
        }).then(res=> {
          if (res.data.error_code ===0){
            this.yufuzijinLists = res.data.data.moneyRecord;
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
  .czhkOrderContent li > div {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: .4rem;
  }
  .czhkOrderContent li > div span {
    width: 14%;
  }
  .czhkOrderContent li > div span:first-child {
    width: 22%;
    word-wrap:break-word
  }
  .czhkOrderContent li > div span:last-child {
    width: 20%;
  }
</style>
