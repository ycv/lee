import {postRequest} from "@/utils/api";


/**
 * 指标数据 -> kdj数据
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getStockChartDataList = params => {
    return postRequest('/market/getStockChartDataList/', params);
};

let api = {
    getStockChartDataList
};


export default api;


