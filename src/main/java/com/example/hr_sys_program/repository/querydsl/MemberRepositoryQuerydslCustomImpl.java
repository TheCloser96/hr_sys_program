package com.example.hr_sys_program.repository.querydsl;

import com.example.hr_sys_program.controller.dto.SearchKeywordsWithoutSalaryDto;
import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.hr_sys_program.domain.QMember.member;
import static com.example.hr_sys_program.domain.QTeam.team;

public class MemberRepositoryQuerydslCustomImpl implements MemberRepositoryQuerydslCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryQuerydslCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }   //생성자 주입


    @Override
    public List<Member> searchByMemberName(String name) {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.name.eq(name))
                .fetch();

        return result;
    }   //사원의 이름을 검색하여 해당 사원의 정보를 가져오는 메서드


    @Override
    public Page<Member> searchWithoutSalary(SearchKeywordsWithoutSalaryDto dto, Pageable pageable) {

        List<Member> content = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(checkName(dto.getMemberName()), checkPosition(dto.getMemberPosition()), checkPartName(dto.getTeamPartName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .join(member.team, team)
                .where(checkName(dto.getMemberName()), checkPosition(dto.getMemberPosition()), checkPartName(dto.getTeamPartName()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }   //사원의 이름, 직위, 팀의 부서들을 종합해서 검색해 해당하는 사원의 정보를 가져오는 메서드

    private BooleanExpression checkName(String memberName) {
        return memberName != null ? member.name.eq(memberName) : null;
    }   //검색 키워드 중에서 사원의 이름이 존재할 경우 where 파트에 추가
    private BooleanExpression checkPosition(Position memberPosition) {
        return memberPosition != null ? member.position.eq(memberPosition) : null;
    }   //검색 키워드 중에서 사원의 직위가 존재할 경우 where 파트에 추가
    private BooleanExpression checkPartName(PartName partName) {
        return partName != null ? team.partName.eq(partName) : null;
    }   //검색 키워드 중에서 팀의 부서가 존재할 경우 where 파트에 추가


    @Override
    public Long updateMemberSalaryBulk(List<Long> membersId, int inputSalary) {
        long count = queryFactory
                .update(member)
                .set(member.salary, member.salary.add(inputSalary))
                .where(member.id.in(membersId))
                .execute();

        return count;   //연봉의 영향을 받은 사원의 수가 반환됨
    }   //특정한 사원들의 연봉을 한번에 가감하는 메서드


}
