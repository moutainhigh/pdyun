<template>
    <div>
      <div class="modifyAddrWrap">
        <p class="modify_back_wrap"><router-link to="/addressC"><img src="./meImgs/arrow_left.png" alt=""></router-link> <span>修改地址</span></p>
        <p class="modify_reset_wrap"><span class="reset_default" @click="setSiteDefault()">设为默认地址</span> <span @click="delSite()">删除 <img class="delSite" src="./meImgs/address_del.png" alt=""></span></p>
      </div>
      <addressCommonC :datalist="datalist"></addressCommonC>
    </div>
</template>

<script>
  import addressCommonC from '@/components/me/addressCommonC';
  export default {
      name: "modify-address",
    data(){
        return{
          datalist:''
        }
    },
    components: {addressCommonC},
    created(){
        // 接收addressC组件中传递过来的数据
        // 取到路由带过来的参数
        this.datalist=JSON.parse(this.$route.query.data);
        console.log(this.$route.query.data);
    },
    methods:{
      setSiteDefault(){
        this.$http({
          method: 'POST',
          url: this.url + '/api/my/setDefaultAddress',
          params: {
            'id': this.datalist.id
          }
        }).then(res=>{
          if (res.data.error_code===0){  //返回成功
            this.$Toast('地址设置默认成功');
            this.$router.push({
              path: '/addressC',
            })
          }else{
            this.$Toast(res.data.error_code);
          }
        })
      },
      delSite(){
        this.$http({
          method: 'DELETE',
          url: this.url + '/api/my/deleteMyAddress',
          params: {
            'id': this.datalist.id
          }
        }).then(res=>{
          if (res.data.error_code===0){  //返回成功
            this.$Toast('地址删除成功');
            this.$router.push({
              path: '/addressC',
            })
          }else{
            this.$Toast(res.data.error_code)
          }
        })
      }
    }
  }
</script>

<style scoped>
  .modifyAddrWrap {
    background: #974f02;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: .4rem;
    font-size: .45rem;
  }
  .delSite{
    vertical-align: top;
  }
  .modify_back_wrap {
    color: #fff;
  }
  .modifyAddrWrap img {
    width: .4rem;
  }
  .modify_back_wrap img {
    width: .58rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .modify_reset_wrap {
    color: #ffffff;
  }
  .modify_reset_wrap img {
    margin-left: .2rem;
  }
  .reset_default {
    display: inline-block;
    border-right: 1px solid #fff;
    padding-right: .5rem;
    margin-right: .2rem;
  }
</style>
