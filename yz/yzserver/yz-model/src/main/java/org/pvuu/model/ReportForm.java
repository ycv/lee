package org.pvuu.model;

public class ReportForm extends AudiTableInfo {
    private static final long serialVersionUID = 8938999202306081106L;

    private Integer id;

    // 报表编码
    private String code;
    // 报表名称
    private String name;
    // 报表类型
    private Integer type;
    // 报表周期 1:年 2:月 3:日
    private Integer period;

    // 邮箱
    private String email;
    // 婚姻状况
    private String wedlock;
    // 报表终止日期
    private String endContract;
    // 文件地址
    private String fileAddress;
    // excel二进制数据
    private String excelBinary;
    // 报表状态
    private Integer state;
    // 备注
    private String remark;
    // 创建人
    private String creator;
    // 创建时间
    private String createTime;
    // 修改人
    private String updater;
    // 修改时间
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWedlock() {
        return wedlock;
    }

    public void setWedlock(String wedlock) {
        this.wedlock = wedlock;
    }

    public String getEndContract() {
        return endContract;
    }

    public void setEndContract(String endContract) {
        this.endContract = endContract;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getExcelBinary() {
        return excelBinary;
    }

    public void setExcelBinary(String excelBinary) {
        this.excelBinary = excelBinary;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
