/**
 * 格式化金额
 * @param money { String / Number } 金额
 * @param format { String } a-b:限制输入的字符长度，a:整数长度，b:小数长度
 * @returns {string|null}
 */
export const formatMoney = (money, format) => {
    if (typeof money === 'number') {
        money = money.toString()
    }
    if (money === '-' || !money) return money
    if (!format) format = '11-2'
    const intNum = Number(format.split('-')[0])
    const decimalNum = Number(format.split('-')[1])
    const moneyArr = money.split('.')
    moneyArr[0] = moneyArr[0].length > intNum ? moneyArr[0].substr(0, intNum) : moneyArr[0]
    if (moneyArr[1]) {
        moneyArr[1] = moneyArr[1].length > decimalNum ? moneyArr[1].substr(0, decimalNum) : moneyArr[1]
    }
    money = moneyArr.join('.')
    const isNegativeNum = money.startsWith('-')
    const pointPosition = money.indexOf('.')
    const decimal = pointPosition !== -1 ? money.substr(pointPosition) : ''
    const integer = Math.abs(parseInt(money).toString()).toString()
    const integerArrReverse = integer.split('').reverse().join('')
    const moneyStringify = `${isNegativeNum ? '-' : ''}${integerArrReverse.replace(/(\d{3})(?=\d)/g, '$1,').split('').reverse().join('')}${decimal}`
    return moneyStringify
}
