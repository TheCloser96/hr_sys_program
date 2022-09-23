package com.example.hr_sys_program.repository;

import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.roletype.Position;
import com.example.hr_sys_program.repository.querydsl.MemberRepositoryQuerydslCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydslCustom {

}
