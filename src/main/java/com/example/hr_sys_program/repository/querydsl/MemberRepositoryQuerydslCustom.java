package com.example.hr_sys_program.repository.querydsl;

import com.example.hr_sys_program.controller.dto.SearchKeywordsWithoutSalaryDto;
import com.example.hr_sys_program.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryQuerydslCustom {
    
    List<Member> searchByMemberName(String name);   //사원의 이름을 검색하여 해당 사원의 정보를 가져오는 메서드

    Page<Member> searchWithoutSalary(SearchKeywordsWithoutSalaryDto dto, Pageable pageable); //사원의 이름, 직위, 팀의 부서들을 종합해서 검색해 해당하는 사원의 정보를 가져오는 메서드

    Long updateMemberSalaryBulk(List<Long> membersId, int inputSalary);  //특정한 사원들의 연봉을 한번에 가감하는 메서드
}
