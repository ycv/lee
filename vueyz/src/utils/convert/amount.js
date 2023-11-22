export const formatArea = (num, precision = 2) => {
    if (precision < 0 || precision > 20) precision = 2
    if (parseFloat(num).toString() != "NaN") {
        // num = parseFloat((num + "").replace(/[^\d\.-]/g, "")).toFixed(precision) + ""
        num = parseFloat((num + "").replace(/[^\d\.-]/g, "")) + ""
        let left = num.split(".")[0].split("").reverse()
        let right = num.split(".")[1]
        right = right ? right + '0'.repeat(20) : '0'.repeat(20) // 禁止小数位进位直接截取
        right = right.substring(0, precision)

        let total = ""
        for (let i = 0; i < left.length; i++) {
            total += left[i] + ((i + 1) % 3 == 0 && (i + 1) != left.length ? "," : "")
        }

        let leftPart = total.split("").reverse().join("").replace(/-,/, '-')
        if (right) {
            return leftPart + "." + right
        }
        return leftPart
    }
    return ''
}

export const parseArea = (str) => {
    return parseFloat(str.replace(/[^\d\.-]/g, ""))
}

