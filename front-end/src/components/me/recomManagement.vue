<template>
    <div>
      <p class="commandGLC"><router-link to="/me"><img src="./meImgs/arrow_left.png" alt=""></router-link><span>推荐管理</span></p>
      <ul class="comListTitle">
        <li>
          <span>姓名</span>
          <span>手机号</span>
          <!--<span>推广数量合计</span>-->
        </li>
      </ul>
      <ul class="recomContentInfo">
        <li v-for="recomItem of recommandList">
          <span>{{recomItem.chnName}}</span>
          <span>{{recomItem.mobile}}</span>
          <!--<span>{{recomItem.parent2Total}}</span>-->
        </li>
      </ul>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  export default {
    name: "recom-management",
    data(){
      return {
        recommandList: [],
      }
    },
    created(){
      store.commit('footShow');
      this.$http({
        method: 'post',
        url: this.url + '/api/my/corps',
        header: {
          'Content-type': 'application/x-www-form-urlencoded'
        }
      }).then(res=> {
        if (res.data.error_code ===0){
          if (res.data.data.corps != ''){
            this.recommandList = res.data.data.corps;
          }else {
            this.$Toast('还没有推荐');
          }
        }
      }).catch(error => {
        ;
      })
    }
  }
</script>

<style scoped>
  .commandGLC {
    background: #974f02;
    padding: .4rem;
    text-align: center;
    position: relative;
    font-size: .45rem;
    color: #fff;
  }
  .commandGLC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .comListTitle {
    background: #fff;
    padding: .3rem .6rem;
    border-bottom: 1px solid #ddd;
  }
  .comListTitle li {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .comListTitle li span{
    width: 20%;
    text-align: center;
  }
  .comListTitle li span:nth-child(2),.comListTitle li span:nth-child(3){
    width: 30%;
  }
  .recomContentInfo {
    padding: .3rem .6rem;
    background: #fff;
  }
  .recomContentInfo li {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: .1rem 0;
  }
  .recomContentInfo li span{
    width: 20%;
  }
  .recomContentInfo li span:first-child{
    width: 30%;
    text-align: left;
  }
  .recomContentInfo li span:last-child{
     width: 30%;
     text-align: center;
   }
</style>
