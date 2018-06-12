<template>
    <div>
      <p class="newLandC">
        <router-link to="home">
          <img src="./homeImgs/arrow_left.png" alt="">
        </router-link>
        <span>新手上路</span>
      </p>
      <div>
        <img src="./homeImgs/newBee.jpg" alt="">
      </div>
    </div>
</template>

<script>
    export default {
        name: "greenhand"
    }
</script>

<style scoped>
  .newLandC {
    background: #974f02;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: .4rem ;
    color: #fff;
    text-align: center;
    font-size: .45rem;
    position: relative;
  }
  .newLandC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .3rem;
  }
</style>
