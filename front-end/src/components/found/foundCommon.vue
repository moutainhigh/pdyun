<template>
    <div style="width: 100%;">
      <div class="pro_gongao_tabs">
        <div class="pro_gongao_bgc">
          <p :class="{bgactive:bgactive}" @click="tab(1)"><span>通知</span></p>
          <p :class="{bgactive:!bgactive}" @click="tab(2)"><span>公告</span></p>
        </div>
      </div>
      <ul class="pro_gongao_detail">
          <li v-for="zxItem of zixunInfos">
            <router-link tag="div" :to="{path:'zixun',query:{data: JSON.stringify(zxItem)}}">
              <span>{{zxItem.title}}</span>
              <span class="pro_gongao_time">{{zxItem.createTime}}</span>
            </router-link>
          </li>
      </ul>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Toast} from 'vant';
    Vue.use(Toast);
    export default {
      name: "found-common",
      data(){
        return {
          bgactive: -1,
          currentNum: 1,
          zixunInfos: []
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/index/getNewsFlash',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            type: this.currentNum
          }
        }).then(res => {
          if (res.data.error_code === 0){
            this.zixunInfos = res.data.data.list;
          }
        }).catch(error => {

        })
      },
      methods: {
        tab(curNum) {
          this.bgactive = !this.bgactive;
          this.$http({
            method: 'get',
            url: this.url + '/api/index/getNewsFlash',
            header: {
              'Content-type': 'application/x-www-form-urlencoded',
            },
            params: {
              type: curNum
            }
          }).then(res => {
            if (res.data.error_code === 0){
              this.zixunInfos = res.data.data.list;
            }
          }).catch(error => {

          })
        }
      }
    }
</script>

<style scoped>
  .pro_gongao_bgc {
    height: .9rem;
  }
  .pro_gongao_tabs {
    width: 60%;
    margin: auto;
    margin-bottom: .4rem;
  }
  .pro_gongao_tabs p {
    text-align: center;
    width: 50%;
  }
  .pro_gongao_tabs > div {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    background: #ddd;
    border-radius: .5rem;
    text-align: left;
  }

  .pro_gongao_tabs p.bgactive {
    padding: .2rem;
    background: #fff;
    border: 1px solid #eee;
    border-radius: .5rem;
  }
  .pro_gongao_tabs p.bgactive span {
    display: inline-block;
    width: 100%;
  }
  .pro_gongao_tabs p.bgactive span {
    color: #974f02;
  }
  .pro_gongao_detail li {
    width: 100%;
    border-bottom: 1px solid #eee;
    padding: .2rem 0;
    text-align: left;
  }
  .pro_gongao_detail span:first-child {
    display: inline-block;
    width: 59%;
  }
  .pro_gongao_time {
    display: inline-block;
    width: 39%;
    text-align: right;
  }
</style>
