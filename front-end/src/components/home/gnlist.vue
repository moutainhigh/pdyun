<template>
  <div id="molists">
    <ul>
      <li>
        <router-link to="/myInvitation">
          <img src="./homeImgs/invite_award.png" alt="">
          <span>邀请有奖</span>
        </router-link>
      </li>
      <li>
        <a href="javascript:;" @click="this.loadDev">
          <img src="./homeImgs/daily_check.png" alt="">
          <span>每日签到</span>
        </a>
      </li>
      <li>
        <router-link to="/gongGCenter">
          <img src="./homeImgs/announcements.png" alt="">
          <span>通知公告</span>
        </router-link>
      </li>
      <li>
        <router-link to="/hotZiXun">
          <img src="./homeImgs/pop_consultation.png" alt="">
          <span>热门资讯</span>
        </router-link>
      </li>
      <li>
        <router-link to="/tuanGou">
          <img src="./homeImgs/bulk.png" alt="">
          <span>团购</span>
        </router-link>
      </li>
      <li>
        <router-link to="/mall">
          <img src="./homeImgs/business_street.png" alt="">
          <span>买卖街</span>
        </router-link>
      </li>
      <li>
        <a href="javascript:;" @click="this.loadDev">
          <img src="./homeImgs/make_purchase.png" alt="">
          <span>拍派购</span>
        </a>
      </li>
      <li>
        <router-link to="/newGreenLand">
          <img src="./homeImgs/newbie.png" alt="">
          <span>新手上路</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
    export default {
        name: "gnlist"
    }
</script>

<style scoped>
  ul {
    font-size: .3rem;
  }
  #molists {
    width: 100%;
    background: #fff;
    padding-bottom: 20px;
  }
  #molists ul {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
  }
  #molists ul li {
    width: 25%;
    margin-top: .2rem;

  }
  #molists ul li a {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  #molists ul li img {
    width: 1.6rem;
    border-radius: 50%;
    margin-bottom: .15rem;
  }
</style>
