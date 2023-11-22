package org.pvuu.model;

import java.util.List;

public class Person extends AudiTableInfo {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;

    private String phone;
    private String address;
    private String genderValue;
    private String birth;
    private String email;

    private String ssnNo;
    private String visaType;
    private String country;
    private String visaExp;
    private String passportNo;

    private String driverNo;
    private String driverExp;
    private String travelExp;

    private String createTime;
    private String updateTime;

    private PersonEmployment personEmployment;
    private PersonFinancial personFinancial;
    private List<PersonBeneficiary> personBeneficiary;

    public PersonEmployment getPersonEmployment() {
        return personEmployment;
    }

    public void setPersonEmployment(PersonEmployment personEmployment) {
        this.personEmployment = personEmployment;
    }

    public PersonFinancial getPersonFinancial() {
        return personFinancial;
    }

    public void setPersonFinancial(PersonFinancial personFinancial) {
        this.personFinancial = personFinancial;
    }

    public List<PersonBeneficiary> getPersonBeneficiary() {
        return personBeneficiary;
    }

    public void setPersonBeneficiary(List<PersonBeneficiary> personBeneficiary) {
        this.personBeneficiary = personBeneficiary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsnNo() {
        return ssnNo;
    }

    public void setSsnNo(String ssnNo) {
        this.ssnNo = ssnNo;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVisaExp() {
        return visaExp;
    }

    public void setVisaExp(String visaExp) {
        this.visaExp = visaExp;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getDriverNo() {
        return driverNo;
    }

    public void setDriverNo(String driverNo) {
        this.driverNo = driverNo;
    }

    public String getDriverExp() {
        return driverExp;
    }

    public void setDriverExp(String driverExp) {
        this.driverExp = driverExp;
    }

    public String getTravelExp() {
        return travelExp;
    }

    public void setTravelExp(String travelExp) {
        this.travelExp = travelExp;
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
