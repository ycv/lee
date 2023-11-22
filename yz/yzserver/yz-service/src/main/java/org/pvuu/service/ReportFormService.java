package org.pvuu.service;

import java.text.SimpleDateFormat;
import java.util.UUID;

import org.pvuu.mapper.ReportFormMapper;
import org.pvuu.model.ReportForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class ReportFormService {

    @Autowired
    ReportFormMapper reportFormMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public Integer addReportData(ReportForm reportForm) {
        reportForm.setCode(UUID.randomUUID().toString());
        reportForm.setType(1);
        reportForm.setFileAddress("");
        reportForm.setExcelBinary("");
        reportForm.setState(1);
        reportForm.setCreateTime(dfs.format(System.currentTimeMillis()));
        reportForm.setUpdateTime(dfs.format(System.currentTimeMillis()));
        reportForm.setCreator("");
        reportForm.setUpdater("");
        logger.warn("addReportData0001 ： " + JSON.toJSONString(reportForm));
        return reportFormMapper.insertOne(reportForm);
    }
}
