<template>
  <div style="border: 0px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">

    <el-row style="margin-top: 40px">
      <el-col :span="9">&nbsp;</el-col>
      <el-col :span="6" style="text-align: center;">
        <p style="font-size:20px;font-weight:bold;">Starting at:</p>
        <a style="margin-top:20px;font-size:38px;font-weight:bold;">
          <span class="dollars">{{ matter(price) }}&nbsp;</span>
        </a>
        <a>/&nbsp;day</a>
      </el-col>
      <el-col :span="9">&nbsp;</el-col>
    </el-row>

    <el-row style="margin-top: 40px">
      <el-col :span="9">&nbsp;</el-col>
      <el-col :span="6">
        <a style="color:rgb(147,153,147);">Coverage</a><br>
        <a style="font-size:20px;font-weight:bold;"><span class="dollars">{{ matter(money) }}</span></a><br>
        <div style="margin-top: 10px;">
          <el-slider
              v-model="money" @input="inputMoney"
              :min="1000" :max="3000000"
              :step="1000">
          </el-slider>
        </div>
      </el-col>
      <el-col :span="9">&nbsp;</el-col>
    </el-row>

    <el-row style="margin-top: 20px">
      <el-col :span="9">&nbsp;</el-col>
      <el-col :span="6">
        <a style="color:rgb(147,153,147);">Term length</a><br>
        <a style="font-size:20px;font-weight:bold;">{{ year }} years</a><br>
        <div style="margin-top: 10px;">
          <el-slider
              @input="inputYear"
              v-model="year"
              :min="10" :max="30"
              :step="5" show-stops>
          </el-slider>
        </div>
      </el-col>
      <el-col :span="9">&nbsp;</el-col>
    </el-row>


    <el-row style="margin-top: 30px">
      <el-col :span="10">&nbsp;</el-col>
      <el-col :span="4">
        <el-button type="danger" round style="width: 100%;font-weight:bold;" @click="person">get my final rate
        </el-button>
      </el-col>
      <el-col :span="10">&nbsp;</el-col>
    </el-row>

  </div>
</template>


<script>
export default {
  name: "quote",
  data() {
    return {
      money: 1000,
      year: 10,
      price: 0,
      ratioObj: {
        10: 1.0,
        15: 1.25,
        20: 1.5,
        25: 1.75,
        30: 2
      }
    }
  },
  mounted() {
    this.initData();
  },

  methods: {
    initData() {
      this.getPrice(this.money, this.year);
    },
    inputMoney(v) {
      this.getPrice(v, this.year);
    },
    inputYear(y) {
      this.getPrice(this.money, y);
    },
    getPrice(p, y) {
      let that = this;
      console.log(p + " * " + that.ratioObj[y] + " / " + y + " / 365");
      let priceTemp = p * that.ratioObj[y] / y / 365;
      that.price = priceTemp.toFixed(2);
      console.log(that.price);
    },

    person() {
      this.$router.push({name: "测试数据"});
    },

    matter(value) {
      if (!value) return 0;
      const intPart = Math.trunc(value)
      const intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
      let floatPart = ''
      const valueArray = value.toString().split('.');
      if (valueArray.length === 2) {
        floatPart = valueArray[1].toString();
        return intPartFormat + '.' + floatPart;
      }
      return intPartFormat + floatPart;
    }
  }
}
</script>

<style scoped>
.dollars:before {
  content: '$';
}
</style>