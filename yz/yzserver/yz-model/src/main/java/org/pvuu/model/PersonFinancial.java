package org.pvuu.model;

public class PersonFinancial extends AudiTableInfo {
    private Integer id;
    private String yearIncome;
    private String personWorth;
    private String householdIncome;
    private String householdWorth;

    private String createTime;
    private String updateTime;
    private Integer pId;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(String yearIncome) {
        this.yearIncome = yearIncome;
    }

    public String getPersonWorth() {
        return personWorth;
    }

    public void setPersonWorth(String personWorth) {
        this.personWorth = personWorth;
    }

    public String getHouseholdIncome() {
        return householdIncome;
    }

    public void setHouseholdIncome(String householdIncome) {
        this.householdIncome = householdIncome;
    }

    public String getHouseholdWorth() {
        return householdWorth;
    }

    public void setHouseholdWorth(String householdWorth) {
        this.householdWorth = householdWorth;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
