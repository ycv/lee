<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-input placeholder="请输名称进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                  clearable
                  @clear="initStockList"
                  style="width: 300px;margin-right: 10px" v-model="stockParam.name"
                  @keydown.enter.native="initStockList" :disabled="showStockSearchView"></el-input>
        <el-button icon="el-icon-search" type="primary" @click="initStockList" :disabled="showStockSearchView">
          搜索
        </el-button>
      </div>
      <div>
        <el-button icon="el-icon-refresh" type="success" @click="stockInfo">股票详情</el-button>
        <el-button icon="el-icon-plus" type="primary" @click="getStockList">添加用户</el-button>
      </div>
    </div>

    <div style="margin-top: 6px">
      <el-table :data="stockList" border stripe highlight-current-row
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                @row-click="qualityStockClick" ref="tbStockList" @select="selectStockInfo"
                @selection-change="handleStockSelectionChange"
                style="width: 100%">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column width="140" prop="name" fixed label="名称" align="center"></el-table-column>
        <el-table-column width="140" prop="code" label="代码" align="center"></el-table-column>
        <el-table-column width="140" label="市场" align="center">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.market == 'sh'">上海</el-tag>
            <el-tag type="danger" v-else-if="scope.row.market == 'sz'">深圳</el-tag>
            <el-tag type="info" v-else>{{ scope.row.market }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="200" prop="date" label="最近数据日期" align="center"></el-table-column>
        <el-table-column width="200" label="数据更新时间" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <el-tag>{{ scope.row.createTime }}</el-tag>
          </template>
        </el-table-column>


        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="showEditStockView(scope.row)">详情</el-button>
            <el-button type="danger" @click="deleteStock(scope.row)">删除</el-button>
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

import stockRsiApi from '@/api/sta/stockrsi';
import stockApi from '@/api/sta/stock';
import {mymessage} from "@/utils/mymessage";

export default {
  name: "StaAll",
  data() {
    return {
      showStockSearchView: false,
      total: 0,
      loading: false,
      stockParam: {
        name: "", date: "", code: "",
        page: 1, size: 10
      }, selectData: {},
      stockList: []
    }
  },
  created() {
    this.initStockList();
  },
  //mounted:在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作。
  mounted() {

  },
  methods: {
    // 表格的选中 可以获得当前选中的数据
    handleStockSelectionChange(val) {
      this.selectData = val[0];
    },
    selectStockInfo(selection, row) {
      // 清除 所有勾选项
      this.$refs.tbStockList.clearSelection();
      // 当表格数据都没有被勾选的时候 就返回  主要用于将当前勾选的表格状态清除
      if (selection.length == 0) return;
      this.$refs.tbStockList.toggleRowSelection(row, true);
    },
    //表格行内点击选中
    qualityStockClick(row, column) {
      const selectData = this.selectData;
      this.$refs.tbStockList.clearSelection();
      if (selectData != undefined && selectData.length == 1) {
        selectData.forEach(item => {
          // 判断 如果当前的一行被勾选, 再次点击的时候就会取消选中
          if (item == row) {
            this.$refs.tbStockList.toggleRowSelection(row, false);
          }
          // 不然就让当前的一行勾选
          else {
            this.$refs.tbStockList.toggleRowSelection(row, true);
          }
        })
      } else {
        this.$refs.tbStockList.toggleRowSelection(row, true);
      }
    },
    sizeChange(currentSize) {
      this.stockParam.size = currentSize;
      this.initStockList();
    },
    currentChange(currentPage) {
      this.stockParam.page = currentPage;
      this.initStockList();
    },
    showEditStockView(data) {
      console.log("111");
      console.log(data);
    },
    deleteStock(data) {
      this.$confirm('此操作将删除：【' + data.name + '】，是否继续？', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定'
      }).then(() => {
        console.log("1112");
        // this.deleteRequest("/salary/sob/" + data.id).then(resp => {
        //   if (resp) {
        //     this.initStockList();
        //   }
        // })
      }).catch(() => {
        mymessage.warning("取消删除 ！");
      })


    },
    initStockList() {
      this.loading = true;
      this.$Progress.start();
      stockRsiApi.getStockRsiList(this.stockParam).then(resp => {
        if (resp) {
          this.stockList = resp.data;
          this.total = resp.total;
        }
        this.loading = false;
        this.$Progress.finish();
      });
    },
    stockInfo() {
      this.$Progress.start();
      stockApi.getStockInfo().then(resp => {
        this.$Progress.finish();
      })
    },
    getStockList() {
      console.log(1112);
    }
  }
}
</script>

<style scoped>

</style>