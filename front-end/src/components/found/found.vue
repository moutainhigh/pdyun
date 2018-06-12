<template>
  <div>
    <div class="found_wrap_bg"></div>
    <div class="found_content">
      <div class="all_jf_wrap">
        <div><span class="jf_num">{{integrals}}</span><span>总积分</span></div>
        <div><router-link to="/points"><img src="./foundImgs/find_score.png" alt=""><span>积分明细</span></router-link></div>
      </div>
      <div class="pro_gongao_wrap">
        <foundCommon></foundCommon>
        <router-link to="/proGongCenter">
          <button class="loadMore">更多</button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import store from '../../vuex';
  import Vue from 'vue';
  import {Toast} from 'vant';
  Vue.use(Toast);
  import foundCommon from './foundCommon'
    export default {
        name: "found",
        data(){
          return {
            integrals: '',
          }
        },
        created(){
          store.commit('footShow');
          this.$http({
            method: 'get',
            url: this.url + '/api/sub/getUserIntegral',
          }).then(res => {
            if (res.data.error_code === 0){
              this.integrals = res.data.data.integral;
            }
          }).catch(error => {
            ;
          })
        },
        components:{
          foundCommon
        }
    }
</script>

<style scoped>
  .found_wrap_bg {
    width: 100%;
    background: url("./foundImgs/find_banner.png") center center no-repeat;
    background-size: cover;
    height: 4rem;
  }
  .found_content {
    margin: 0 .15rem;
    font-size: .3rem;
    margin-top: -.95rem;
  }
  .found_content > div > div{
    width: 60%;
    text-align: center;
  }
  .found_content > div {
    background: #fff;
  }
  .all_jf_wrap {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: .15rem;
    border-radius: .2rem;
    padding: .3rem 0;
    letter-spacing: 1px;
  }
  .all_jf_wrap > div {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
  .all_jf_wrap > div > a {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }
  .all_jf_wrap img {
    width: .4rem;
    margin-bottom: .3rem;
  }
  .jf_num {
    font-size: .5rem;
    color: #2eae64;
    margin-bottom: .1rem;
  }

  .pro_gongao_wrap {
    padding: .3rem;
  }
  .loadMore {
    background: #178f4a;
    border-radius: .3rem;
    color: #fff;
    width: 3rem;
    padding: .1rem;
    margin-top: .4rem;
  }


</style>
