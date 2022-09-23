package com.example.hr_sys_program.service;

import com.example.hr_sys_program.controller.dto.CreateMemberInfoDto;
import com.example.hr_sys_program.controller.dto.SearchKeywordsWithoutSalaryDto;
import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import com.example.hr_sys_program.repository.MemberRepository;
import com.example.hr_sys_program.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.hr_sys_program.domain.Member.createMember;
import static com.example.hr_sys_program.domain.Team.createTeam;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private TeamService teamService;


    /*
        테스트로 사용될 값들을 미리 셋팅
     */
    @BeforeEach
    public void beforeStart() {
        /*
            각 사원이 속하게 될 팀 객체 생성
         */
        Team teamHr = createTeam(PartName.hr);  //인사부
        Team teamLab = createTeam(PartName.lab);    //R&D
        Team teamSales = createTeam(PartName.sales);    //영업부

        /*
            각 팀들을 저장
         */
        teamRepository.save(teamHr);
        teamRepository.save(teamLab);
        teamRepository.save(teamSales);



        /*
            사원 객체 생성
         */
        Member james = createMember("james", Position.staff, 30000, teamHr);
        Member james2 = createMember("james", Position.staff, 34000, teamHr);
        Member jack = createMember("jack", Position.assistant_manager, 37000, teamHr);
        Member aron = createMember("aron", Position.manager, 42000, teamHr);
        
        /*
            각 사원들을 저장
         */
        memberRepository.save(james);
        memberRepository.save(james2);
        memberRepository.save(jack);
        memberRepository.save(aron);
        
    }


    @Test
    public void saveMember() throws Exception {
        //given
        CreateMemberInfoDto dto = new CreateMemberInfoDto("aden", "staff", 27000, "hr");


        //when
        Long saveMemberId = memberService.saveMember(dto);


        //then
        Member findMember = memberRepository.findById(saveMemberId).get();
        assertThat(findMember.getName()).isEqualTo(dto.getName());
    }


    @Test
    public void searchByMemberName() throws Exception {
        //given
        String name = "jack";


        //when
        List<Member> members = memberService.searchByMemberName(name);
        Member member = members.get(0);


        //then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getPosition()).isEqualTo(Position.assistant_manager);
        assertThat(member.getSalary()).isEqualTo(37000);
    }


    @Test
    public void searchWithoutSalary() throws Exception {
        //given
        SearchKeywordsWithoutSalaryDto keywords = new SearchKeywordsWithoutSalaryDto("james", Position.staff, PartName.hr);
        Pageable pageable = PageRequest.of(0, 1);

        //when
        Page<Member> members = memberService.searchWithoutSalary(keywords, pageable);

        //then
        System.out.println(members.getSize());
        System.out.println(members.getTotalPages());
        System.out.println(members.getTotalElements());
    }


    @Test
    public void updateMemberSalary() throws Exception {
        //given

        List<Member> getMembers = memberService.searchByMemberName("aron");
        Member member = getMembers.get(0);


        //when

        memberService.updateMemberSalary(member.getId(), 45000);
        em.flush();
        em.clear();

        //then
        int aronSalary = memberService.searchByMemberName("aron").get(0).getSalary();

        assertThat(aronSalary).isEqualTo(45000);
    }


    @Test
    public void updateMemberSalaryBulk() throws Exception {
        //given
        Team teamLab = createTeam(PartName.lab);
        Team savedTeam = teamRepository.save(teamLab);

        Member jackson1 = createMember("jackson1", Position.manager, 37000, savedTeam);
        Member jackson2 = createMember("jackson2", Position.manager, 35000, savedTeam);
        memberRepository.save(jackson1);
        memberRepository.save(jackson2);

        List<Long> membersId = new ArrayList<>();
        membersId.add(jackson1.getId());
        membersId.add(jackson2.getId());


        //when
        Long memberCnt = memberService.updateMemberSalaryBulk(membersId, 1500);


        //then
        assertThat(memberCnt).isEqualTo(2); //연봉 가감 적용된 사원 인원 수
        assertThat(memberRepository.findById(membersId.get(0)).get().getSalary()).isEqualTo(38500);
        assertThat(memberRepository.findById(membersId.get(1)).get().getSalary()).isEqualTo(36500);
    }
}