<template>
    <div>
      <p class="collect_title"><router-link to="/home"><img src="./collectImgs/arrow_left.png" alt=""></router-link><span>收藏列表</span></p>
      <div class="collect_list_content">
        <ul class="scListC">
          <li class="sc_list_wrap" v-for="scItem of shoucangList">
            <router-link :to="{path: 'mall/productInfo',query: { goodsId: scItem.id, imgPath: scItem.imgLoadPath, proCode: scItem.goodsCode }}">
              <p>
                <img :src="scItem.imgLoadPath" >
              </p>
              <div>
                <p class="mmj_pro_tit_name"><span>{{scItem.goodsName}}</span></p>
                <p><span>商品代码：</span><span>{{scItem.goodsCode}}</span></p>
                <p><span>最新价：</span><span>{{scItem.sellPointNew}}</span></p>
                <p><span>卖出价：</span><span>{{scItem.buyPointMin}}</span></p>
              </div>
            </router-link>
          </li>
        </ul>
      </div>
    </div>
</template>

<script>
    export default {
      name: "collect",
      data(){
        return {
          shoucangList: [],
        }
      },
      created(){
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/getSubGoods',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            status: '2',
            isStore: 1,
          }
        }).then(res => {
          if (res.data.error_code === 0){
            if (res.data.data.list != ''){
              this.shoucangList = res.data.data.list;
            }else {
              this.$Toast('暂时还没有收藏的列表数据');
            }
          }else {
            this.$Toast('获取收藏的列表数据失败');
          }
        })
      },
    }
</script>

<style scoped>
  .collect_title {
    background: #974f02;
    color: #fff;
    font-size: .45rem;
    padding: .4rem;
    text-align: center;
    position: relative;
  }
  .collect_title img {
    position: absolute;
    top: .4rem;
    left: .4rem;
    width: .6rem;
    padding-right: .3rem;
    margin-right: .3rem;
  }
  .collect_list_content {
    background: #fff;
  }
  li.sc_list_wrap  a{
    margin-top: 2px;
    padding: .2rem .4rem;
    background: #ffffff;
    display: flex;
    justify-content: flex-start;
    border-bottom: 1px solid #f5f5f5;
  }
  li.sc_list_wrap a > p {
    width: 30%;
  }
  li.sc_list_wrap a > div {
    width: 70%;
    text-align: left;
    padding-left: .3rem;
    line-height: 1.8;
  }
  li.sc_list_wrap a > div > p.mmj_pro_tit_name {
    font-weight: bold;
    font-size: .42rem;
  }
</style>
