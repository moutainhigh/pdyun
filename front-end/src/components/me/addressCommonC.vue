<template>
    <div>
        <van-address-edit
          :address-info="addressInfo"
          :area-list="areaList"
          @save="onSave"
        />
    </div>
</template>

<script>
  import areaList from './proCity';
  import store from '@/vuex/index';
  import Vue from 'vue';
  import { AddressEdit,Toast } from 'vant';
  Vue.use( AddressEdit,Toast );
    export default {
      name: "address-common-c",
      props:['datalist'], //父组件传过来的信息
      data(){
        return {
          areaList,
          addressInfo:{  //默认值
            id: '', //地址列表中的
            name:'',  //姓名
            tel: '', //手机号
            province:'', //省份
            city:'', //城市
            county:'',  //地区
            address_detail:'' ,//详细地址
            value: '',
          }
        }
      },
      created(){ //页面加载进来赋值
        if (this.datalist){
          this.addressInfo.id=this.datalist.id;
          this.addressInfo.name=this.datalist.receiverName;
          this.addressInfo.tel=this.datalist.receiverMobile;
          this.addressInfo.address_detail=this.datalist.address;
        }
      },
      mounted(){
        if (this.datalist){
          let regionAll = this.datalist.region.split(',');
          $('.van-cell__value  span:first-child').html(regionAll[0]);
          $('.van-cell__value  span:last-child').html(regionAll[2]);
          $('.van-cell__value  span:nth-child(2)').html(regionAll[1]);
        }
        $('.van-button--default').css('display','none');
        $('.van-button--primary').css({'backgroundColor':'#974f02','border':'none'});
      },
      methods:{

        onSave(val) {
          //在这里判断一下是添加地址还是修改地址
          console.log(this.addressInfo.id);
          if(this.addressInfo.id){
            this.$http({
              method: 'post',
              url: this.url + '/api/my/updAddress',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'receiverName': val.name,
                'receiverMobile': val.tel,
                'region': `${val.province},${val.city},${val.county}`,
                'address': val.address_detail,
                'id': this.addressInfo.id,
              }
            }).then(reg=>{
              if(reg.data.error_code===0){
                Toast('提交成功');
                this.$router.push({path: '/addressC'});
              }
            }).catch(error => {
              ;
            })
          }else {
            this.$http({
              method: 'post',
              url: this.url + '/api/my/addMyAddress',
              header: {
                'Content-type': 'application/x-www-form-urlencoded'
              },
              params: {
                'receiverName': val.name,
                'receiverMobile': val.tel,
                'region': `${val.province},${val.city},${val.county}`,
                'address': val.address_detail,
                'userId': localStorage.getItem('userId'),
              }
            }).then(reg=>{
              if(reg.data.error_code===0){
                Toast('提交成功');
                this.$router.push({path: '/addressC'});
              }
            }).catch(error => {
                ;
            })
          }
        },
      },
    }
</script>

<style scoped>

</style>
