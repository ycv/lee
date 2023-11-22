import {postRequest} from "@/utils/api";


/**
 * 统计管理 -> 全量历史记录 -> 列表查询
 * @param params
 * @returns {Promise<AxiosResponse<any>>}
 */
export const getStockInfoRecord = params => {
    return postRequest('/market/getStockInfoRecord/', params);
};

let api = {
    getStockInfoRecord
};


export default api;




