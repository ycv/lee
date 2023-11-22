<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-button @click="drawer = true" type="primary" style="margin-left: 16px;">
          点我打开
        </el-button>
      </div>
      <div>
        <el-button icon="el-icon-refresh" type="success" @click="srcPage">页面跳转2</el-button>
      </div>
    </div>
    <div style="margin-top: 6px;border:1px solid #b8e5b1;">
      <el-tooltip popper-class="trap" placement="bottom-end">
        <el-tag>testTip</el-tag>
        <div slot="content">
          　<span>1.所选的表必须设有主键，并且是单字段主键</span><br/>
          　<span>2.必须保证主键长度至少19位</span>
        </div>
      </el-tooltip>
    </div>

    <el-drawer
        :title="stockName" size="82%" :destroy-on-close="true"
        :visible.sync="drawer" ref="stockMaDrawer" @open="stockMaDrawerOpen" @opened="stockMaDrawerOpened"
        :direction="directionType">
      <stock-chart-data :stock-param="stockCodeParam"/>
    </el-drawer>
  </div>
</template>

<script>


import StockChartData from "@/components/stock/StockChartData";

export default {
  name: "PerEc",
  components: {
    StockChartData
  },
  data() {
    return {
      stockName: '',
      stockCodeParam: {stockCode: '', beginDate: '', endDate: ''},
      directionType: 'rtl',//ltr  rtl ttb btt
      drawer: false,
    };
  },
  created() {

  },

  mounted() {
    this.getParams();
  },

  methods: {
    stockMaDrawerOpen() {
      console.log("Open 222");
      this.stockCodeParam.stockCode = '002094';
      this.stockCodeParam.beginDate = '2022-09-07';
      this.stockCodeParam.endDate = '2022-09-19';
      this.stockName = "长江材料(sz)    " + this.stockCodeParam.stockCode + "    [" + this.stockCodeParam.beginDate + "~" + this.stockCodeParam.endDate + "]";
    },
    stockMaDrawerOpened() {
      console.log("opened 9999");
    },

    getParams() {
      console.log("Test2Test2a ");
      if (Object.getOwnPropertyNames(this.$route.params).length > 0 && this.$route.params.hasOwnProperty('code')) {
        console.log(this.$route.params);
      }
    },
    srcPage() {
      console.log("Test2Test2Test2222");
      this.$router.push({path: "/per/emp", query: {courseId: 200, code: 300}})
      // this.$router.push({
      //   path: '/per/emp',
      //   query: {
      //     searchObj: {type: "monitoring1", data: "data_real", begin: "2018-12-20", end: "2018-12-29"}
      //   }
      // });
    }
  }
}
</script>

<style scoped>
/deep/ .el-drawer__header {
  margin-bottom: 4px !important;
  font-size: 22px;
}
</style>