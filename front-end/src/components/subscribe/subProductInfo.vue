<template>
  <div>
    <div class="proDetailInfo"><router-link to="/subscribe"><img src="./subscribImgs/hose_back.png" alt=""></router-link>
      <p>
        <span  v-for="(item,index) in tabs" :class="{active:index == num}" @click="tab(index)">{{item}}</span>
      </p>

    </div>
    <div class="proListInfo" ref="proListInfo">
      <div v-show="productIntroInfoIsshow" class="productIntroInfo">
        <p>茶农2号 - 白茶茶膏</p>
        <ul class="userBuyInfo">
          <li><img src="./subscribImgs/probuy_username.png" alt=""><span>卖家昵称：</span><span>**29</span></li>
          <li><img src="./subscribImgs/probuy_num.png" alt=""><span>数量：</span><span>18</span></li>
        </ul>
      </div>
      <div v-show="proIntroduceIsshow" class="proIntroduce" >
        <ul>
          <li>包装规格: 20粒 / 瓶，9瓶 / 箱</li>
          <li>重量： 10克/瓶</li>
          <li>提货规则： 提货最小单位为箱</li>
        </ul>
      </div>
      <div v-show="detailContentIsshow" class="detailContent" >
        <p>在明清时期，Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aperiam autem corporis deserunt error esse, facere ipsa itaque iure labore molestias, qui reiciendis voluptatum! Ducimus incidunt magnam minima nostrum quas voluptatibus.</p>
        <p>茶农传世茶，白茶茶膏产品外包装又中国景德镇考古研究院。景德镇弘业宫廷瓷为之特别定制，极具收藏价值</p>
        <table>
          <tbody>
          <tr>
            <td>发行数量</td>
            <td>总重66万瓶</td>
          </tr>
          <tr>
            <td>单瓶规格</td>
            <td>20粒，10克/瓶</td>
          </tr>
          <tr>
            <td>包装规格</td>
            <td>9瓶/箱，提货最小单位为箱</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="proListFooter">

    </div>
  </div>
</template>

<script>
  import store from '@/vuex/index';
  export default {
    name: "sub_product_info",
    created(){
      store.commit('footHide');
    },
    data(){
      return {
        tabs: ["商品", "介绍","详情"],
        num: 0,
        productIntroInfoIsshow:true,
        proIntroduceIsshow:false,
        detailContentIsshow:false,
      }
    },
    methods:{
      tab(index) {
        if(index==0){
          this.productIntroInfoIsshow=true;
          this.proIntroduceIsshow=false;
          this.detailContentIsshow=false
        }else if(index==1){
          this.productIntroInfoIsshow=false;
          this.proIntroduceIsshow=true;
          this.detailContentIsshow=false
        }else if(index==2){
          this.productIntroInfoIsshow=false;
          this.proIntroduceIsshow=false;
          this.detailContentIsshow=true
        }
        this.num = index;
      }
    }
  }
</script>

<style scoped>
  .proDetailInfo {
    position: relative;
    background: #fff;
    padding-top: .3rem;
    border-bottom: 1px solid #ddd;
  }
  .proDetailInfo img {
    position: absolute;
    top: .3rem;
    left: .3rem;
    width: .4rem;
  }
  .proDetailInfo p {
    display: flex;
    justify-content: space-around;
    width: 60%;
    align-items: center;
    margin: auto;
  }
  .proDetailInfo p span {
    width: 20%;
    padding-bottom: .3rem;
  }
  .proDetailInfo p span.active {
    border-bottom: 1px solid #178f4a;
  }
  .proListInfo {
    background: #fff;
    height: 10rem;
    padding: .2rem;
  }
  .detailContent {
    text-align: left;
    padding: .1rem;
    background: #fff;
  }
  .detailContent table {
    border: 1px solid #000;
    border-collapse: collapse;
    margin-top: .2rem;
    width: 80%;
  }
  .detailContent table td {
    border: 1px solid #000;
  }
  .detailContent table td:first-child {
    font-weight: bold;
  }
  .detailContent table td:last-child {
    text-align: center;
  }
  .productIntroInfo {
    text-align: left;
  }
  .userBuyInfo {
    margin-top: .5rem;
    line-height: 2.5;
  }
  .userBuyInfo img {
    width: .5rem;
    margin-right: .3rem;
  }
  .proIntroduce {
    text-align: left;
    background: #fff;
  }
</style>
