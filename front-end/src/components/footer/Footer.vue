<template>
  <div id="footer">
    <ul class="footerC">
      <li>
        <transition name="fade">
        <router-link to="/home">
          <i class="home_icon"></i>
          <span>首页</span>
        </router-link>
        </transition>
      </li>
      <li>
        <router-link to="/mall">
        <!--<a href="javascript:;" @click="this.loadDev">-->
          <i class="subscribe_icon"></i>
          <span>买卖街</span>
        <!--</a>-->
        </router-link>
      </li>
      <li>
        <!--<router-link to="/mall">-->
        <a href="javascript:;" @click="this.loadDev">
          <i class="mall_icon"></i>
          <span>拍派购</span>
        </a>
        <!--</router-link>-->
      </li>
      <li class="active">
        <router-link to="/shoucang">
          <i class="find_icon"></i>
          <span>收藏</span>
        </router-link>
      </li>
      <li>
        <router-link to="/me">
          <i class="me_icon"></i>
          <span>个人中心</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
  export default {
    name: 'Footer',
  }
</script>

<style scoped>
  #footer {
    background: #fff;
    border-top: 1px solid #ddd;
  }
  ul {
    list-style: none;
    width: 100%;
    padding: .1rem;
    padding-top: .2rem;
  }
  a {
    text-decoration: none;
    color: #333;
    text-align: center;
  }

  #footer {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    font-size: .35rem;
  }
  #footer ul li i {
    width: .6rem;
    height: .55rem;
    margin-bottom: .1rem;
    margin-left: .05rem;
  }
  #footer ul {
    display: flex;
    justify-content: space-around;
  }
  #footer ul li {
    text-align: center;
  }
  #footer ul li a{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .home_icon {
    background: url("./footerImgs/home.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active .home_icon {
    background: url("./footerImgs/home_over.png") center center no-repeat;
    background-size: contain;
  }
  .subscribe_icon {
    background: url("./footerImgs/buy_sell_street.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active .subscribe_icon {
    background: url("./footerImgs/buy_sell_street_over.png") center center no-repeat;
    background-size: contain;
  }

  .mall_icon {
    background: url("./footerImgs/pai_pai_buy.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active .mall_icon{
    background: url("./footerImgs/pai_pai_buy_over.png") center center no-repeat;
    background-size: contain;
  }
  .find_icon {
    background: url("./footerImgs/shoucang.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active .find_icon {
    background: url("./footerImgs/shoucang_over.png") center center no-repeat;
    background-size: contain;
  }
  .me_icon {
    background: url("./footerImgs/user_info_center.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active .me_icon {
    background: url("./footerImgs/user_info_center_over.png") center center no-repeat;
    background-size: contain;
  }
  .router-link-active span{
    color: #553006;
  }
</style>
