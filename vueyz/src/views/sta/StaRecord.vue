<template>
  <div>
    <div>
      <div style="display: flex;justify-content: space-between">
        <div>
          <el-input placeholder="请输名称进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                    clearable
                    @clear="initStockAllRecord"
                    style="width: 300px;margin-right: 10px" v-model="stockDetailsParam.name"
                    @keydown.enter.native="initStockAllRecord" :disabled="showAllStockSearchView"></el-input>

          <el-button icon="el-icon-search" type="primary" @click="initStockAllRecord"
                     :disabled="showAllStockSearchView">
            搜索
          </el-button>

          <el-button type="primary" @click="setStockSearchParam">
            <i :class="showAllStockSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'" aria-hidden="true"></i>
            高级搜索
          </el-button>
        </div>
        <div>
          <el-button icon="el-icon-search" type="success" @click="getStockRSIDate">RSI走势</el-button>
          <el-button icon="el-icon-refresh" type="info" @click="actStockDate">统计数据</el-button>
        </div>
      </div>
      <transition name="slide-fade">
        <div v-show="showAllStockSearchView"
             style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
          <el-row>
            <el-col :span="6">
              涨跌幅:
              <el-input size="mini" style="width: 170px" prefix-icon="el-icon-edit"
                        v-model="stockDetailsParam.diff_rate" type="number" placeholder="请输入涨跌幅"></el-input>
            </el-col>

            <el-col :span="6">
              振&nbsp;&nbsp;幅:
              <el-input size="mini" style="width: 170px" prefix-icon="el-icon-edit" v-model="stockDetailsParam.swing"
                        type="number" placeholder="请输入振幅"></el-input>
            </el-col>


            <el-col :span="6">
              换手率:
              <el-input size="mini" style="width: 170px" prefix-icon="el-icon-edit"
                        v-model="stockDetailsParam.turnover" type="number" placeholder="请输入换手率"></el-input>
            </el-col>

            <el-col :span="6">

            </el-col>
          </el-row>

          <el-row style="margin-top: 10px">
            <el-col :span="6">
              交易数:
              <el-input size="mini" style="width: 170px" prefix-icon="el-icon-edit"
                        v-model="stockDetailsParam.tradeNum" type="number" placeholder="请输入交易数"></el-input>
            </el-col>

            <el-col :span="6">
              市场:
              <el-radio-group v-model="stockDetailsParam.market">
                <el-radio label="sh">上海</el-radio>
                <el-radio label="sz">深圳</el-radio>
              </el-radio-group>
            </el-col>

            <el-col :span="12">
              日期区间:
              <el-date-picker
                  v-model="stockDetailsParam.beginDateScope"
                  type="daterange"
                  size="mini"
                  unlink-panels
                  value-format="yyyy-MM-dd"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-col>
          </el-row>

          <el-row style="margin-top: 10px">
            <el-col :span="6">&nbsp;</el-col>
            <el-col :span="6">&nbsp;</el-col>
            <el-col :span="6">&nbsp;</el-col>
            <!--            justify=center 居中对齐   justify=start 左对齐   justify=end 右对齐-->
            <!--            justify=space-between 空格间距在中间对齐   justify=space-around 左右各占半格空格对齐-->
            <el-col :span="6" style="display: flex;justify-content: end">
              <el-button size="mini" icon="el-icon-moon" @click="setStockSearchParam">取消</el-button>
              <el-button size="mini" icon="el-icon-search" type="primary" @click="initStockAllRecord">搜索</el-button>
            </el-col>
          </el-row>

        </div>
      </transition>
    </div>
    <div style="margin-top: 6px">
      <!--      :header-cell-style="{fontSize:'16px',color:'#def9ff',fontFamily:'MicrosoftYaHei',background:'transparent'}"-->
      <el-table :data="stockAllRecord" border stripe v-loading="loading" fixed
                element-loading-text="正在加载..." highlight-current-row
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)" @select="handleSelectionChange" @row-click="rowClick"
                ref="tbStockQuality" style="width: 100%">
        <el-table-column type="selection" width="40" align="center"></el-table-column>

        <el-table-column label="股票基本信息" align="center">
          <el-table-column width="88" label="名称" align="center">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.market == 'sz'">{{ scope.row.name }}</el-tag>
              <el-tag type="danger" v-else-if="scope.row.market == 'sh'">{{ scope.row.name }}</el-tag>
              <el-tag type="info" v-else>{{ scope.row.name }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column width="64" prop="code" label="代码" align="center"></el-table-column>
          <!--          <el-table-column width="70" prop="market" label="市场" align="center"></el-table-column>-->
        </el-table-column>

        <el-table-column label="价格走势" align="center">
          <el-table-column width="58" prop="closePrice" label="昨日收盘价" align="left"></el-table-column>
          <el-table-column width="58" prop="openPrice" label="今日开盘价" align="left"></el-table-column>
          <el-table-column width="58" prop="todayMin" label="今日最低价" align="left"></el-table-column>
          <el-table-column width="58" prop="todayMax" label="今日最高价" align="left"></el-table-column>
          <el-table-column width="58" prop="nowPrice" label="当前价" align="left"></el-table-column>
        </el-table-column>

        <el-table-column width="88" label="涨跌幅" align="left">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.diff_rate > 0">{{ scope.row.diff_rate }}%</el-tag>
            <el-tag type="success" v-else>{{ scope.row.diff_rate }}%</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="84" label="涨跌金额" align="left">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.diff_money>0">{{ scope.row.diff_money }}￥</el-tag>
            <el-tag type="success" v-else>{{ scope.row.diff_money }}￥</el-tag>
          </template>
        </el-table-column>


        <el-table-column width="54" prop="swing" align="left">
          <template v-slot:header>
            <el-tooltip content="个股振幅越大，说明主力资金介入的程度就越深，反之，就越小" placement="top-start" popper-class="trap" effect="dark">
              <span style="cursor: pointer">振幅</span>
            </el-tooltip>
          </template>
        </el-table-column>


        <el-table-column width="54" prop="liangbi" align="left">
          <template v-slot:header>
            <el-tooltip placement="top-start" popper-class="trap" effect="dark">
              <span style="cursor: pointer">量比</span>
              <div slot="content">
                <span style="color: red;">0.&nbsp;&nbsp;缩量下跌时的买入;缩量上涨时买入;量增价升，继续上涨;</span><br/>
                <span>1.&nbsp;&nbsp;量比为0.8-1.5倍，则说明成交量处于正常水平;</span><br/>
                <span>2.&nbsp;&nbsp;量比数值在1.5-2.5倍范围内：温和放量、主要是表明股票价格处于一种在稳步上升的阶段时，上升趋势相对健康；反之就是股价在下跌的时候，这个下跌趋势不会在短时间内结束;</span><br/>
                <span>3.&nbsp;&nbsp;量比数值在5-10倍范围内：剧烈放量、它出现在一只股票长期价格低位的时候，未来的涨势空间会非常大；反之如果它出现在一只股价上涨巨大的过程中，投资者就要保持高度警惕;</span><br/>
                <span>4.&nbsp;&nbsp;在股票涨停板的时候，量比指标在1倍以下就是表明它未来趋势不可限量，在第二天会有很大可能开盘即封涨停的情况。量比在0.5倍的时候也需要投资者多多关注!</span>
              </div>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-table-column width="70" prop="turnover" align="left">
          <template v-slot:header>
            <el-tooltip placement="top-start" popper-class="trap" effect="dark">
              <i class="el-icon-warning-outline" style="cursor: pointer"></i>
              <div slot="content">
                <span>1.&nbsp;&nbsp;价格在高位，换手率高，一般为庄家出货所致;在低位换手率高的话;一般为庄家进货造成;</span><br/>
                <span>2.&nbsp;&nbsp;个股低位换手率度过活跃期后,日换手率大于7%,且持续超过10%时,表明筹码正在急剧换手,个股将会出现急剧飙升;</span><br/>
                <span>3.&nbsp;&nbsp;当一只股票的换手率非常高时，股票价格通常不是在大涨就是在大跌;</span><br/>
                <span>4.&nbsp;&nbsp;长期换手率低的股票，价格又在低位属冷门股碰不得!</span>
              </div>
            </el-tooltip>
            <span>换手率</span>
          </template>
        </el-table-column>


        <el-table-column width="70" prop="appointDiff" label="委差(手)" align="left"></el-table-column>
        <el-table-column width="60" prop="appointRate" label="委比%" align="left"></el-table-column>


        <el-table-column width="78" prop="waipanStr" align="left">
          <template v-slot:header>
            <el-tooltip content="市场上外盘数量小于内盘数量时,现在的市场行情是比较偏向卖方力量的,这个时候的股票价格下跌的可能性很大" placement="top-start"
                        popper-class="trap" effect="dark">
              <span style="cursor: pointer">外盘</span>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-table-column width="78" prop="neipanStr" align="left">
          <template v-slot:header>
            <el-tooltip content="内盘数量小于外盘数量时,现在的市场行情是比较偏向买方力量的,这个时候股票价格上涨的可能性很大" placement="top-start"
                        popper-class="trap" effect="dark">
              <span style="cursor: pointer">内盘</span>
            </el-tooltip>
          </template>
        </el-table-column>


        <el-table-column width="60" prop="pb" label="市净率" align="left"></el-table-column>
        <el-table-column width="68" prop="pe" label="市盈率" align="left"></el-table-column>


        <el-table-column label="成交量" align="center">
          <el-table-column width="100" prop="tradeNumStr" label="交易数(股)" align="left"
                           :show-overflow-tooltip="true"></el-table-column>
          <el-table-column width="136" label="成交金额(元)" align="left" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <el-tag>{{ scope.row.tradeAmountStr }}</el-tag>
            </template>
          </el-table-column>
        </el-table-column>

        <el-table-column label="更新日期" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <!--            <el-tag>{{ scope.row.date }} {{ scope.row.time }}</el-tag>-->
            <el-tag> {{ replaceStr(scope.row.date) }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex;justify-content: flex-end">
        <el-pagination
            background
            @current-change="currentChange"
            @size-change="sizeChange"
            layout="sizes, prev, pager, next, jumper, ->, total, slot"
            :total="total">
        </el-pagination>
      </div>
    </div>

    <el-dialog :title="stockRSIViewTitle" :visible.sync="dialogStockRSIVisible" width="90%">
    </el-dialog>
  </div>
</template>

<script>

import stockRsiApi from '@/api/sta/stockrsi';
import stockInfoApi from '@/api/sta/stockinfo';
import {mymessage} from "@/utils/mymessage";

export default {
  name: "StaRecord",
  data() {
    return {
      showAllStockSearchView: false,
      total: 0,
      loading: false,
      stockDetailsParam: {
        diff_rate: "", swing: "", turnover: "", market: "",
        tradeNum: "", diff_money: "", beginDateScope: null, beginDate: null, endDate: null, diffDayNum: null,
        name: "", page: 1, size: 10
      },
      multipleSelection: [],
      stockRSIViewTitle: "",
      dialogStockRSIVisible: false,
      stockAllRecord: []
    }
  },
  //created:在模板渲染成html前调用，即通常初始化某些属性值，然后再渲染成视图。
  created() {
    this.initStockAllRecord();
  },
  //mounted:在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
  mounted() {

  },
  // filters: {
  //   currency: currency,
  // },
  methods: {
    handleSelectionChange(selection, row) {
      if (this.multipleSelection[0] == row) {
        this.multipleSelection = [];
        this.$refs.tbStockQuality.clearSelection();
      } else {
        this.multipleSelection = [row];
        this.$refs.tbStockQuality.clearSelection();
        this.$refs.tbStockQuality.toggleRowSelection(row, true);
      }
    },
    // 点击行多选
    rowClick(row, column, event) {
      // 从已选中数据中 判断当前点击的是否被选中;  是取消选择还是选中
      const selected = this.multipleSelection.some(item => item.id === row.id);
      if (!selected) { // 不包含   代表选择
        this.multipleSelection.push(row);
        this.$refs.tbStockQuality.toggleRowSelection(row, true);
      } else { // 取消选择
        let finalArr = this.multipleSelection.filter((item) => {
          return item.id !== row.id;
        })
        this.multipleSelection = finalArr;  // 取消后剩余选中的
        this.$refs.tbStockQuality.toggleRowSelection(row, false);
      }
    },
    getStockRSIDate() {
      let stockCodeArr = [];
      if (this.multipleSelection.length > 0) {
        for (var k in this.multipleSelection) {
          stockCodeArr.push(this.multipleSelection[k].code);
        }
        stockCodeArr = Array.from(new Set(stockCodeArr));
        console.log(stockCodeArr);
        // this.$router.push("/sta/score");
        // this.$router.replace("/sta/score");
      } else {
        mymessage.warning("请先选中数据 ！");
      }
    },
    actStockDate() {
      if (this.multipleSelection.length == 1) {
        let multipleSelectionCode = this.multipleSelection[0].code;
        let multipleSelectionName = this.multipleSelection[0].name;
        this.$confirm('此操作将  【' + multipleSelectionName + '】  数据统计, 是否继续?', '提示', {
          iconClass: 'el-icon-question', // 自定义图标样式
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$Progress.start();
          stockRsiApi.getActStockDate("?code=" + multipleSelectionCode).then(resp => {
            if (resp && resp.status == 200) {
            }
            this.$Progress.finish();
          })
        }).catch(() => {
          mymessage.warning("取消数据统计 ！");//info  warning error success
        });
      } else {
        mymessage.warning("请先选中数据 ！");
      }
    },
    initStockDetailsParam() {
      //市场
      this.stockDetailsParam.market = "";
      //换手率
      this.stockDetailsParam.turnover = "";
      //振幅
      this.stockDetailsParam.swing = "";
      //跌涨幅度
      this.stockDetailsParam.diff_rate = "";
      //成交量(沪深为股，港股为手)
      this.stockDetailsParam.tradeNum = "";
      //跌涨金额
      this.stockDetailsParam.diff_money = "";
      //日期
      this.stockDetailsParam.beginDateScope = null;

    },
    setStockSearchParam() {
      this.showAllStockSearchView = !this.showAllStockSearchView;
      this.initStockDetailsParam();
    },
    sizeChange(currentSize) {
      this.stockDetailsParam.size = currentSize;
      this.initStockAllRecord();
    },

    currentChange(currentPage) {
      this.stockDetailsParam.page = currentPage;
      this.initStockAllRecord();
    },


    replaceStr(str) {
      return str.slice(5).replace(str.slice(5).substring(2, 3), '月');
    },

    initStockAllRecord() {
      this.loading = true;
      this.stockDetailsParam.beginDate = null;
      this.stockDetailsParam.endDate = null;
      this.stockDetailsParam.diffDayNum = 1;
      if (this.stockDetailsParam.beginDateScope != null) {
        this.stockDetailsParam.beginDate = this.stockDetailsParam.beginDateScope[0];
        this.stockDetailsParam.endDate = this.stockDetailsParam.beginDateScope[1];
        // 取相差毫秒数的绝对值
        let diffDate = Math.abs(Date.parse(this.stockDetailsParam.beginDate) - Date.parse(this.stockDetailsParam.endDate));
        this.stockDetailsParam.diffDayNum = Math.floor(diffDate / (1000 * 3600 * 24)) // 向下取整
        this.stockDetailsParam.diffDayNum = this.stockDetailsParam.diffDayNum - 0;
      }
      console.log(this.stockDetailsParam);
      this.$Progress.start();
      stockInfoApi.getStockInfoRecord(this.stockDetailsParam).then(resp => {
        this.loading = false;
        console.log(resp);
        if (resp) {
          this.stockAllRecord = resp.data;
          this.total = resp.total;
        }
        this.$Progress.finish();
      });
    }
  }
}
</script>

<style scoped>

</style>