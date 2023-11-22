<template>
  <el-input
      v-model="inputValue"
      v-bind="$attrs"
      :maxlength="maxlength"
      @input="handleInput"
      @focus="handleFocus"
      @blur="handleBlur"
      @change="handleChange"
  >
    <template slot="append">
      <slot name="append"></slot>
    </template>
  </el-input>
</template>

<script>
import {inputNumber} from '@/utils/convert/inputNumber'
import {formatMoney} from '@/utils/convert/formatMoney'

export default {
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
      inputValue: '',
      inputing: false
    }
  },
  computed: {
    integerNum() {
      return Number(this.format.split('-')[0])
    },
    decimalNum() {
      return Number(this.format.split('-')[1])
    },
    maxlength() {
      return this.integerNum + (this.decimalNum > 0 ? this.decimalNum + 1 : this.decimalNum)
    }
  },
  watch: {
    value: {
      immediate: true,
      handler(n) {
        if (!n) {
          this.inputValue = n
          return
        }
        if (this.inputing) {
          this.inputValue = n
        } else {
          this.inputValue = formatMoney(inputNumber(n.toString()), this.format)
        }
      }
    }
  },
  methods: {
    handleInput(val) {
      this.inputing = true
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
      this.inputValue = money
      let value = ''
      if (money === '-' || money === '.') {
        value = ''
      } else if (money !== '') {
        value = Number(inputNumber(money))
      }
      this.$emit('input', value)
    },
    handleChange(val) {
      this.inputing = true
      this.$emit('change', Number(val.replaceAll(',', '')))
    },
    handleBlur(e) {
      this.inputing = false
      this.inputValue = formatMoney(inputNumber(e.target.value), this.format)
      this.$emit('blur', e)
    },
    handleFocus(e) {
      this.inputing = true
      this.inputValue = inputNumber(this.inputValue)
      this.$emit('focus', e)
    }
  }
}
</script>
