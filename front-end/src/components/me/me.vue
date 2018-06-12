<template>
    <div>
      <div class="me_top_wrap">
        <p class="me_user_info">
          <router-link to="/home">
            <img src="./meImgs/arrow_left.png">
          </router-link>
          <span>个人中心</span>
        </p>
        <div class="me_user_wrap">
          <div class="userImgW"><img src="./meImgs/account_head.png"></div>
          <div class="userInfoW">
            <p class="user_titleN">品道云商</p>
            <p class="fonspoint2" style="white-space:nowrap">手机号码:{{myInfo.mobile}}</p>
            <p class="fonspoint2" style="white-space:nowrap">
              <span>账户金额:</span><span> {{myInfo.money}} （元）</span>
            </p>
          </div>
          <div class="userShare">
            <router-link to="/accountSet">
              <span class="setInfos">设置</span>
            </router-link>
            <router-link to="/erweima">
              <img src="./meImgs/account_share.png" alt="">
              <p>分享</p>
            </router-link>
          </div>
        </div>
      </div>
      <!--<ul class="my_order_zr">-->
        <!--<router-link to="/orderManagement"><li><span>我的订单</span><span>{{myInfo.tradeNum}}</span></li></router-link>-->
        <!--<router-link to="/storeManagement"><li><span>我的转让</span><span>{{myInfo.transferNum}}</span></li></router-link>-->
      <!--</ul>-->
      <ul class="my_list_wrap">
        <router-link to="/addressC">
          <li><p><span class="my_addr_icon"></span><span>我的地址</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/orderManagement">
          <li><p><span class="order_icon"></span><span>订单管理</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/moneyManagement">
          <li><p><span class="my_money_icon"></span><span>我的资金</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/cardManagement">
          <li><p><span class="my_card_icon"></span><span>我的绑卡</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/points">
          <li><p><span class="my_tuangou_icon"></span><span>我的积分</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/recomManagement">
          <li><p><span class="my_recom_icon"></span><span>我的推荐</span></p><i class="arrowRight"></i></li>
        </router-link>
        <router-link to="/websiteStated">
          <li><p><span class="website_icon"></span><span>网站声明</span></p><i class="arrowRight"></i></li>
        </router-link>
      </ul>
      <div class="logoutW">
        <a href="javascript:;" @click="logoutDL">退出登录</a>
      </div>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  export default {
    name: "login",
    data() {
      return{
        myInfo:''
      }
    },
    created(){
      store.commit('footShow');  //显示底部导航
      this.$http({
        method: 'get',
        url: this.url + '/api/my/getMyInfo',
        header: {
          'Content-type': 'application/x-www-form-urlencoded'
        }
      }).then(res=>{  //请求成功
        if(res.data.error_code===0){  //返回成功
          if (res.data.data.myInfo.id){
            this.myInfo=res.data.data.myInfo;
          }else {
            this.$Toast('请退出后,重新登录');
          }
        }else{  //返回失败
          this.$router.push({
            path: '/login'
          });
          this.$Toast('清除缓存后,重新登录');
        }
      }).catch(error=>{ //请求失败
        alert(error+'222');
      })
    },
    methods: {
      logoutDL(){
        //把localStorage中的记录清除
        localStorage.removeItem('name');
        localStorage.removeItem('value');
        localStorage.removeItem('userId');
        localStorage.removeItem('userMobile');//把用户的手机号码存储到
        this.$router.push({
          path: '/home'
        })
      }
    }
  }
</script>

<style scoped>
  .fonspoint2 {
    font-size: .2rem;
  }
  .user_titleN {
    font-size: .43rem;
  }
  .me_top_wrap {
    background: url("./meImgs/jianbianBg.png") center center no-repeat;
    background-size: cover;
    color: #fff;
    padding: .3rem .2rem;
  }
  .me_user_info {
    position: relative;
    text-align: center;
    font-size: .5rem;
  }
  .me_user_info img {
    position: absolute;
    top: 0;
    left: 0;
    width: .3rem;
    vertical-align: middle;
  }
  .me_user_wrap {
    display: flex;
    justify-content: space-between;
    margin-top: .4rem;
  }
  .erweimaBg span{
    display: inline-block;
    width: 1.4rem;
    text-align: center;
    padding-top: .2rem;
    height: 1.2rem;
    background: #000;
  }
  .userImgW {
    width: 20%;
  }
  .userImgW img {
    width: 100%;
  }
  .userInfoW {
    width: 35%;
    text-align: left;
    padding-left: .3rem;
    line-height: 1.8;
  }
  .userInfoW img {
    width: .2rem;
  }
  .userShare {
    display: flex;
    flex-direction: column;
    width: 50%;
    text-align: right;
    font-size: .4rem;
  }
  .userShare img {
    width: .55rem;
    margin-right: .05rem;
  }
  .userShare .setInfos{
    display: inline-block;
    margin-bottom: .3rem;
  }
  .my_list_wrap {
    margin-top: .1rem;
    padding: 0 .2rem;
    background: #fff;
    line-height: 2;
    font-size: .43rem;
  }
  .my_list_wrap li {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #eee;
    padding: .2rem 0;
  }
  .arrowRight {
    width: .3rem;
    height: .3rem;
    border-top: 1px solid #ddd;
    border-right: 1px solid #ddd;
    transform: rotate(45deg);
    margin-top: .2rem;
  }
  .me_user_info a {
    color: #ffffff !important;
  }
  .my_order_zr li {
    display: flex;
    flex-direction: column;
  }
  .my_order_zr a {
    color: #ffffff !important;
  }
  .logoutW {
    background: url("./meImgs/exitLogin.png") center center no-repeat;
    width: 90%;
    padding: .1rem;
    margin: auto;
    margin-top: 1rem;
    border-radius: .15rem;
  }
  .logoutW a {
    color: #ffffff;
    display: inline-block;
    width: 100%;
    border-radius: .2rem;
    padding: .2rem;
  }
  .userShare a {
    color: #fff;
  }
  .my_addr_icon {
    display: inline-block;
    background: url("./meImgs/locationIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
  }
  .order_icon {
    display: inline-block;
    background: url("./meImgs/orderIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
    margin-top: -.1rem;
  }
  .my_money_icon {
    display: inline-block;
    background: url("./meImgs/moneyIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
    margin-top: -.1rem;
  }
  .my_card_icon {
    display: inline-block;
    background: url("./meImgs/bindcardIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
    margin-top: -.1rem;
  }
  .my_tuangou_icon {
    display: inline-block;
    background: url("./meImgs/tuangouIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
    margin-top: -.1rem;
  }
  .my_recom_icon {
    display: inline-block;
    background: url("./meImgs/myrecomIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
  }
  .website_icon {
    display: inline-block;
    background: url("./meImgs/websiteIcon.png") center center no-repeat;
    background-size: contain;
    width: .5rem;
    height: 1rem;
    vertical-align: middle;
    margin-right: .4rem;
    margin-top: -.1rem;
  }
</style>
