package org.pvuu.controller.emp;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.pvuu.model.*;
import org.pvuu.service.*;
import org.pvuu.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EmployeeService employeeService;
    @Autowired
    NationService nationService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue = "10") Integer size, Employee employee, Date[] beginDateScope) {
        logger.warn("getEmployeeByPageff ： " + JSON.toJSONString("aaas试试33"));

        return employeeService.getEmployeeByPage(page, size, employee, beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {

        logger.warn("employee ： " + JSON.toJSONString(employee));
        if (employeeService.addEmp(employee) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmpByEid(@PathVariable Integer id) {
        logger.warn("deleteEmpByEid ： " + JSON.toJSONString(id));
        if (employeeService.deleteEmpByEid(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.getAllNations();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.getAllPoliticsstatus();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.getAllJobLevels();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionService.getAllPositions();
    }

    @GetMapping("/maxWorkID")
    public RespBean maxWorkID() {
        RespBean respBean =
            RespBean.build().setStatus(200).setObj(String.format("%08d", employeeService.maxWorkID() + 1));
        return respBean;
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<Employee> list =
            (List<Employee>)employeeService.getEmployeeByPage(null, null, new Employee(), null).getData();
        return POIUtils.employee2Excel(list);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file) throws IOException {
        List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNations(),
            politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentsWithOutChildren(),
            positionService.getAllPositions(), jobLevelService.getAllJobLevels());
        if (employeeService.addEmps(list) == list.size()) {
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }
}