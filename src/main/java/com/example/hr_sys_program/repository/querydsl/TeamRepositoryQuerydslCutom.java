package com.example.hr_sys_program.repository.querydsl;

import com.example.hr_sys_program.domain.Team;

public interface TeamRepositoryQuerydslCutom {

    Team checkDuplicationTeam(Team team);   //팀을 생성하기 이전에 존재하는지 중복검사하는 용도의 메서드
}
