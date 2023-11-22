import {getRequest, postRequest} from "@/utils/api";


/**
 * 统计管理 -> 股票RSI列表 -> 列表数据
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getStockRsiList = params => {
    return postRequest('/market/getStockRsiList/', params);
};

/**
 * 统计管理 -> 全量历史记录 -> 统计数据
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getActStockDate = params => {
    return getRequest('/market/getActStockDate/' + params);
};


export const getStockRsiDataByParam = params => {
    return postRequest('/market/getStockChartDataList/', params);
};


export const actionTest = params => {
    return getRequest('/market/getActStockDate/' + params);
};


let api = {
    getStockRsiDataByParam,
    actionTest,
    getStockRsiList,
    getActStockDate
};


export default api;




