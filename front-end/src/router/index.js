import Vue from 'vue';
import Router from 'vue-router';
import store from '@/vuex/index'
import home from '@/components/home/Home';
import me from '@/components/me/me';
import mall from '@/components/mall/mall';
import found from '@/components/found/found';
import login from '@/components/login/login';
import forgetPwd from '@/components/login/forgetPwd/forgetPwd';
import register from '@/components/register/register';
import regAgreement from '@/components/register/regAgreement';
import accountSet from '@/components/me/accountSet';
import addressC from '@/components/me/addressC';
import addAddr from '@/components/me/addAddr';
import orderManagement from '@/components/me/orderManagement';
import moneyManagement from '@/components/me/moneyManagement';
import cardManagement from '@/components/me/cardManagement';
import recomManagement from '@/components/me/recomManagement';
import websiteStated from '@/components/me/websiteStated';
import modifyAddress from '@/components/me/modifyAddress';
import gongGCenter from '@/components/found/gongGCenter';
import zixun from '@/components/found/zixun';
import points from '@/components/found/points';
import productInfo from  '@/components/mall/productInfo';
import subscribe from '@/components/subscribe/subscribe';
import subProductInfo from '@/components/subscribe/subProductInfo';
import erweima from '@/components/me/erweima';
import modifyPwd from '@/components/me/modifyLoginPwd';
import moneyRecord from '@/components/me/moneyRecord';
import moneyOut from '@/components/me/moneyOut';
import myInvitation from '@/components/home/myInvitation';
import tuanGou from '@/components/me/tuan_gou';
import hotZiXun from '@/components/found/hot_zixun';
import shoucang from '@/components/shoucang/collect';
import newGreenLand from '@/components/home/greenhand.vue';

Vue.use(Router);
const routes= [
    {
      path: '/',
      component: home
    },
    {
      path: '/home',
      component: home
    },
    {
      path: '/mall',
      component: mall,
      meta:{requireAuth:true}
    },
    // {
    //   path: '/found',
    //   component: found,
    // },
    {
      path: '/me',
      component: me,
      meta:{requireAuth:true}
    },
    {
      path: '/login',
      component: login
    },
    {
      path: '/forgetPwd',
      component: forgetPwd
    },
    {
      path: '/register',
      component: register
    },
    {
      path: '/regAgreement',
      component: regAgreement
    },
    {
      path: '/accountSet',
      component: accountSet,
      meta: {requireAuth: true},
    },
    {
      path: '/addressC',
      component: addressC,
      meta: {requireAuth: true,}
    },
    {
      path: '/addAddr',
      component: addAddr,
      meta: {requireAuth: true,}
    },
    {
      path: '/orderManagement',
      component: orderManagement,
      meta: {requireAuth: true},
    },
    {
      path: '/moneyManagement',
      component: moneyManagement,
      meta: {requireAuth: true},
    },
    {
      path: '/cardManagement',
      component: cardManagement,
      meta: {requireAuth: true},
    },
    {
      path: '/recomManagement',
      component: recomManagement,
      meta: {requireAuth: true},
    },
    {
      path: '/websiteStated',
      component: websiteStated
    },
    {
      path: '/modifyAddress',
      component: modifyAddress,
      meta: {requireAuth: true},
    },
    {
      path: '/gongGCenter',
      component: gongGCenter
    },
    {
      path: '/zixun',
      component: zixun,
    },
    {
      path: '/points',
      component: points,
      meta: {requireAuth: true},
    },
    // {
    //   path: '/productInfo',
    //   component: productInfo,
    //   meta: {requireAuth: true},
    // },
    {
      path: '/mall/productInfo',
      component: productInfo,
      meta: {requireAuth: true},
    },
    // {
    //   path: '/subscribe',
    //   component: subscribe
    // },
    // {
    //   path: '/subProductInfo',
    //   component: subProductInfo
    // },
    {
      path: '/erweima',
      component: erweima,
      meta: {requireAuth: true},
    },
    {
      path: '/modifyPwd',
      component: modifyPwd,
      meta: {requireAuth: true},
    },
    {
      path: '/moneyRecord',
      component: moneyRecord,
      meta: {requireAuth: true},
    },
    {
      path: '/moneyOut',
      component: moneyOut,
      meta: {requireAuth: true},
    },
    {
      path: '/tuanGou',
      component: tuanGou
    },
    {
      path: '/myInvitation',
      component: myInvitation
    },
    {
      path: '/hotZiXun',
      component: hotZiXun
    },
    {
      path: '/shoucang',
      component: shoucang,
      meta: {requireAuth: true},
    },
    {
      path: '/newGreenLand',
      component: newGreenLand,
    }
  ];

const router=new Router({
  routes
});

router.beforeEach((to, from, next) => {//全局路由钩子
  var token=localStorage.getItem('value');
  if (to.meta.requireAuth) {  // 判断该路由是否需要登录权限
    if (token) {  // 通过vuex state获取当前的token是否存在
      next();
    }
    else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}  // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
    }
  }
  else {
    next();
  }
});

export default router;



