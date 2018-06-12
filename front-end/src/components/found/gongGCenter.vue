<template>
  <div>
    <header class="pro_gg_header">
      <a href="javascript:;" @click="this.backTop">
        <img src="./foundImgs/arrow_left.png" alt="">
      </a>
      <span class="pro_center_name">通知公告</span>
    </header>
    <div class="pro_gg_detail">
      <foundCommon></foundCommon>
    </div>
  </div>
</template>

<script>
  import foundCommon from './foundCommon';
  export default {
    name: "proGongCenter",
    components: {foundCommon}
  }
</script>

<style scoped>
  .pro_gg_header {
    background: #974f02;
    color: #fff;
    text-align: center;
    padding: .4rem ;
    font-size: .45rem;
    letter-spacing: 1px;
    position: relative;
  }
  .pro_gg_header img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .3rem;
  }
  .pro_gg_detail {
    padding: .3rem;
    background: #fff;
  }
</style>
