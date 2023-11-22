package org.pvuu.controller.per;

import java.io.IOException;

import org.pvuu.model.ReportForm;
import org.pvuu.model.RespBean;
import org.pvuu.service.ReportFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/per/train")
public class PerTrainController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    ReportFormService reportFormService;

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        // List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNations(),
        // politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentsWithOutChildren(),
        // positionService.getAllPositions(), jobLevelService.getAllJobLevels());
        // if (employeeService.addEmps(list) == list.size()) {
        // return RespBean.ok("上传成功");
        // }
        // public RespBean updateHrPasswd(@RequestBody Map<String, Object> info) {
        logger.warn("demoSimpleDate002 : " + JSON.toJSONString(66699969));
        return RespBean.error("上传失败1212 69");
    }

    @PostMapping("/getExcelFileData")
    public RespBean getExcelFileData(MultipartFile file) throws IOException {
        // public RespBean getExcelFileData(@RequestBody Map<String, Object> info) {
        logger.warn("getExcelFileData222 : " + JSON.toJSONString(11122211));
        return RespBean.error("上传失败");
    }

    @PostMapping("/save")
    public RespBean addReportForm(@RequestBody ReportForm reportForm) {
        if (reportFormService.addReportData(reportForm) == 1) {
            logger.warn("addReportForm5556 : " + JSON.toJSONString(333));
            return RespBean.ok("添加成功!");
        }
        logger.warn("addReportForm5558 : " + JSON.toJSONString(4444));
        return RespBean.error("添加失败!");
    }

}
