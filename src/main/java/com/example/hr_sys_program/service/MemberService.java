package com.example.hr_sys_program.service;

import com.example.hr_sys_program.controller.dto.CreateMemberInfoDto;
import com.example.hr_sys_program.controller.dto.SearchKeywordsWithoutSalaryDto;
import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import com.example.hr_sys_program.repository.MemberRepository;
import com.example.hr_sys_program.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static com.example.hr_sys_program.domain.Member.createMember;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final EntityManager em;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public Long saveMember(CreateMemberInfoDto dto) {

        //팀 부서명을 기준으로 해당 팀을 가져오는 과정
        PartName getPartName = PartName.from(dto.getStrTeam());
        Team findTeam = teamRepository.findByPartName(getPartName).get();

        //사원 객체 생성 및 해당 객체에 맞는 값들을 기입 후 저장
        Position getPosition = Position.from(dto.getStrPosition());
        Member member = createMember(dto.getName(), getPosition, dto.getSalary(), findTeam);
        Member saveMember = memberRepository.save(member);

        return saveMember.getId();  //저장된 해당 객체의 id값 반환
    }   //사원을 저장하는 메서드

    public List<Member> searchByMemberName(String name) {
        return memberRepository.searchByMemberName(name);
    }   //사원의 이름을 입력하여 해당 사원의 정보들을 조회하는 메서드

    public Page<Member> searchWithoutSalary(SearchKeywordsWithoutSalaryDto dto, Pageable pageable) {
        return memberRepository.searchWithoutSalary(dto, pageable);
    }   //사원의 이름, 직위, 팀의 부서들을 종합해서 사원 정보들을 조회하는 메서드

    @Transactional
    public int updateMemberSalary(Long memberId, int inputSalary) {
        Member findMemberById = memberRepository.findById(memberId).orElseThrow(null);
        findMemberById.modifySalary(inputSalary);

        return findMemberById.getSalary();  //해당 사원의 변경된 연봉을 반환
    }   //특정 사원의 연봉을 수정하는 메서드

    @Transactional
    public Long updateMemberSalaryBulk(List<Long> membersId, int inputSalary) {

        Long affectedMemberCnt = memberRepository.updateMemberSalaryBulk(membersId, inputSalary);    //특정 사원들의 연봉을 해당 금액만큼 가감 후 영향을 받은 사원의 인원수 반환

        em.flush();
        em.clear(); //영속성 컨텍스트 정보와 DB정보가 다르므로 같게 설정
        
        return affectedMemberCnt;
    }   //특정 사원들의 연봉을 한번에 가감하는 메서드
}
