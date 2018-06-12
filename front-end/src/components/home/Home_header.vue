<template>
  <swiper :options="swiperOption" ref="mySwiper">
    <swiper-slide><img src="../../assets/lunbo/bg1.png" ></swiper-slide>
    <swiper-slide><img src="../../assets/lunbo/bg2.png" ></swiper-slide>
    <swiper-slide><img src="../../assets/lunbo/bg3.png" ></swiper-slide>
    <swiper-slide><img src="../../assets/lunbo/bg4.png" ></swiper-slide>
    <div class="swiper-pagination"  slot="pagination"></div>
  </swiper>
</template>
<script>
  import {swiper,swiperSlide} from 'vue-awesome-swiper';
  import 'swiper/dist/css/swiper.css'
  export default {
    data() {
      return {
        swiperOption: {
          notNextTick: true,
          autoplay: true,
          loop:true,
          pagination: {
            el: '.swiper-pagination',
            clickable :true,
          }
        }
      }
    },
    components: {
      swiper,
      swiperSlide
    },
  }
</script>
<style scoped>


</style>
