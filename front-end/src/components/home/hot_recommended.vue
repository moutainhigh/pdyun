<template>
    <div class="hot_pro_wrapC">
      <p class="hot_rec_wrap"><span>新品推荐</span><router-link to="/mall"><span>更多</span></router-link></p>
      <ul >
        <li class="hot_list_wrap" v-for="( hotProItem , index ) of hotProductInfos" v-if="index < 5" >
          <router-link tag="div" :to="{path: 'mall/productInfo',query: { goodsId: hotProItem.id, imgPath: hotProItem.imgLoadPath, proCode: hotProItem.goodsCode }}">
            <p>
              <img :src="hotProItem.imgLoadPath" alt="">
            </p>
            <div>
              <p class="hot_pro_tit_name"><span>{{hotProItem.goodsName}}</span></p>
              <p><span>商品代码：</span><span>{{hotProItem.goodsCode}}</span></p>
              <p><span>最新价：</span><span>{{hotProItem.sellPointNew}}</span></p>
              <p><span>卖出价：</span><span>{{hotProItem.buyPointMin}}</span></p>
            </div>
          </router-link>
        </li>
      </ul>
    </div>
</template>

<script>
    export default {
      name: "hot_recommended",
      data(){
        return {
          hotProductInfos: [],/*热门资讯数据*/
        }
      },
      created(){
        /*页面初始化的时候获取热门推荐产品 查询全部的*/
        this.$http({
          method:'get',
          url: this.url + '/api/sub/getSubGoods',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            status: '2',
            goodsTypeId: '',
          }
        }).then(res => {
          if(res.data.error_code === 0){
            if (res.data.data.list != ''){
              this.hotProductInfos = res.data.data.list;
            }else {
              this.$Toast('还没有热门推荐产品');
            }
          }
        })
      }
    }
</script>

<style scoped>
  .hot_rec_wrap {
    margin-top: 2px;
    color: #e07701;
    padding: .2rem .4rem;
    background: #ffffff;
    font-weight: bold;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  li.hot_list_wrap > div{
    margin-top: 2px;
    padding: .2rem .4rem;
    background: #ffffff;
    display: flex;
    justify-content: flex-start;
    border-bottom: 1px solid #f5f5f5;
  }
  li.hot_list_wrap > div > p {
    width: 30%;
  }
  li.hot_list_wrap > div > div {
    width: 70%;
    text-align: left;
    padding-left: .3rem;
    line-height: 1.8;
  }
  li.hot_list_wrap > div > div > p.hot_pro_tit_name {
    font-weight: bold;
    font-size: .42rem;
  }
  .hot_rec_wrap a span {
    color: #e07701;
  }
</style>
