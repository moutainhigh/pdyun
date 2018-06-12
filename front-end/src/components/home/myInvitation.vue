<template>
    <div class="invitation_wrapC">
      <p class="myInvitationC">
        <router-link to="/home">
          <img src="./homeImgs/arrow_left.png" alt="">
        </router-link>
        <span>我的邀请</span>
      </p>
      <div>
        <p><img src="./homeImgs/invitation_tp.png" alt=""></p>
        <p ><router-link to="/recomManagement"><span class="yaoq_btnC">我的邀请</span></router-link></p>
      </div>
      <div class="invitation_content">
        <p>奖励一：好友买卖街服务费返现11%. <br>好友在“买卖街”页面买入产品，您将获得买入产品服务费的11%返现作为邀请奖励。例如：<br>
          买入产品1万元  *  0.3%  * 11% = 3.3元
        </p>
        <p>奖励二：好友所邀请好友买卖街服务费返现11%. <br>好友所邀请的好友在“买卖街”页面买入产品，您将获得买入产品服务费的11%返现作为邀请奖励。例如：<br>
          买入产品1万元  *  0.3%  * 11% = 3.3元
        </p>
      </div>
      <div class="copyQ"><p>注：产品所有规则解释权归“品道云商”所有</p></div>
      <div class="invitation_btns">
        <router-link to="/erweima"><span>面对面邀请</span></router-link>
        <span @click="sendGifts" class="copyBtn" :data-clipboard-text = "giftText">给好友送福利</span>
      </div>
    </div>
</template>

<script>
    import Clipboard from 'clipboard';
    import { Dialog } from 'vant';
    export default {
      name: "my_invitation",
      data(){
        return {
          invitInfoId: '',
          invitorPhone: '',
          giftText: '',
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/user/getShareImg',
          header: {
            'Content-type': 'application/x-www-form-urlencoded'
          }
        }).then(res => {
          if (res.data.error_code === 0){
            //获取到返回的参数
            this.invitInfoId = res.data.data.inviteId;
            this.invitorPhone = res.data.data.refereePhone;
            this.giftText = "http://pay.pindao360.com/#/register?userId="+this.invitInfoId+"&refereePhone="+this.invitorPhone
          }
        })
      },
      methods: {
        sendGifts(){
          let clipboard = new Clipboard('.copyBtn');

          //成功回调
          clipboard.on('success', function(e) {
            Dialog.alert({
              title: '链接',
              message: e.text,
              showConfirmButton: false,
              closeOnClickOverlay: true
            }).then(() => {
              // on close
            });
            e.clearSelection();
          });
          //失败回调
          clipboard.on('error', (e) => {
            this.$Toast('请退出重新登录');
            console.error('Action:', e.action);
            console.error('Trigger:', e.trigger);
          });
        },
      }
    }
</script>

<style scoped>
  .invitation_wrapC {
    background: #ffffff;
  }
  .myInvitationC {
    background: #974f02;
    color: #fff;
    padding: .4rem;
    text-align: center;
    font-size: .45rem;
    position: relative;
  }
  .myInvitationC img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .65rem;
    padding-right: .3rem;
    margin-right: .2rem;
  }
  .yaoq_btnC {
    margin-top: .8rem;
    display: inline-block;
    width: 70%;
    background: url("./homeImgs/invitation_btn_bg.png") center center no-repeat;
    background-size: contain;
    color: #fff;
    font-size: .45rem;
    padding: .35rem;
  }
  .invitation_content {
    margin-top: .8rem;
  }
  .invitation_content p{
    width: 90%;
    margin: .3rem auto;
    text-align: left;
    background: #f2f1f1;
    padding: .3rem;
    line-height: 1.4;
    color: #666;
    font-weight: bold;
  }
  .invitation_btns {
    margin-top: 1rem;
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
  .invitation_btns a{
    display: inline-block;
    background: #bc6102;
    padding: .3rem .1rem;
    color: #fff;
    border-radius: .2rem;
    margin-bottom: 1rem;
    width: 36%;
    font-size: .45rem;
  }
  .invitation_btns > span {
    display: inline-block;
    background: #bc6102;
    padding: .3rem .1rem;
    color: #fff;
    border-radius: .2rem;
    margin-bottom: 1rem;
    width: 36%;
    font-size: .45rem;
  }
  .copyQ {
    text-align: left;
    padding: .1rem .5rem;
    color: #974f02;
    font-weight: bold;
  }
</style>
