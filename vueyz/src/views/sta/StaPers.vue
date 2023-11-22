<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-input placeholder="请输名称进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                  clearable
                  @clear="initStockDragonTigerList"
                  style="width: 300px;margin-right: 10px" v-model="stockDragonTigerParam.name"
                  @keydown.enter.native="initStockDragonTigerList"
                  :disabled="showStockDragonTigerSearchView"></el-input>
        <el-button icon="el-icon-search" type="primary" @click="initStockDragonTigerList"
                   :disabled="showStockDragonTigerSearchView">
          搜索
        </el-button>
      </div>
      <div>
        <el-button icon="el-icon-refresh" type="success" @click="stockDragonTigerInfo">股票详情</el-button>
        <el-button icon="el-icon-plus" type="primary" @click="getStockDragonTigerList">添加用户</el-button>
      </div>
    </div>

    <div style="margin-top: 6px">
      <el-table :data="stockDragonTigerList" border stripe
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                @row-click="qualityStockDragonTigerClick" ref="tbStockDragonTigerList"
                @select="selectStockDragonTigerInfo"
                @selection-change="handleStockDragonTigerSelectionChange"
                style="width: 100%">
        <el-table-column type="selection" width="40" align="center"></el-table-column>
        <el-table-column width="70" prop="name" label="名称" align="center"></el-table-column>
        <el-table-column width="70" prop="code" label="代码" align="center"></el-table-column>
        <el-table-column width="70" label="市场" align="center">
          <template slot-scope="scope">
            <el-tag type="danger" v-if="scope.row.market == 'sh'">上海</el-tag>
            <el-tag type="success" v-else-if="scope.row.market == 'sz'">深圳</el-tag>
            <el-tag type="info" v-else>{{ scope.row.market }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="70" prop="chgStr" label="涨跌幅" align="center"></el-table-column>
        <el-table-column width="70" prop="close" label="收盘价" align="center"></el-table-column>
        <el-table-column width="70" prop="turnoverStr" label="换手率" align="center"></el-table-column>
        <el-table-column width="100" prop="buyAmountStr" label="总买入额" align="center"></el-table-column>
        <el-table-column width="100" prop="sellAmountStr" label="总卖出额" align="center"></el-table-column>
        <el-table-column width="120" label="日期" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.endDate }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="300" prop="reason" label="上榜原因" align="left"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="showEditStockDragonTigerView(scope.row)">详情</el-button>
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


  </div>
</template>

<script>

import stockDragonTiger from '@/api/sta/stockdragontiger';

export default {
  name: 'StaPers',
  data() {
    return {
      showStockDragonTigerSearchView: false,
      total: 0,
      loading: false,
      stockDragonTigerParam: {
        name: "", endDate: "", code: "",
        page: 1, size: 10
      }, selectData: {},
      stockDragonTigerList: []
    }
  },
  mounted() {
  },
  created() {
    this.initStockDragonTigerList();
  },
  methods: {
    showEditStockDragonTigerView(data) {
      console.log("111");
      console.log(data);
    },
    // 表格的选中 可以获得当前选中的数据
    handleStockDragonTigerSelectionChange(val) {
      this.selectData = val[0];
    },
    selectStockDragonTigerInfo(selection, row) {
      // 清除 所有勾选项
      this.$refs.tbStockDragonTigerList.clearSelection();
      // 当表格数据都没有被勾选的时候 就返回  主要用于将当前勾选的表格状态清除
      if (selection.length == 0) return;
      this.$refs.tbStockDragonTigerList.toggleRowSelection(row, true);
    },
    sizeChange(currentSize) {
      this.stockDragonTigerParam.size = currentSize;
      this.initStockDragonTigerList();
    },
    currentChange(currentPage) {
      this.stockDragonTigerParam.page = currentPage;
      this.initStockDragonTigerList();
    },
    //表格行内点击选中
    qualityStockDragonTigerClick(row, column) {
      const selectData = this.selectData;
      this.$refs.tbStockDragonTigerList.clearSelection();
      if (selectData != undefined && selectData.length == 1) {
        selectData.forEach(item => {
          // 判断 如果当前的一行被勾选, 再次点击的时候就会取消选中
          if (item == row) {
            this.$refs.tbStockDragonTigerList.toggleRowSelection(row, false);
          }
          // 不然就让当前的一行勾选
          else {
            this.$refs.tbStockDragonTigerList.toggleRowSelection(row, true);
          }
        })
      } else {
        this.$refs.tbStockDragonTigerList.toggleRowSelection(row, true);
      }
    },
    stockDragonTigerInfo() {
      console.log(33);
    },
    getStockDragonTigerList() {
      console.log(1112);
    },
    initStockDragonTigerList() {
      this.loading = true;
      this.$Progress.start();
      stockDragonTiger.getStockDragonTigerList(this.stockDragonTigerParam).then(resp => {
        console.log(resp);
        if (resp) {
          this.stockDragonTigerList = resp.data;
          this.total = resp.total;
        }
        this.loading = false;
        this.$Progress.finish();
      });
    }
  },
}
</script>

<style scoped>

</style>