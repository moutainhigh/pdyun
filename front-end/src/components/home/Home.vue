<template>
  <div>
    <homeheader></homeheader>
    <noticebar></noticebar>
    <!--<div>-->
      <!--<div class="ms_content">-->
        <!--<div class="ms_wrap">-->
          <!--<div class="ms_img">-->
            <!--<div>-->
              <!--<span>买卖街秒杀: </span>-->
              <!--<span>0</span>-->
            <!--</div>-->
            <!--<p><span>零售价: </span><span class="colorR">&yen;0</span></p>-->
          <!--</div>-->
          <!--<p class="bgR">-->
            <!--<a href="javascript:;" @click="systermWH">-->
              <!--<img src="./homeImgs/home_onebuy.png" alt="">-->
              <!--<span class="buy_yj">一键买入</span>-->
            <!--</a>-->
            <!--<a href="javascript:;" @click="systermWH">-->
              <!--<span class="sel_yj">一键卖出</span>-->
              <!--<img src="./homeImgs/home_onesell.png" alt="">-->
            <!--</a>-->
          <!--</p>-->
        <!--</div>-->
        <!--<div class="msc_wrap">-->
          <!--&lt;!&ndash;倒计时组件的位置&ndash;&gt;-->
          <!--<div class="yjms_price">-->
            <!--<div class="ms_back">-->
              <!--<div class="ms_price_content">-->
                <!--<p><span>秒杀基准价: </span><span></span></p>-->
                <!--<p><span>当前成交价: </span><span></span></p>-->
              <!--</div>-->
              <!--<p class="ms_back_timeC">-->
                <!--<span>秒杀倒计时 </span>-->
                <!--<span class="bg_black">00</span>:-->
                <!--<span class="bg_black">00</span>:-->
                <!--<span class="bg_black">00</span>-->
              <!--</p>-->
            <!--</div>-->
            <!--<div class="back_wrapper">-->
              <!--<p class="num_price" >-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">.</span>-->
                <!--<span class="bg_f">0</span>-->
                <!--<span class="bg_f">0</span>-->
              <!--</p>-->
              <!--<p class="ch_price">-->
                <!--<span>万</span>-->
                <!--<span>千</span>-->
                <!--<span>百</span>-->
                <!--<span>十</span>-->
                <!--<span>个</span>-->
                <!--<i>·</i>-->
                <!--<span>角</span>-->
                <!--<span>分</span>-->
              <!--</p>-->
            <!--</div>-->
          <!--</div>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->

    <van-swipe @change="onChange" :show-indicators="false"  >
      <van-swipe-item  v-for = "proItem of gxProListInfos">
        <div class="ms_content">
          <div class="ms_wrap">
            <div class="ms_img">
              <div>
                <span>买卖街秒杀: </span>
                <span>{{proItem.goodsName}}</span>
              </div>
              <p><span>零售价: </span><span class="colorR">&yen;{{parseFloat(nowPrice)}}</span></p>
            </div>
            <p class="bgR">
              <router-link to="/mall">
                <img src="./homeImgs/home_onebuy.png" alt="">
                <span class="buy_yj">一键买入</span>
              </router-link>
              <router-link to="/orderManagement">
                <span class="sel_yj">一键卖出</span>
                <img src="./homeImgs/home_onesell.png" alt="">
              </router-link>
            </p>
          </div>
          <div class="msc_wrap">
            <!--倒计时组件的位置-->
            <div class="yjms_price">
              <div class="ms_back">
                <div class="ms_price_content">
                  <p><span>秒杀基准价: </span><span>{{basePrice}}</span></p>
                  <p><span>当前成交价: </span><span>{{newPrice}}</span></p>
                </div>
                <p class="ms_back_timeC">
                  <span>秒杀倒计时 </span>
                  <span class="bg_black">{{phours}}</span>:
                  <span class="bg_black"><span v-show="zeroSH">0</span>{{pmintues}}</span>:
                  <span class="bg_black"><span v-show="zeroSecondS">0</span>{{psecondes}}</span>
                </p>
              </div>
              <div class="back_wrapper">
                <p class="num_price" >
                  <span class="bg_f" v-for="priceItem of curProPrice">{{priceItem}}</span>
                </p>
                <p class="ch_price">
                  <span>万</span>
                  <span>千</span>
                  <span>百</span>
                  <span>十</span>
                  <span>个</span>
                  <i>·</i>
                  <span>角</span>
                  <span>分</span>
                </p>
              </div>
            </div>
          </div>
        </div>
      </van-swipe-item>
    </van-swipe>
    <homelist></homelist>
    <hotrecommend></hotrecommend>
    <div class="home_apply">
      <p><span>合作单位: </span><span>品道收藏</span></p>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue';
  import {Toast,Swipe, SwipeItem} from 'vant';
  import {Timer} from './timeout';
  Vue.use(Toast);
  Vue.use(Swipe).use(SwipeItem);
  import homelist from './gnlist';
  import noticebar from './noticeBar';
  import homeheader from './Home_header';
  import hotrecommend from './hot_recommended';
  import store from '@/vuex/index';
  export default {
    name: 'Home',
    data () {
      return {
        gxProListInfos: [],
        curProPrice: '',
        curProId: 0,
        phours: '0',
        pmintues: '0',
        psecondes: '0',
        defaultShow: true,
        xunhunShow: false,
        systermTime: '',
        basePrice: '',
        newPrice: '',
        nowPrice: '',
        curWeekday : '',
        curTimeDuan: '',
        curYears: '',
        curMonth: '',
        curDay: '',
        zeroSH: false,/*初始化分钟是否补0*/
        zeroSecondS: false,/*初始化秒是否补0*/
        curHour: '',
        curSysMin: '',
        curSysSecond: '',
        initState: '',/*页面初始化的状态,根据这个状态来判断是否在用户设置的交易时间内*/
        _ordertimer: ''
      }
    },
    created(){
      store.commit('footShow');
      this.getProId();
      /*this.timer = setInterval(this.getSysterTimer,1000);*//*页面初始化的时候就进入定时器任务*/
      this.getSysterTimer();
    },
    methods: {
      systermWH() {
        Toast('系统维护中');
      },
      getGoodsPrice(id) {
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/getSubGoodsById',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            'id': id
          }
        }).then(res => {
          if (res.data.data.goodsInfo.goodsCost != 0) {
            if(Number(res.data.data.newPrice) == 0){
              console.log(parseFloat(res.data.data.retailPrice).toString());
              this.curProPrice = parseFloat(res.data.data.retailPrice).toString();
              this.nowPrice = res.data.data.retailPrice;
              this.basePrice = res.data.data.basePrice;
              this.newPrice = res.data.data.newPrice;
            }else {
              console.log(parseFloat(res.data.data.newPrice).toString());
              this.curProPrice = parseFloat(res.data.data.newPrice).toString();
              this.nowPrice = res.data.data.retailPrice;
              this.basePrice = res.data.data.basePrice;
              this.newPrice = res.data.data.newPrice;
            }

          } else {
            this.curProPrice = parseFloat(res.data.data.goodsInfo.goodsSubPrice).toString();
          }

          let curPricelength = this.curProPrice.length;
          //先判断当前价格里面有没有小数点
          if (this.curProPrice.indexOf('.') > 0) {//找不到会返回-1，这个是说明找到了
            //要判断小数点后面的位数
            let xsNumLength = this.curProPrice.split('.')[1].length;
            if (xsNumLength == 2) {//说明小数位是2位数了，那么要判断整数位数是否为5位
              if (curPricelength == 8) {
                this.curProPrice = this.curProPrice;
              } else if (curPricelength == 7) {
                this.curProPrice = '0' + this.curProPrice;
              } else if (curPricelength == 6) {
                this.curProPrice = '00' + this.curProPrice;
              } else if (curPricelength == 5) {
                this.curProPrice = '000' + this.curProPrice;
              } else if (curPricelength == 4) {
                this.curProPrice = '0000' + this.curProPrice;
              }
            } else {//小数点位数为1，后面需要补0，
              if (curPricelength == 7) {
                this.curProPrice = this.curProPrice + '0';
              } else if (curPricelength == 6) {
                this.curProPrice = '0' + this.curProPrice + '0';
              } else if (curPricelength == 5) {
                this.curProPrice = '00' + this.curProPrice + '0';
              } else if (curPricelength == 4) {
                this.curProPrice = '000' + this.curProPrice + '0';
              } else if (curPricelength == 3) {
                this.curProPrice = '0000' + this.curProPrice + '0';
              }
            }
          } else {//没找到小数点，说明此时的curPricelength位数完全是整数位
            if (curPricelength == 5) {
              this.curProPrice = this.curProPrice + '.' + '00';
            } else if (curPricelength == 4) {
              this.curProPrice = '0' + this.curProPrice + '.' + '00';
            } else if (curPricelength == 3) {
              this.curProPrice = '00' + this.curProPrice + '.' + '00';
            } else if (curPricelength == 2) {
              this.curProPrice = '000' + this.curProPrice + '.' + '00';
            } else if (curPricelength == 1) {
              this.curProPrice = '0000' + this.curProPrice + '.' + '00';
            }
          }
        }).catch(error => {
          ;
        });
      },
      onChange(index) {
        this.curProId = index;
        //获取产品当前价格
        this.getGoodsPrice(this.gxProListInfos[index].id);
      },
      getSysterTimer() {
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/getSysTime',
        }).then(res => {
          if (res.data.error_code === 0) {
            console.log(res.data.data.sysTime)
            this.systermTime = res.data.data.sysTime;
            // /*获取当前年*/
            this.curYears = parseInt(this.systermTime.split(' ')[0].split('-')[0]);
            /*获取当前月*/
            this.curMounth = parseInt(this.systermTime.split(' ')[0].split('-')[1]);
            /*获取当前日*/
            this.curDay = parseInt(this.systermTime.split(' ')[0].split('-')[2]);
            /*获取当前系统小时数*/
            this.curHour = parseInt(this.systermTime.split(' ')[1].split(':')[0]);
            /*获取当前系统分钟数*/
            this.curSysMin = parseInt(this.systermTime.split(' ')[1].split(':')[1]);
            /*获取当前系统秒数*/
            this.curSysSecond = parseInt(this.systermTime.split(' ')[1].split(':')[2]);
            //
            // console.log(this.curHour + '--' + this.curSysMin + '--' + this.curSysSecond);
            // this.getHTState();
            /*每秒钟调用一下状态是否改变*/

            /* 获取开盘休市时间段 */
            this.$http({
              method: 'get',
              url: this.url + '/api/sub/openWeekAndTime',
              header: {
                'Content-type': 'application/x-www-form-urlencoded',
              }
            }).then(res => {
              // console.log(res.data);
              if (res.data.error_code == 0) {
                this.curWeekday = res.data.data.openWeekAndTime.weekDaySet;
                this.curTimeDuan = res.data.data.openWeekAndTime.openTime;
                // console.log(JSON.stringify(this.gxProListInfos) + '111111');
                // console.log(this.timers.length + ' ssssssssssss ' + (this.gxProListInfos[0].id != ''));
                if(!!this.gxProListInfos && this.gxProListInfos.length > 0){
                  this.timers.forEach(it=>{
                    it.destroy();
                  });
                  this.timers.length = 0;
                  this.timers.push(new Timer(this.curTimeDuan.split(',')[0], this.systermTime, (a, b)=> {
                    /*console.log(a, b);*/
                    // console.log(11111111111111);
                    this.timeOut(a, b);
                  }));
                  this.timers.push(new Timer(this.curTimeDuan.split(',')[1], this.systermTime, (a, b)=> {
                    /*console.log(a, b);*/
                    // console.log(222222222222222222222);
                    this.timeOut(a, b);
                  }));
                }
              } else {

              }
            });

          } else {
            Toast('获取系统时间失败');
          }
        });
      },
      timeOut (a, b) {
        this.defaultShow = false;
        this.xunhunShow = true;
        this.phours = b[0] < 10 ? ('0'+b[0]) : b[0]+'';
        this.pmintues = b[1] < 10 ? ('0'+b[1]) : b[1]+'';
        this.psecondes = b[2] < 10 ? ('0'+b[2]) : b[2]+'';
      },
      pbackTime() {
        if (this.pmintues > 0) {
          if (this.pmintues < 10) {
            this.zeroSH = true;
          } else {
            this.zeroSH = false;
          }
          this.psecondes--;
          if (this.psecondes < 10) {
            this.zeroSecondS = true;
            if (this.psecondes == 0) {
              this.pmintues--;
              this.psecondes = 59;
              this.zeroSecondS = false;
            }
          } else {
            this.zeroSecondS = false;
          }
        } else {
          //调取获取当前单价的接口函数
          this.getProId();
          /*调用获取产品id的接口函数*/
          this.pmintues = 59;
        }
      },
      getTradeTime() {
        /**
         * 截取开盘时间字符串，比较当前系统时间。
         * */
        /* 循环次数 */
        var count = 0;
        // "openTime": "9:00-12:00,13:30-16:30"
        var timeHours = this.curTimeDuan.split(",");
        for (var i = 0; i < timeHours.length; i++) {
          var timeNums = timeHours[i].split("-");
          var openTime = timeNums[0].split(":");
          var endTime = timeNums[1].split(":");
          /* 获取当前时间，开盘时间 */
          var nowTime = (new Date(this.systermTime)).getTime();
          var openTimeStr = (new Date(this.curYears + '/' + this.curMonth + '/' + this.curDay + ' ' + openTime[0] + ':' + openTime[1])).getTime();


          var endTimeStr = (new Date(`${this.curYears}/${this.curMounth}/${this.curDay} ${endTime[0]}:${endTime[1]}`)).getTime();
          // console.log("------" + `${this.curYears}/${this.curMounth}/${this.curDay} ${endTime[0]}:${endTime[1]}`);
          var date1 = (new Date(this.systermTime)).getTime(), data2 = endTimeStr;

          if (data2 < date1) continue; //设置的时间小于现在时间退出
        }

      },
      getHTState() {
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/judgeOpenTime',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          }
        }).then(res => {
          if (res.data.error_code == 0) {
            this.initState = res.data.data.isOpenTime;
            if (this.initState) {
              this.defaultShow = false;
              this.xunhunShow = true;

              this.pbackTime();
              /*当状态返回的是true的时候，调用倒计时的实现过程*/

            } else {
              this.$Toast('处在非交易时间段');
              this.defaultShow = true;
              this.xunhunShow = false;
            }
          } else {
            this.$Toast('请联系管理员');
            this.defaultShow = true;
            this.xunhunShow = false;
          }
        });
      },
      getProId() {
        //获取购销产品id的接口
        this.$http({
          method: 'get',
          url: this.url + '/api/sub/getSubGoods',
          header: {
            'Content-type': 'application/x-www-form-urlencoded',
          },
          params: {
            'status': '2'
          }
        }).then(res => {
          if (res.data.error_code === 0) {
            if (res.data.data.list != '') {
              this.defaultShow = false;
              this.xunhunShow = true;
              this.gxProListInfos = res.data.data.list;
              if (this.gxProListInfos != '') {
                this.getGoodsPrice(this.gxProListInfos[this.curProId].id);
              } else {
                this.$Toast('产品数据为空');
                this.defaultShow = true;
                this.xunhunShow = false;
              }
            } else {
              this.defaultShow = true;
              this.xunhunShow = false;
            }
          } else {
            Toast('请求的error_code不为0');
          }
        })
      },
    },
    components:{
      homelist,
      homeheader,
      noticebar,
      hotrecommend
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .ms_price_content {
    font-size: .4rem;
    text-align: left;
  }
  .kuaixun img {
    width: 1.4rem;
    margin-right: .3rem;
    margin-top: .1rem;
    vertical-align: middle;
  }
  .kuaixun > ul {
    width: 80%;
  }
  .back_wrapper {
    margin-top: .5rem;
  }
  .num_price span {
    display: inline-block;
    padding: .1rem .3rem;
    font-size: .6rem;
    margin-right: .1rem;
  }
  .ch_price span{
    display: inline-block;
    width: 1rem;
    font-size: .4rem;
    margin-top: .2rem;
  }
  .bg_f {
    background: #fff;
    border-radius: .1rem;
  }
  .ms_content {
    background: #fff;
    padding: .1rem .3rem;
    font-size: 14px;
    padding-top: .2rem;
  }
  .bg_black {
    background: #000000;
    color: #fff;
    display: inline-block;
    padding: 0 .05rem;
    padding-top: .05rem;
    border-radius: .1rem;
  }
  .colorR {
    color: #E21918;
    font-size: .5rem;
  }
  .bgR a {
    color: #fff;
    margin-right: .1rem;
  }
  .bgR a img {
    width: 2rem;
  }
  .ms_wrap{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: .3rem;
  }
  .msc_wrap {
    width: 98%;
    margin: auto;
    background: #f5f5f5;
  }
  .ms_back_timeC {
    font-size: .4rem;
  }
  .bgR {
    color: #fff;
    position: relative;
    font-size: .3rem;
  }
  .bgR span.buy_yj {
    position: absolute;
    top: .15rem;
    left: .3rem;
  }
  .sel_yj {
    position: absolute;
    top: .15rem;
    right: .4rem;
  }
  .yjms_price {
    background: #f5f5f5;
    padding: .3rem;
    font-size: .3rem;
  }
  .ms_back {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .home_apply {
    width: 100%;
    background: #f5f5f5;
    margin-top: .1rem;
    color: #553006;
    text-align: left;
    padding: .2rem .4rem;
    font-size: .4rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
  }
  .ms_img {
    font-size: .42rem;
    line-height: 1.5;
    text-align: left;
  }
  .ms_img img{
    width: .7rem;
    margin-right: .2rem;
    vertical-align: middle;
  }
  .all_ms_img img {
    width: 1.6rem;
  }

</style>
