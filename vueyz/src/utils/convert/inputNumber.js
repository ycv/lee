// 此方法用来实现将一个字符串通过replace方法，格式化为普通数字格式（包括正负整数、正负浮点数都支持）
export const inputNumber = val => {
    if (val === '-' || !val) return val
    if (val === '.') return ''
    // 下列代码中正则表达式的非捕获组（?<=）在IE浏览器中不支持，所以弃用
    // const reg1 = /[^\d|^\-|\^.]/g // 匹配所有非数字，非-，非.的字符
    // const reg2 = /(?<=[\.|\-])[^\d]/g // 匹配所有.和-字符后的非数字字符
    // const reg3 = /(?<=\.\d*)\./g // 匹配小数后的.
    // const reg4 = /(?<=\d)\-/g // 匹配-后面的非数字
    // return val.replace(reg1, '').replace(reg2, '').replace(reg3, '').replace(reg4, '')

    const reg1 = /[^\d|\-|\.]/g
    const reg2 = /(\d|\.)\-+/g
    const str = val.replace(reg1, '').replace(reg2, '$1')
    const pointArr = str.split('.')
    let value = ''
    if (pointArr.length > 1) {
        pointArr.forEach((item, index) => {
            value = value + item
            if (!index) {
                value = value + '.'
            }
        })
    } else {
        value = str
    }
    return value
}
