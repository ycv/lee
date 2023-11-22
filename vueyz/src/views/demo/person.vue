<template>
  <div style="border: 0px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
    <el-form :model="person" :rules="rules" ref="personForm">

      <table width="70%" align="center" class="mt">
        <tr>
          <td colspan="4">
            <a style=" font-size:22px;font-weight:bold;">&nbsp; Insured Information 受保人个人信息</a>
          </td>
        </tr>
        <tr>
          <td width="25%">&nbsp;First Name 名字</td>
          <td width="25%">
            <el-input size="mini" style="width: 100%" prefix-icon="el-icon-edit" clearable
                      v-model="person.firstName" placeholder="please input First Name"></el-input>
          </td>
          <td width="25%">&nbsp;Phone No.电话</td>
          <td width="25%">
            <el-input size="mini" style="width: 100%" prefix-icon="el-icon-phone" clearable
                      v-model="person.phone" placeholder="please input Phone No"></el-input>
          </td>
        </tr>
        <tr>
          <td width="25%">&nbsp;Middle Name 名字</td>
          <td width="25%">
            <el-input size="mini" style="width: 100%;" prefix-icon="el-icon-edit" clearable
                      v-model="person.middleName" placeholder="please input Middle Name"></el-input>
          </td>
          <td width="50%" colspan="2">&nbsp;Residential Address 家庭住址</td>
        </tr>

        <tr>
          <td>&nbsp;Last Name 姓氏</td>
          <td>
            <el-input size="mini" style="width: 100%;" prefix-icon="el-icon-edit" clearable
                      v-model="person.lastName" placeholder="please input Last Name"></el-input>
          </td>
          <td colspan="2" rowspan="2">
            <el-input size="mini" style="width: 100%;" prefix-icon="el-icon-edit" clearable
                      type="textarea" :rows="3" v-model="person.address" placeholder="please input Address"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Sex 性别</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Gender" clearable
                       v-model="person.genderValue" style="width: 100%;">
              <el-option
                  v-for="item in genderArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
        </tr>

        <tr>
          <td>&nbsp;Date of Birth 生日</td>
          <td>
            <el-date-picker
                v-model="person.birth" placeholder="please select Birth" clearable
                size="mini"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 100%; cursor: pointer; ">
            </el-date-picker>
          </td>
          <td>&nbsp;E-mail 电子邮箱</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-message" placeholder="please input Email" clearable
                      v-model="person.email" style="width: 100%;"></el-input>
          </td>
        </tr>

        <tr>
          <td>&nbsp;SSN 社安号</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input SSN" clearable
                      v-model="person.ssnNo" style="width: 100%;"></el-input>
          </td>
          <td>&nbsp;Type of Visa 签证种类</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Type of Visa" clearable
                       v-model="person.visaType" style="width: 100%;">
              <el-option
                  v-for="item in visaTypeArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Country of Birth 国籍</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Country of Birth" clearable
                       v-model="person.country" style="width: 100%;">
              <el-option
                  v-for="item in countryArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
          <td>&nbsp;Visa Exp. Date 签证过期日期</td>
          <td>
            <el-date-picker
                v-model="person.visaExp" placeholder="please select Visa Exp. Date" clearable
                size="mini"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 100%; cursor: pointer; ">
            </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Passport No. 护照号</td>
          <td rowspan="2">
            <el-input size="mini" style="width: 100%;" prefix-icon="el-icon-edit" clearable
                      type="textarea" :rows="3" v-model="person.passportNo"
                      placeholder="please input Passport No"></el-input>
          </td>
          <td>&nbsp;Driver License 驾照号</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Driver License" clearable
                      v-model="person.driverNo" style="width: 100%;"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Permanent Residence Card No. <br>&nbsp;绿卡号</td>
          <td>&nbsp;Driver License Exp. Date <br>&nbsp;驾照过期日期</td>
          <td>
            <el-date-picker
                v-model="person.driverExp" placeholder="please select Driver License Exp. Date" clearable
                size="mini"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 100%; cursor: pointer; ">
            </el-date-picker>
          </td>
        </tr>
        <tr>
          <td colspan="4">&nbsp;During the next 2 years do you intend to travel or reside outside of the USA for more
            than 2 weeks in a year？ 是否会在未来两年内在美国境外旅游或居住超过两周以上？<br>
            &nbsp;&nbsp;<el-radio v-model="person.travelExp" label="1">是</el-radio>
            <el-radio v-model="person.travelExp" label="2">否</el-radio>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a style=" font-size:22px;font-weight:bold;">&nbsp; Employment Information 工作信息</a>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Employer Name 公司名字</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Employer Name" clearable
                      v-model="person.personEmployment.name" style="width: 100%;"></el-input>
          </td>
          <td colspan="2">&nbsp;Business Address 公司地址</td>
        </tr>
        <tr>
          <td>&nbsp;Industry 工作领域</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Industry" clearable
                       v-model="person.personEmployment.industry" style="width: 100%;">
              <el-option
                  v-for="item in industryArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
          <td rowspan="2" colspan="2">
            <el-input size="mini" style="width: 100%;" prefix-icon="el-icon-edit" clearable
                      type="textarea" :rows="3" v-model="person.personEmployment.address"
                      placeholder="please input Business Address"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Occupation Type 职业种类</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Occupation Type" clearable
                       v-model="person.personEmployment.occupation" style="width: 100%;">
              <el-option
                  v-for="item in occupationArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Job Title 工作职称</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Job Title" clearable
                      v-model="person.personEmployment.jobTitle" style="width: 100%;"></el-input>
          </td>
          <td>&nbsp;Years of Working Experience 工龄</td>
          <td>
            <el-input-number v-model="person.personEmployment.workingExperience" :min="0" :max="30"
                             style="width: 100%;"></el-input-number>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a style=" font-size:22px;font-weight:bold;">&nbsp; Financial Information 财务状况</a>
          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;Annual Income 个人年收入</td>
          <td colspan="2">
            <el-input
                v-model="person.personFinancial.yearIncome" prefix-icon="el-icon-edit"
                v-bind="$attrs" placeholder="please input Annual Income" clearable
                @input="handleInput" size="mini" style="width: 100%;"
                @focus="handleFocus"
                @blur="handleBlur"
                @change="handleChange"
            >
            </el-input>
          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;Net Worth 个人资产(房子、存款等)</td>
          <td colspan="2">
            <el-input
                v-model="person.personFinancial.personWorth" prefix-icon="el-icon-edit"
                v-bind="$attrs" placeholder="please input Net Worth" clearable
                @input="handleInput2"
                size="mini" style="width: 100%;"
                @blur="handleBlur2"
            >
            </el-input>

          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;Household Annual Income 家庭年收入</td>
          <td colspan="2">
            <el-input
                v-model="person.personFinancial.householdIncome" prefix-icon="el-icon-edit"
                v-bind="$attrs" placeholder="please input Household Annual Income" clearable
                @input="handleInput3"
                size="mini" style="width: 100%;"
                @blur="handleBlur3"
            >
            </el-input>
          </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;Household Net Worth 家庭总资产</td>
          <td colspan="2">
            <el-input
                v-model="person.personFinancial.householdWorth" prefix-icon="el-icon-edit"
                v-bind="$attrs" placeholder="please input Household Net Worth" clearable
                @input="handleInput4"
                size="mini" style="width: 100%;"
                @blur="handleBlur4"
            >
            </el-input>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <a style=" font-size:22px;font-weight:bold;">&nbsp; Beneficiary Information 受益人信息</a>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Relationship 关系</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Relationship" clearable
                       v-model="person.beneficiary1.relationship" style="width: 100%;">
              <el-option
                  v-for="item in relationshipArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
          <td>&nbsp;Relationship 关系</td>
          <td>
            <el-select size="mini" prefix-icon="el-icon-edit" placeholder="please select Relationship" clearable
                       v-model="person.beneficiary2.relationship" style="width: 100%;">
              <el-option
                  v-for="item in relationshipArr"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </td>
        </tr>
        <tr>
          <td>&nbsp;First Name 名字</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input First Name" clearable
                      v-model="person.beneficiary1.firstName" style="width: 100%;"></el-input>
          </td>
          <td>&nbsp;First Name 名字</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input First Name" clearable
                      v-model="person.beneficiary2.firstName" style="width: 100%;"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Last Name 姓氏</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Last Name" clearable
                      v-model="person.beneficiary1.lastName" style="width: 100%;"></el-input>
          </td>
          <td>&nbsp;Last Name 姓氏</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input Last Name" clearable
                      v-model="person.beneficiary2.lastName" style="width: 100%;"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;SSN 社安号</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input SSN" clearable
                      v-model="person.beneficiary1.ssnNo" style="width: 100%;"></el-input>
          </td>
          <td>&nbsp;SSN 社安号</td>
          <td>
            <el-input size="mini" prefix-icon="el-icon-edit" placeholder="please input SSN" clearable
                      v-model="person.beneficiary2.ssnNo" style="width: 100%;"></el-input>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Date of Birth 生日</td>
          <td>
            <el-date-picker
                v-model="person.beneficiary1.birth" placeholder="please select Date of Birth" clearable
                size="mini"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 100%; cursor: pointer; ">
            </el-date-picker>
          </td>
          <td>&nbsp;Date of Birth 生日</td>
          <td>
            <el-date-picker
                v-model="person.beneficiary2.birth" placeholder="please select Date of Birth" clearable
                size="mini"
                type="date"
                value-format="yyyy-MM-dd"
                style="width: 100%; cursor: pointer; ">
            </el-date-picker>
          </td>
        </tr>
        <tr>
          <td>&nbsp;Share % 收益比例</td>
          <td>
            <el-input-number v-model="person.beneficiary1.share" :precision="2" :step="0.01" :min="0"
                             style="width: 100%; cursor: pointer; " :max="1">
            </el-input-number>
          </td>
          <td>&nbsp;Share % 收益比例</td>
          <td>
            <el-input-number v-model="person.beneficiary2.share" :precision="2" :step="0.01" :min="0"
                             style="width: 100%; cursor: pointer; " :max="1"></el-input-number>
          </td>
        </tr>
      </table>
    </el-form>
    <div style="text-align: center;margin-top: 20px;">
      <el-button>取 消</el-button>
      <el-button type="primary" @click="doAddPerson">确 定</el-button>
    </div>
  </div>
</template>


<script>

import {inputNumber} from '@/utils/convert/inputNumber'
import {formatMoney} from '@/utils/convert/formatMoney'

export default {
  name: "person",
  props: {
    value: {
      type: [String, Number],
      default: ''
    },
    // 金额位数格式（a-b）；a:整数位数；b:小数位数
    format: {
      type: String,
      default: '11-2'
    }
  },
  data() {
    return {
      person: {
        firstName: '', middleName: '', lastName: '',
        phone: '', address: '', genderValue: '', birth: '', email: '',
        ssnNo: '', visaType: '', country: '', visaExp: '', passportNo: '',
        driverNo: '', driverExp: '', travelExp: '1',


        //工作信息
        personEmployment: {
          name: '', //公司名字
          address: '',//公司地址
          industry: '',//工作领域
          occupation: '',//职业种类
          jobTitle: '',//工作职称
          workingExperience: '',// 工龄
        },
        //财务状况
        personFinancial: {
          yearIncome: '',//个人年收入
          personWorth: '',//个人资产(房子、存款等)
          householdIncome: '',//家庭年收入
          householdWorth: '',//家庭总资产
        },
        //受益人信息
        beneficiary1: {
          relationship: '',//关系
          firstName: '', lastName: '',
          ssnNo: '',//社安号
          birth: '',// 生日
          share: '',// 收益比例
        },
        beneficiary2: {
          relationship: '',
          firstName: '', lastName: '',
          ssnNo: '',
          birth: '', share: '',
        },
        personBeneficiary: []
      },
      genderArr: [{id: 1, name: 'Male'}, {id: 2, name: 'Female'}],
      visaTypeArr: [{id: 1, name: '普通签证'}, {id: 2, name: '外交签证'}, {id: 3, name: '公务签证'}],

      countryArr: [{id: 1, name: '中国'}, {id: 2, name: '美国'}, {id: 3, name: '日本'}, {id: 4, name: '韩国'}],


      industryArr: [{id: 1, name: '数据分析师'}, {id: 2, name: '软件测试'}, {id: 3, name: '市场营销'}, {id: 4, name: '教师'}],
      occupationArr: [{id: 1, name: '国家机关'}, {id: 2, name: '专业技术人员'}, {id: 3, name: '生活服务人员'}, {
        id: 4,
        name: '事业单位负责人'
      }],
      relationshipArr: [{id: 1, name: '父母'}, {id: 2, name: '子女'}],

      rules: {
        email: [{
          type: 'email',
          message: '邮箱格式不正确',
          trigger: 'blur'
        }],
        inputIng: false,
      }
    }
  },
  mounted() {
    this.initData();
  },

  methods: {
    initData() {

    },
    doAddPerson() {
      this.$refs['personForm'].validate(valid => {
        console.log(111331);
        console.log(this.person);
        this.person.personBeneficiary.push(this.person.beneficiary1);
        this.person.personBeneficiary.push(this.person.beneficiary2);

        console.log(2222222);
        console.log(this.person);

        this.postRequest("/demo/addPerson/", this.person).then(resp => {
          if (resp) {
            console.log(4444);
            console.log(resp);
          }
        })

      });


      console.log(111222);
    },
    handleInput(val) {
      this.inputIng = true
      let money = inputNumber(val)
      const format = this.format
      const intNum = Number(format.split('-')[0])
      const decimalNum = Number(format.split('-')[1])
      const moneyArr = money.split('.')
      moneyArr[0] = moneyArr[0].length > intNum ? moneyArr[0].substr(0, intNum) : moneyArr[0]
      if (moneyArr[1]) {
        moneyArr[1] = moneyArr[1].length > decimalNum ? moneyArr[1].substr(0, decimalNum) : moneyArr[1]
      }
      money = moneyArr.join('.')
      this.person.personFinancial.yearIncome = money
      let value = ''
      if (money === '-' || money === '.') {
        value = ''
      } else if (money !== '') {
        value = Number(inputNumber(money))
      }
      this.$emit('input', value)
    },
    handleInput2(val) {
      this.inputIng = true
      let money = inputNumber(val)
      const format = this.format
      const intNum = Number(format.split('-')[0])
      const decimalNum = Number(format.split('-')[1])
      const moneyArr = money.split('.')
      moneyArr[0] = moneyArr[0].length > intNum ? moneyArr[0].substr(0, intNum) : moneyArr[0]
      if (moneyArr[1]) {
        moneyArr[1] = moneyArr[1].length > decimalNum ? moneyArr[1].substr(0, decimalNum) : moneyArr[1]
      }
      money = moneyArr.join('.')
      this.person.personFinancial.personWorth = money
      let value = ''
      if (money === '-' || money === '.') {
        value = ''
      } else if (money !== '') {
        value = Number(inputNumber(money))
      }
      this.$emit('input', value)
    },
    handleInput3(val) {
      this.inputIng = true
      let money = inputNumber(val)
      const format = this.format
      const intNum = Number(format.split('-')[0])
      const decimalNum = Number(format.split('-')[1])
      const moneyArr = money.split('.')
      moneyArr[0] = moneyArr[0].length > intNum ? moneyArr[0].substr(0, intNum) : moneyArr[0]
      if (moneyArr[1]) {
        moneyArr[1] = moneyArr[1].length > decimalNum ? moneyArr[1].substr(0, decimalNum) : moneyArr[1]
      }
      money = moneyArr.join('.')
      this.person.personFinancial.householdIncome = money
      let value = ''
      if (money === '-' || money === '.') {
        value = ''
      } else if (money !== '') {
        value = Number(inputNumber(money))
      }
      this.$emit('input', value)
    },
    handleInput4(val) {
      this.inputIng = true
      let money = inputNumber(val)
      const format = this.format
      const intNum = Number(format.split('-')[0])
      const decimalNum = Number(format.split('-')[1])
      const moneyArr = money.split('.')
      moneyArr[0] = moneyArr[0].length > intNum ? moneyArr[0].substr(0, intNum) : moneyArr[0]
      if (moneyArr[1]) {
        moneyArr[1] = moneyArr[1].length > decimalNum ? moneyArr[1].substr(0, decimalNum) : moneyArr[1]
      }
      money = moneyArr.join('.')
      this.person.personFinancial.householdWorth = money
      let value = ''
      if (money === '-' || money === '.') {
        value = ''
      } else if (money !== '') {
        value = Number(inputNumber(money))
      }
      this.$emit('input', value)
    },
    handleChange(val) {
      this.inputIng = true
      this.$emit('change', Number(val.replaceAll(',', '')))
    },
    handleBlur(e) {
      this.inputIng = false
      this.person.personFinancial.yearIncome = formatMoney(inputNumber(e.target.value), this.format)
      this.$emit('blur', e)
    },
    handleBlur2(e) {
      this.inputIng = false
      this.person.personFinancial.personWorth = formatMoney(inputNumber(e.target.value), this.format)
      this.$emit('blur', e)
    },
    handleBlur3(e) {
      this.inputIng = false
      this.person.personFinancial.householdIncome = formatMoney(inputNumber(e.target.value), this.format)
      this.$emit('blur', e)
    },
    handleBlur4(e) {
      this.inputIng = false
      this.person.personFinancial.householdWorth = formatMoney(inputNumber(e.target.value), this.format)
      this.$emit('blur', e)
    },

    handleFocus(e) {
      this.inputIng = true
      this.person.personFinancial.yearIncome = inputNumber(this.person.personFinancial.yearIncome)
      this.$emit('focus', e)
    }


  }
}
</script>

<style scoped>
tr td {
  border: 1px solid #020203;
  height: 28px;
}

.mt {
  border-collapse: collapse;
}
</style>