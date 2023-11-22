package org.pvuu.mapper;

import java.util.List;

import org.pvuu.model.Person;
import org.pvuu.model.PersonBeneficiary;
import org.pvuu.model.PersonEmployment;
import org.pvuu.model.PersonFinancial;

public interface PersonMapper {

    // 人员基本信息
    int insertPerson(Person person);

    // Financial Information 财务状况
    int insertPersonFinancial(PersonFinancial personFinancial);

    // Employment Information 工作信息
    int insertPersonEmployment(PersonEmployment personEmployment);

    // Beneficiary Information 受益人信息
    int insertPersonBeneficiaryBatch(List<PersonBeneficiary> list);
}
