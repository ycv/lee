<template>
  <div style="border: 0px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
    <el-form :model="person" :rules="rules" ref="demoForm">
      <el-row style="margin-top: 40px">
        <el-col :span="8">&nbsp;</el-col>
        <el-col :span="3">
          First Name<br>
          <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input First Name" clearable
                    v-model="person.firstName" style="width: 100%;"></el-input>
        </el-col>
        <el-col :span="2">&nbsp;</el-col>
        <el-col :span="3">
          Last Name<br>
          <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Last Name" clearable
                    v-model="person.lastName" style="width: 100%;"></el-input>
        </el-col>
        <el-col :span="8">&nbsp;</el-col>
      </el-row>

      <el-row style="margin-top: 10px">
        <el-col :span="8">&nbsp;</el-col>
        <el-col :span="3">
          Gender<br>
          <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Gender" clearable
                     v-model="person.genderValue" style="width: 100%;">
            <el-option
                v-for="item in genderArr"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="2">&nbsp;</el-col>
        <el-col :span="3">
          ZIP Code<br>
          <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input ZIP Code" clearable
                    v-model="person.zipCode" style="width: 100%;"></el-input>
        </el-col>
        <el-col :span="8">&nbsp;</el-col>
      </el-row>

      <el-row style="margin-top: 10px">
        <el-col :span="8">&nbsp;</el-col>
        <el-col :span="8">
          Date of Birth<br>
          <el-date-picker
              v-model="person.birth" placeholder="please select Birth" clearable
              size="mini"
              type="date"
              value-format="yyyy-MM-dd"
              style="width: 100%; cursor: pointer; ">
          </el-date-picker>
        </el-col>
        <el-col :span="8">&nbsp;</el-col>
      </el-row>

      <el-row style="margin-top: 10px">
        <el-col :span="8">&nbsp;</el-col>
        <el-col :span="8">
          Phone Number<br>
          <el-form-item label="" prop="phoneNumber">
            <el-input size="mini" prefix-icon="el-icon-phone" placeholder="please input Phone Number" clearable
                      v-model="person.phoneNumber" style="width: 100%;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">&nbsp;</el-col>
      </el-row>

      <el-row style="margin-top: 0px">
        <el-col :span="8">&nbsp;</el-col>
        <el-col :span="8">
          Email<br>
          <el-form-item label="" prop="email">
            <el-input size="mini" prefix-icon="el-icon-message" placeholder="please input Email" clearable
                      v-model="person.email" style="width: 100%;"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">&nbsp;</el-col>
      </el-row>


      <el-row style="margin-top: 30px">
        <el-col :span="10">&nbsp;</el-col>
        <el-col :span="4">
          <el-button type="danger" round style="width: 100%;font-weight:bold;" @click="quote">GET MY FREE QUOTE
          </el-button>
        </el-col>
        <el-col :span="10">&nbsp;</el-col>
      </el-row>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "demoInput",
  data() {
    return {
      person: {
        firstName: '', lastName: '', genderValue: '', zipCode: '', birth: '', phoneNumber: '', email: ''
      },

      genderArr: [{id: 1, name: 'Male'}, {id: 2, name: 'Female'}],
      rules: {
        email: [{
          type: 'email',
          message: '邮箱格式不正确',
          trigger: 'blur'
        }],
        phoneNumber: [{
          pattern: /^1[3,4,5,6,7,8,9][0-9]{9}$/,
          message: '手机格式不正确',
          trigger: 'blur'
        }],
      }


    }
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
    },
    quote() {
      this.$router.push({name: "测试报价", params: {code: "abc002"}});
    },
  }
}
</script>

<style scoped>

</style>