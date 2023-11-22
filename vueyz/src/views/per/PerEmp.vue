<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <div>
        <el-button @click="show2 = !show2" icon="el-icon-loading">Click Me</el-button>
        <el-button @click="show3 = !show3" icon="el-icon-view">Click Me</el-button>
      </div>
      <div>
        <stock-kdj-data :user-id="tta" @kdj-list="updateKdjList"/>
        <el-button icon="el-icon-refresh" type="success" @click="srcPage">页面跳转1</el-button>
      </div>
    </div>


    <div style="margin-top: 6px">
      <div style="display: flex; margin-top: 20px; height: 100px;">
        <transition name="el-zoom-in-center">
          <div v-show="show2" class="transition-box">.el-zoom-in-center</div>
        </transition>

        <transition name="el-zoom-in-top">
          <div v-show="show2" class="transition-box">.el-zoom-in-top</div>
        </transition>

        <transition name="el-zoom-in-bottom">
          <div v-show="show2" class="transition-box">.el-zoom-in-bottom</div>
        </transition>
      </div>

      <div style="margin-top: 20px; height: 200px;">
        <el-collapse-transition>
          <div v-show="show3">
            <div class="transition-box">el-collapse-transition</div>
            <div class="transition-box">el-collapse-transition</div>
          </div>
        </el-collapse-transition>
      </div>
    </div>
  </div>
</template>

<script>


import StockKdjData from "@/components/stock/dialog/StockKdjData";

export default {
  name: "PerEmp",
  components: {
    StockKdjData
  },
  data() {
    return {
      show2: true,
      show3: true,
      tta: 1233
    }
  },
  created() {
  },
  mounted() {
    this.getParams();
  },
  methods: {
    getParams() {
      console.log("Test1Test1B ");
      if (Object.getOwnPropertyNames(this.$route.query).length > 0 && this.$route.query.hasOwnProperty('code')) {
        console.log(this.$route.query);
      }
    },

    srcPage() {
      console.log("Test1Test1a");
      this.$router.push({name: "员工奖惩23", params: {code: "abc002"}});
    },

    updateKdjList() {
      console.log("pp_updateKdjList");
    }
  }
}
</script>

<style scoped>

.transition-box {
  margin-bottom: 10px;
  width: 200px;
  height: 100px;
  border-radius: 4px;
  background-color: #409EFF;
  text-align: center;
  color: #fff;
  padding: 40px 20px;
  box-sizing: border-box;
  margin-right: 20px;
}

</style>