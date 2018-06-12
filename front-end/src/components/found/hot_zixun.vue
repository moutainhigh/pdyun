<template>
    <div>
      <header class="hot_zixun_header">
        <a href="javascript:;" @click="this.backTop">
          <img src="./foundImgs/arrow_left.png" alt="">
        </a>
        <span class="hot_zixun_name">热门资讯</span>
      </header>
      <div>
        <ul class="host_zixun_list">
          <li v-for="zixunItem of zixunListInfos">
            <router-link tag="div" :to="{path:'zixun',query:{data: JSON.stringify(zixunItem)}}">
              <span>{{zixunItem.title}}</span><span>{{zixunItem.createTime}}</span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
</template>

<script>
    export default {
      name: "hot_zixun",
      data(){
        return {
          zixunListInfos: [],
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/index/getNewsFlash',
          params: {
            type: '3',
          }
        }).then(res => {
          if (res.data.error_code === 0){
            if (res.data.data.list != ''){
              this.zixunListInfos = res.data.data.list;
            }else {
              this.$Toast('还没有热门资讯数据');
            }
          }
        });
      },
    }
</script>

<style scoped>
  .hot_zixun_header {
    background: #974f02;
    color: #fff;
    text-align: center;
    padding: .4rem ;
    font-size: .45rem;
    letter-spacing: 1px;
    position: relative;
  }
  .hot_zixun_header img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .3rem;
  }
  .host_zixun_list li div{
    display: flex;
    background: #fff;
    border-bottom: 1px solid #ddd;
    padding: .2rem .4rem;
  }
  .host_zixun_list span:first-child {
    width: 59%;
    text-align: left;
  }
  .host_zixun_list span:last-child {
    width: 39%;
    text-align: right;
  }
</style>
