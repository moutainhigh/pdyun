<template>
    <div class="add_address">
      <p class="add_addr_head"><router-link to="/addressC"><img src="./meImgs/arrow_left.png" alt=""><span>添加地址</span></router-link></p>
      <addressCommonC></addressCommonC>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  import addressCommonC from '@/components/me/addressCommonC';
  export default {
    name: "add_addr",
    created(){
      store.commit('footShow');
    },
    components:{addressCommonC}
  }
</script>

<style scoped>
  .add_addr_head {
    background: #974f02;
    text-align: center;
    padding: .4rem;
    font-size: .45rem;
    position: relative;
  }
  .add_addr_head a {
    display: inline-block;
    color: #fff;
  }
  .add_addr_head img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .3rem;
  }

</style>
