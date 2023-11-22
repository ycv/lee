<template>
  <div>
    <div>
      <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="RSI指标" name="StockRsiData">
          <stock-rsi-data :rsi-param="stockRsiChartListParam" :rsi-width="chartClientWidth"/>
        </el-tab-pane>

        <el-tab-pane label="日线Kdj" name="StockKdjData">
          <stock-kdj-data :kdj-param="stockKdjChartListParam"/>
        </el-tab-pane>

        <el-tab-pane name="StockCciData" :lazy="true">
          <span slot="label"><i class="el-icon-date"></i> 我的行程</span>
          我的CCC行程
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="chartFooter">
      <el-row>
        <el-col :span="24">
          <el-form :inline="true" :model="formInline" label-width="80px" class="demo-form-inline"
                   :label-position="labelPosition">
            <el-form-item label="日期">
              <el-date-picker type="date" align="right" placeholder="选择日期" v-model="formInline.date"
                              :picker-options="pickerOptions" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="指标">
              <el-select v-model="formInline.region" multiple placeholder="请选择">
                <el-option
                    v-for="item in chartOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="评分">
              <el-rate v-model="formInline.rate" :colors="colorsRate"></el-rate>
            </el-form-item>


            <el-form-item label="备注">
              <el-input type="textarea" v-model="formInline.remark"></el-input>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="stockSelectSubmit">保持</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>


    </div>
    <!--    <div style="margin-top: 6px"></div>-->
    <!--      <span>{{ stockParam }}</span>-->
  </div>
</template>


<script>
/**
 * 日线wr
 * 买入信号:当股价开始下跌时，WR曲线开始向100轴线靠近，也就是开始向超卖区域靠近。一旦曲线进入这一区域，则意味着短期内有大量的卖盘出现，股价则会提示出现超跌信号，我们知道，超跌会有反弹，那么这就表明股价短期会有反弹需求。
 * 卖出信号:当股价开始上涨时，WR曲线开始向0轴线靠近，也就是说曲线向超买区域靠近。而一旦曲线进入该区域，则意味着股价已经进入超买区，我们要随时等待卖出信号的出现。同样，这个时候还不是卖出股票的最佳时间。
 * 短线买点:1慢线与快线在80以上粘合在一起,收阳日买入(激进买点);
 *        2两线同时下穿80买入(稳健买点)。
 * 当WR高于80，股票处于超卖状态，股价下跌力量基本用尽，股价将会上涨，投资者可以抄底入场。
 * 当WR小于20，股票处于超买状态，股价上升力量基本用尽，股价将会下跌，投资者应该及时出场。
 * 当WR位于20以下，形成W底、双重底、三重底、多重底或头肩底，说明股价处于严重超买，股价将会下跌，朋友们应当赶紧出场。
 * wr指标连续几次撞顶（底），局部形成双重或多重顶（底），是卖出（买进）的信号。
 * WR指标会在0-100区间波动，其中0-20这一区间是超买区。而20这一线为超买线;80-100这一区间是超卖区，而80线为超卖线;50线为多空平衡线
 *
 *
 * 日线cci
 * 当CCI曲线向上突破 100线而进入非常态区间时，表明股价开始进入强势状态，投资者应及时买入股票。
 * 当CCI曲线向上突破 100线而进入非常态区间后，只要CCI曲线一直朝上运行，就表明股价强势依旧，投资者可以一路持股待涨。
 * 当CCI曲线在 100线以上的非常态区间，在远离 100线的地方开始掉头向下时，表明股价的强势状态将难以维持，是股价比较强的转势信号。
 *
 *
 * MACD的日线
 * MACD也可以用形态学的有关理论进行分析。日线上的重要底部区域内，MACD走出“W”底的形态后，
 * 当DIF线向上突破“W”底的颈线位后股价分时缩量回踩获得支撑时即为短线重要的买点，可以积极介入进行短中线的获利操作。
 *
 *
 *
 * 日线wr+RSI指标
 * WR指标可以和RSI指标配合使用。当RSI从低位上穿50中轴线时，如果WR指标也同步上穿50中轴线，就表明价格运动已经从弱势区域进入强势区域，可能展开一波升势，反之亦然。
 *
 */
import stockChartApi from '@/api/sta/stockchart';
import {isEmptyObj} from '@/utils/common/validate';
import {mymessage} from "@/utils/mymessage";

import StockRsiData from '@/components/stock/chart/StockRsiData';
import StockKdjData from '@/components/stock/chart/StockKdjData';


export default {
  name: "StockChartData",
  components: {
    StockKdjData,
    StockRsiData
  },
  props: {
    stockParam: {
      type: Object,
      default: function () {
        return {stockCode: '002917', beginDate: '2022-09-15', endDate: '2022-09-15'}
      },
      required: true
    }
  },
  data() {
    return {
      colorsRate: ['#99A9BF', '#F7BA2A', '#FF9900'],
      //日线wr  日线cci 日线kdj ma均线  日线macd  RSI指标 布林带boll 乖离率BIAS
      chartOptions: [{
        value: '1',
        label: '日线wr'
      }, {
        value: '2',
        label: '日线cci'
      }, {
        value: '3',
        label: '日线kdj'
      }, {
        value: '4',
        label: 'ma均线'
      }, {
        value: '5',
        label: '日线macd'
      }, {
        value: '6',
        label: 'RSI指标'
      }, {
        value: '7',
        label: '布林带boll'
      }, {
        value: '8',
        label: '乖离率BIAS'
      }],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      labelPosition: 'right',//right left
      formInline: {
        date: '', region: '', rate: null, remark: ''
      },
      chartClientWidth: "",
      activeName: 'StockKdjData',//StockRsiData   StockKdjData
      stockRsiChartListParam: {rsi6: [], rsi12: [], rsi24: [], dateList: [], wr1: [], wr2: [], tradeAmount: []},
      stockKdjChartListParam: {k: [], d: [], j: [], cci: [], dateList: [], tradeAmount: []},
      stockKdjChartParam: {
        code: '', beginDate: '', endDate: ''
      }
    }
  },
  created() {
    this.$nextTick(() => {
      if (!isEmptyObj(this.stockParam)) {
        console.log("StockChartData 3333");
        this.stockKdjChartParam.code = this.stockParam.stockCode;
        this.stockKdjChartParam.beginDate = this.stockParam.beginDate;
        this.stockKdjChartParam.endDate = this.stockParam.endDate;
        this.getStockChartDataList();
        //最低价*最高价 开开根号 * 0.98

      }
    });
  },
  methods: {
    stockSelectSubmit() {
      console.log(this.formInline);
      console.log('submit222222222!');
    },
    handleClick(tab, event) {
      // document.documentElement.clientHeight
      let chartClientWidth = ((document.documentElement.clientWidth * 0.82 - 2) - 30) * 0.9;
      // 不四舍五入 字符串匹配再转换
      chartClientWidth = Number(chartClientWidth.toString().match(/^\d+(?:\.\d{0,0})?/));
      // chartClientWidth = Math.floor(chartClientWidth * 100) / 100;
      this.chartClientWidth = chartClientWidth + "px";
      console.log(this.chartClientWidth);
      console.log(tab.index);
    },
    async getStockChartDataList() {
      console.log(8888555);
      this.loading = true;
      this.$Progress.start();
      stockChartApi.getStockChartDataList(this.stockKdjChartParam).then(resp => {
        if (!isEmptyObj(resp) && resp.status == 200 && !isEmptyObj(resp.obj)) {
          console.log(888);
          console.log(resp);
          this.setStockRsiDataList(resp.obj);
          this.setStockKdjDataList(resp.obj);
        } else {
          mymessage.warning("无数据 ！");
        }
        this.loading = false;
        this.$Progress.finish();
      });
    },
    setStockRsiDataList(data) {
      this.stockRsiChartListParam.rsi6 = data.rsi6;
      this.stockRsiChartListParam.rsi12 = data.rsi12;
      this.stockRsiChartListParam.rsi24 = data.rsi24;
      this.stockRsiChartListParam.dateList = data.dateList;
      this.stockRsiChartListParam.wr2 = data.wr2;
      this.stockRsiChartListParam.wr1 = data.wr1;
      this.stockRsiChartListParam.tradeAmount = data.tradeAmount;
    },
    setStockKdjDataList(data) {
      this.stockKdjChartListParam.k = data.k;
      this.stockKdjChartListParam.d = data.d;
      this.stockKdjChartListParam.j = data.j;
      // this.stockKdjChartListParam.cci = data.cci;
      this.stockKdjChartListParam.dateList = data.dateList;
      this.stockKdjChartListParam.tradeAmount = data.tradeAmount;
    }
  }
}
</script>


<style scoped>

:root {
  --footer-height: 50px;
}


.chartFooter {
  position: fixed;
  text-align: left;
  bottom: 0;
  width: 100%;
  line-height: var(--footer-height);
  background: #42b983;
  color: #fff;
}
</style>