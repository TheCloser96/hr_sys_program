package com.example.hr_sys_program.service;

import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import com.example.hr_sys_program.repository.MemberRepository;
import com.example.hr_sys_program.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.example.hr_sys_program.domain.Member.createMember;
import static com.example.hr_sys_program.domain.Team.createTeam;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamServiceTest {

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

    
    @Test
    public void createOrFind() throws Exception {
        //given
        Team teamPlanning = createTeam(PartName.planning);  //임의의 특정 부서명을 가진 Team 객체 생성
        Team savePlanningId = teamRepository.save(teamPlanning);


        //when
        Long createOrFindTeamPlanning = teamService.createOrFind(createTeam(PartName.planning));
        Long createOrFindTeamNon = teamService.createOrFind(createTeam(PartName.non));  //존재하지 않는 부서명을 가진 Team 객체 삽입

        Team findTeamPlanning = teamRepository.findById(createOrFindTeamPlanning).get();
        Team findTeamNon = teamRepository.findById(createOrFindTeamNon).get();


        //then
        assertThat(findTeamPlanning.getPartName()).isEqualTo(PartName.planning);
        assertThat(findTeamNon.getPartName()).isEqualTo(PartName.non);

        assertThat(createOrFindTeamPlanning).isEqualTo(savePlanningId.getId());
    }
}