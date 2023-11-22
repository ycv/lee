<template>
  <div class="editDialog">

    <el-button type="primary" icon="el-icon-paperclip" size="mini" @click="showEditDialog(userId)">AAA</el-button>

    <el-dialog
        title="修改用户"
        :visible.sync="editDialogVisible"
        style="width:48%;left:2%;"
        @close="editDialogClose">
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>


  </div>
</template>


<script>
export default {
  name: "StockKdjData",
  props: {
    userId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      editDialogVisible: false
    }
  },
  methods: {
    showEditDialog(id) {
      this.editDialogVisible = true;
      console.log("idid ");
      console.log(id);
    },
    editDialogClose() {
      console.log("idid editDialogClose ");
      // this.$refs.editUserFormRef.resetFields();
    },
    editUserInfo() {
      // 监听子组件发射的事件
      this.$emit('kdj-list');
    }
  }
}
</script>


<style scoped>
.editDialog {
  display: inline-block;
  margin-right: 5px;
}
</style>