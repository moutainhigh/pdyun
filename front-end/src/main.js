
import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import vuex from './vuex';
import token from '@/store/http';
import Vant from 'vant';
import 'vant/lib/vant-css/index.css';
Vue.use(Vant);
import {Toast} from 'vant';
Vue.use(Toast);
Vue.prototype.$http = axios;  //放到vue的原型上面去

Vue.prototype.url ='/front';
// Vue.prototype.url ='http://pay.pindao360.com/front';
// Vue.prototype.url ='http://localhost:8888/front';
Vue.config.productionTip = false;

Vue.prototype.$Toast =Toast;

/*在main.js里进行全局注册一个方法*/
Vue.prototype.loadDev = function (){
  Toast('正在开发中...');
};
Vue.prototype.backTop = function () {
  router.go(-1);
};

Vue.prototype.timers = [];

/* eslint-disable no-new */
var vm=new Vue({
  el: '#app',
  router,//将router注册一下
  vuex,//将vuex注册一下
  token,//将token注册一下
  components: { App },
  template: '<App/>'
});


