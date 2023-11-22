import {getRequest} from "@/utils/api";


export const getStockInfo = params => {
    return getRequest('/market/getStockInfo/');

};

let api = {
    getStockInfo
};


export default api;




