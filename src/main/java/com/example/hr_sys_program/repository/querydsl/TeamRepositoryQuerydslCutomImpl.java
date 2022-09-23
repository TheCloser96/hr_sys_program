package com.example.hr_sys_program.repository.querydsl;

import com.example.hr_sys_program.domain.QTeam;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.example.hr_sys_program.domain.QTeam.team;

public class TeamRepositoryQuerydslCutomImpl implements TeamRepositoryQuerydslCutom{

    private final JPAQueryFactory queryFactory;


    public TeamRepositoryQuerydslCutomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }   //생성자 주입


    @Override
    public Team checkDuplicationTeam(Team team) {
        Team result = queryFactory
                .selectFrom(QTeam.team)
                .where(QTeam.team.partName.eq(team.getPartName()))
                .fetchOne();

        return result;
    }   //팀을 생성하기 이전에 존재하는지 중복검사하는 용도의 메서드
}
