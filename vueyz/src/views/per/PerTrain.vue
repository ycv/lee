<template>
  <div>
    <!--搜索区域-->
    <div class="reportForm-search-input">
      <div>
        <el-input placeholder="输入报表名称、编码搜索,直接回车搜索......" prefix-icon="el-icon-search"
                  clearable @clear="initForms" v-model="keyword"
                  @keydown.enter.native="initForms"></el-input>
        <el-button icon="el-icon-search" type="primary" @click="initForms">
          搜索
        </el-button>
      </div>
      <div>
        <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">
          添加报表
        </el-button>
      </div>
    </div>

    <!--列表区域-->
    <div style="margin-top: 10px">
      <el-table
          :data="reportList" :row-class-name="tableRowClassName"
          highlight-current-row border
          v-loading="loading"
          element-loading-text="正在加载..."
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
          style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="name"
            fixed
            align="center"
            label="报表名称"
            width="120">
        </el-table-column>

        <el-table-column
            prop="code"
            fixed
            align="center"
            label="报表编码"
            width="90">
        </el-table-column>

        <el-table-column
            prop="type"
            fixed
            align="center"
            label="报表类型"
            width="90">
        </el-table-column>

        <el-table-column
            prop="period"
            label="报表周期"
            align="center"
            width="90">
        </el-table-column>

        <el-table-column
            prop="wedlock"
            width="90" align="center"
            label="婚姻状况">
        </el-table-column>


        <el-table-column
            prop="email"
            width="180"
            align="center"
            label="电子邮件">
        </el-table-column>

        <el-table-column
            prop="endContract"
            align="center"
            label="报表终止日期"
            width="120">
        </el-table-column>

        <el-table-column
            prop="state"
            width="90" align="center"
            label="报表状态">
        </el-table-column>

        <el-table-column
            prop="remark"
            align="center"
            label="备注"
            width="120">
        </el-table-column>

        <el-table-column
            fixed="right"
            width="200"
            label="操作">
          <template slot-scope="scope">
            <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>
            <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>
            <el-button @click="deleteEmp(scope.row)" style="padding: 3px" size="mini" type="danger">删除
            </el-button>
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

    <!--弹框区域-->
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="80%">
      <div>
        <el-form :model="reportForm" :rules="rules" ref="formData">
          <!--          row组件的type="flex"启动flex布局，再通过row组件的justify属性调整排版方式，属性值分别有:-->
          <!--          justify=center 居中对齐     justify=start 左对齐     justify=end 右对齐-->
          <!--          justify=space-between 空格间距在中间对齐     justify=space-around 左右各占半格空格对齐-->
          <el-row :gutter="24" type="flex" justify="center" align="middle">
            <el-col :span="8">
              <el-form-item label="报表名称:" prop="name">
                <!--                style="width: 150px"-->
                <el-input size="mini" prefix-icon="el-icon-edit" v-model="reportForm.name"
                          placeholder="请输入报表名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="报表周期:" prop="period">
                <el-radio-group v-model="reportForm.period">
                  <el-radio label="1">年</el-radio>
                  <el-radio label="2">月</el-radio>
                  <el-radio label="3">日</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="电子邮箱:" prop="email">
                <!--                style="width: 150px"-->
                <el-input size="mini" prefix-icon="el-icon-message"
                          v-model="reportForm.email" placeholder="请输入电子邮箱"></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="24" type="flex" justify="center" align="middle">
            <el-col :span="8">
              <el-form-item label="婚姻状况:" prop="wedlock">
                <el-radio-group v-model="reportForm.wedlock">
                  <el-radio label="已婚">已婚</el-radio>
                  <el-radio label="未婚">未婚</el-radio>
                  <el-radio label="离异">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="8">

            </el-col>
            <el-col :span="8">
              <el-form-item label="报表终止日期:" prop="endContract">
                <!--                style="width: 150px;"-->
                <el-date-picker
                    v-model="reportForm.endContract"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="合同终止日期">
                </el-date-picker>
              </el-form-item>

            </el-col>
          </el-row>

          <el-row :gutter="24" type="flex" justify="center" align="middle">
            <el-col :span="8">
              <el-upload
                  accept=".xlsx,.XLSX"
                  :show-file-list="false"
                  :before-upload="beforeUpload"
                  :on-progress="onProgress"
                  :on-success="onSuccess"
                  :on-change="onChange"
                  :on-error="onError"
                  :limit="1"
                  :disabled="importDataDisabled"
                  style="display: inline-flex;"
                  action="/per/train/import">
                <el-button :disabled="importDataDisabled" type="success" :icon="importDataBtnIcon">
                  {{ importDataBtnText }}
                </el-button>
              </el-upload>
            </el-col>

            <el-col :span="16">
              <el-form-item label="备注:" prop="remark">
                <el-input size="small" prefix-icon="el-icon-edit" v-model="reportForm.remark"
                          placeholder="备注"></el-input>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row :gutter="24" type="flex" justify="center" align="middle">

          </el-row>

        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAddReportForm">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import {mymessage} from "@/utils/mymessage";
import LuckyExcel from "luckyexcel"

export default {
  name: "PerTrain",
  mounted() {
    this.initForms();
  },
  created() {
  },
  data() {
    return {
      importDataBtnText: '导入报表',
      importDataBtnIcon: 'el-icon-upload2',
      importDataDisabled: false,
      keyword: '',
      title: '',
      reportList: [],
      loading: false,
      total: 0,
      page: 1,

      excelBinary: '',
      reportForm: {
        name: "",//报表名称
        code: "",//报表编码
        period: "",//报表周期
        email: "",//邮箱
        wedlock: "已婚",//婚姻状况
        remark: "",//备注
        endContract: "2019-12-31"//报表终止日期
      },
      rules: {
        name: [{required: true, message: '请输入报表名称', trigger: 'blur'}],
        period: [{required: true, message: '请输入报表周期', trigger: 'blur'}],
        wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
        email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
          type: 'email',
          message: '邮箱格式不正确',
          trigger: 'blur'
        }],
        endContract: [{required: true, message: '请输入报表终止日期', trigger: 'blur'}]
      },
      dialogVisible: false
    }
  },
  methods: {
    onError(err, file, fileList) {
      this.importDataBtnText = '导入报表';
      this.importDataBtnIcon = 'el-icon-upload2';
      this.importDataDisabled = false;
      console.log("onError333333")
    },
    onChange() {

    },
    onSuccess(response, file, fileList) {
      this.importDataBtnText = '导入报表';
      this.importDataBtnIcon = 'el-icon-upload2';
      this.importDataDisabled = false;
      console.log(111222)
      console.log(response)
    },
    onProgress(event, file, fileList) {
      // console.log("onProgress6661116")
    },
    beforeUpload(file) {
      this.importDataBtnText = '正在导入';
      this.importDataBtnIcon = 'el-icon-loading';
      this.importDataDisabled = true;
      this.excelBinary = '';

      let fileSuffix = file.name.substring(file.name.lastIndexOf('.') + 1);
      let whiteList = ['xls', 'XLS', 'xlsx', 'XLSX'];
      let isSuffix = whiteList.indexOf(fileSuffix.toLowerCase()) === -1;
      let isLt10M = file.size / 1024 / 1024 > 1;//10 1

      if (isSuffix) {
        mymessage.error("上传文件只能是 xls、xlsx 格式 ！");
        this.importDataBtnText = '导入报表';
        this.importDataBtnIcon = 'el-icon-upload2';
        this.importDataDisabled = false;
        return false;
      }
      if (isLt10M) {
        mymessage.error("上传文件大小不能超过 10MB ！");
        this.importDataBtnText = '导入报表';
        this.importDataBtnIcon = 'el-icon-upload2';
        this.importDataDisabled = false;
        return false;
      }

      LuckyExcel.transformExcelToLucky(file, (exportJson, luckysheetfile) => {
        if (exportJson.sheets === null || exportJson.sheets.length === 0) {
          mymessage.error("无法读取excel文件的内容 ！");
          return false;
        }
        this.excelBinary = luckysheetfile;
      });
      console.log("beforeUpload3311S3")
      return !isSuffix && !isLt10M;
    },
    initForms(type) {
      this.loading = true;
      this.loading = false;

      console.log(1122);
    },
    tableRowClassName({row, rowIndex}) {
      if (rowIndex % 2 === 0) {
        return 'warning-row';
      } else {
        return 'success-row';
      }
    },

    sizeChange(currentSize) {
      this.size = currentSize;
      this.initForms();
    },
    currentChange(currentPage) {
      this.page = currentPage;
      this.initForms('advanced');
    },
    emptyEmp() {
      this.reportForm = {
        name: "",
        period: ""
      }
      this.inputDepName = '';
    },
    showAddEmpView() {
      this.emptyEmp();
      this.title = '添加员工';
      this.dialogVisible = true;
    },
    showEditEmpView(data) {
      this.title = '编辑员工信息';
      this.reportForm = data;
      this.dialogVisible = true;
    },
    deleteEmp(data) {

    },
    doAddReportForm() {
      this.$refs['formData'].validate(valid => {
        console.log(112);
        if (valid) {
          console.log(9691);
          this.postRequest("/per/train/save", this.reportForm).then(resp => {
            console.log(334411);
            console.log(resp);
            if (resp) {
              // this.dialogVisible = false;
              this.initForms();
            }
          });
        }
      });
    }
  }
}
</script>

<style scoped>
.el-table .warning-row {
  background: #efdaa9;
}

.el-table .success-row {
  background: #bee8a7;
}

.reportForm-search-input {
  display: flex;
  justify-content: space-between
}

.reportForm-search-input .el-input {
  width: 350px;
  margin-right: 10px;
}

.reportForm-search-input .el-input__inner::placeholder {
  color: #0e3ae7 !important;
  text-align: center;
}


</style>