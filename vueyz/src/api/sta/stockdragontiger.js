import {postRequest} from "@/utils/api";


/**
 * 统计管理 -> 龙虎榜单 -> 龙虎榜列表数据
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getStockDragonTigerList = params => {
    return postRequest('/market/getStockDragonTigerList/', params);
};

let api = {
    getStockDragonTigerList
};


export default api;


