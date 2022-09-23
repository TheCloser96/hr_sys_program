package com.example.hr_sys_program.service;

import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Long createOrFind(Team team) {

        /*
            team 객체가 이미 존재하는지 알아보는 작업
         */

        Optional<Team> findByPartName = teamRepository.findByPartName(team.getPartName());  //입력된 팀의 부서명을 기준으로 팀 객체를 가져온다

        if (findByPartName.isEmpty()) {
            Team saveTeam = teamRepository.save(team);
            return saveTeam.getId();
        } else {
            return findByPartName.get().getId();
        }

    }   //입력된 팀 객체가 이미 존재할 경우 기존 팀의 id값 반환, 아닐경우 팀 객체 저장 후 id값 반환

    public List<Team> findAll() {
        return teamRepository.findAll();
    }   //존재하는 모든 Team 객체 반환
}
