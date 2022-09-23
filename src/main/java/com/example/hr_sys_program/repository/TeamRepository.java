package com.example.hr_sys_program.repository;

import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.repository.querydsl.TeamRepositoryQuerydslCutom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>, TeamRepositoryQuerydslCutom {

    Optional<Team> findByPartName(PartName partName); //팀 부서명을 기준으로 해당 팀 객체를 조회

}
