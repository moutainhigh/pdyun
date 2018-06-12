<template>
  <div id="app">
    <router-view id="routerView"></router-view>
    <foot v-if="$store.state.footIsShow"></foot>
  </div>
</template>

<script>
  import foot from '@/components/footer/Footer'
  import '@/assets/common.css';
  import '@/assets/rem.js';
  import store from '@/vuex/index';

  export default {
    name: 'App',
    data(){
      return {

      }
    },
    store,
    components: {foot},

  }
</script>

<style>
#app {
  font-family: SimHei, 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #333;
  overflow: auto;
}
  #routerView{
    margin-bottom:60px;
  }
</style>
