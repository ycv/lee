package org.pvuu.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.pvuu.mapper.DemoMapper;
import org.pvuu.mapper.PersonMapper;
import org.pvuu.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

@Service
public class DemoService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    DemoMapper demoMapper;

    @Autowired
    PersonMapper personMapper;

    @Transactional
    public Integer addPerson(Person person) {
        // Financial Information 财务状况
        person.setCreateTime(df.format(System.currentTimeMillis()));
        person.setUpdateTime(df.format(System.currentTimeMillis()));
        logger.warn("personData  ： " + JSON.toJSONString(person));
        int personResult = personMapper.insertPerson(person);
        if (personResult == 1) {
            addPersonFinancial(person.getPersonFinancial(), person.getId());
            addPersonEmployment(person.getPersonEmployment(), person.getId());
            addPersonBeneficiary(person.getPersonBeneficiary(), person.getId());
        }
        return personResult;
    }

    // Financial Information 财务状况
    public Integer addPersonFinancial(PersonFinancial personFinancial, int Pid) {
        personFinancial.setYearIncome(personFinancial.getYearIncome().replaceAll(",", ""));
        personFinancial.setPersonWorth(personFinancial.getPersonWorth().replaceAll(",", ""));
        personFinancial.setHouseholdIncome(personFinancial.getHouseholdIncome().replaceAll(",", ""));
        personFinancial.setHouseholdWorth(personFinancial.getHouseholdWorth().replaceAll(",", ""));
        personFinancial.setpId(Pid);
        personFinancial.setCreateTime(df.format(System.currentTimeMillis()));
        personFinancial.setUpdateTime(df.format(System.currentTimeMillis()));
        return personMapper.insertPersonFinancial(personFinancial);
    }

    // Employment Information 工作信息
    public Integer addPersonEmployment(PersonEmployment personEmployment, int Pid) {
        personEmployment.setpId(Pid);
        personEmployment.setCreateTime(df.format(System.currentTimeMillis()));
        personEmployment.setUpdateTime(df.format(System.currentTimeMillis()));
        return personMapper.insertPersonEmployment(personEmployment);
    }

    // Beneficiary Information 受益人信息
    public void addPersonBeneficiary(List<PersonBeneficiary> personBeneficiary, int Pid) {
        if (!CollectionUtils.isEmpty(personBeneficiary)) {
            for (PersonBeneficiary p : personBeneficiary) {
                p.setCreateTime(df.format(System.currentTimeMillis()));
                p.setUpdateTime(df.format(System.currentTimeMillis()));
                p.setpId(Pid);
            }
            logger.warn("personBeneficiaryData ： " + JSON.toJSONString(personBeneficiary));
            personMapper.insertPersonBeneficiaryBatch(personBeneficiary);
        }
    }

    public List<Stock> getDemoListByCodeStr(String code) {
        return demoMapper.getDemoListByCode(code);
    }

    public List<Stock> getDemoListByCodeStr(List<String> codeList) {
        return demoMapper.getDemoListByCodeList(codeList);
    }
}
