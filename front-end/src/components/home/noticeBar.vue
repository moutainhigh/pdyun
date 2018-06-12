<template>
    <div class="notice_wrap_c">
      <div class="notice_content">
        <span>通知公告:</span>
        <router-link to="/gongGCenter" style="display:inline-block;width: 80%">
          <van-notice-bar background="none" color="#333" :text="zixunContent"/>
        </router-link>
      </div>
    </div>

</template>

<script>
  import Vue from 'vue';
  import { NoticeBar } from 'vant';

  Vue.use(NoticeBar);

  export default {
    name: "notice-bar",
    data(){
      return {
        zixunContent:''
      }
    },
    created(){
      /*获取公告信息*/
      this.$http({
        method: 'get',
        url: this.url + '/api/index/getNewsFlash',
        header: {
          'Content-type': 'application/x-www-form-urlencoded',
        },
        parmas: {
          type: '2'
        }
      }).then(res => {
        if (res.data.error_code === 0){
          for (var i = 0;i < res.data.data.list.length;i++ ){
            this.zixunContent += res.data.data.list[i].title + '.';
          }
        }
      }).catch(error => {

      })
    },
    mounted(){
      $('.van-notice-bar').css({'fontSize':'.4rem','fontWeight':'bold'});
    }
  }
</script>

<style scoped>
  .notice_wrap_c {
    border-bottom: 1px solid #ddd;
  }
  .notice_content {
    display: flex;
    justify-content: flex-start;
    padding: .1rem .4rem;
    padding-bottom: 0;
  }
  .notice_content span{
    padding-top: .27rem;
    text-align: left;
    color: #553009;
    font-weight: bold;
  }
</style>
