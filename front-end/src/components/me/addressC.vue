<template>
    <div>
      <div class="addr_wrap">
        <p class="addr_backC"><router-link to="/me"><img src="./meImgs/arrow_left.png" alt=""></router-link> <span>地址管理</span></p>
        <p class="addr_addC"><span>添加</span><router-link to="/addAddr"><img src="./meImgs/add.png" alt=""></router-link></p>
      </div>
      <ul class="address_list_detail">
          <li v-for="item in datalist">
            <router-link :to="{path: '/modifyAddress', query: {data: JSON.stringify(item)}}" class="flex"><!--将当前条内容传递到路由页面-->
              <div>
                <span class="address_user">{{item.receiverName}}</span><span>{{item.receiverMobile}}</span>
                <p class="address_detail">{{item.address}}</p>
              </div>
              <div>
                <span v-if="item.addrType==1" class="default">【默认地址】</span>
                <i class="arrowRight"></i>
              </div>
            </router-link>
          </li>
      </ul>
    </div>
</template>

<script>
  import store from '@/vuex/index';
  export default {
    name: "address-c",
    data(){
      return{
        datalist:''
      }
    },
    created(){
      store.commit('footShow');
      this.$http({
        method: 'GET',
        url: this.url + '/api/my/getMyAddress',
      }).then(res=>{
        if (res.data.error_code === 0){  //返回成功
          this.datalist=res.data.data.addressList;
        }else{
          this.$Toast(res.data.error_code);
        }
      })
    }
  }
</script>

<style scoped>
  .addr_wrap {
    background: #974f02;
    padding: .4rem;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: .45rem;
    color: #ffffff;
  }
  .addr_backC img {
    width: .6rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .addr_addC img {
    width: .3rem;
    margin-left: .2rem;
  }
  .address_list_detail {
    margin-top: .2rem;
    padding: 0 .2rem;
    background: #fff;
  }
  .address_list_detail li {
    border-bottom: 1px solid #eee;
    padding-top: .2rem;
  }
  .flex{
    display: flex;
    justify-content: space-between;
    align-items: center;
    text-align: left;
  }
  .arrowRight {
    width: .3rem;
    height: .3rem;
    border-top: 1px solid #ddd;
    border-right: 1px solid #ddd;
    transform: rotate(45deg);
    margin-top: .2rem;
    display: inline-block;
  }
  .address_user {
    display: inline-block;
    margin-right: 1rem;
  }
  .address_detail {
    margin: .2rem 0;
  }
  .addr_addC  a {
    color: #974f02;
  }
  .default{
    color:#974f02;
  }
</style>
