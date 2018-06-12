import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    footIsShow:true, //判断底部导航显示状态
    token: null, //判断用户是否登录
    userId:null,  //用户id
    val : null, //用户value
  },
  mutations: {
    footHide (state) {//底部导航不显示方法
      state.footIsShow=false
    },
    footShow(state){//底部导航显示方法
      state.footIsShow=true
    },
    setUserId(state,data){
      state.userId = data;
    },
    setUserVal(state,data){
      state.val = data;
    },
  }
});





