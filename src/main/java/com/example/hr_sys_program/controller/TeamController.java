package com.example.hr_sys_program.controller;

import com.example.hr_sys_program.controller.dto.TeamInfoDto;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.service.TeamService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.hr_sys_program.domain.Team.createTeam;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;

    /**
     *  List 형태의 자료구조를 wrapping 하기위한 용도로 사용되는 Inner Class
     */
    @Data
    @AllArgsConstructor
    @ToString(of = {"teamData"})
    static class WrappingTeam<T> {
        private T teamData;
    }



    @PostMapping("/api/v1/createOrFindTeam/{name}")
    public Long createOrFindTeamV1(@PathVariable(value = "name", required = true) String name) {
        log.info("method: createOrFindTeam 진입");
        
        PartName partName = PartName.from(name);    //String -> Enum 으로 전환
        Team team = createTeam(partName);
        log.info("입력값: {}", name);
        
        return teamService.createOrFind(team);
    }   //특정 팀의 부서명을 입력시 없는 경우 생성 후 id값 반환, 존재시 기존의 id값 반환


    @GetMapping("/api/v1/findTeamNameInfo")
    public WrappingTeam findTeamInfoByTeamNameV1() {
        List<Team> getAllTeamName = teamService.findAll();
        ArrayList<TeamInfoDto> teamInfoDtoList = new ArrayList<>();

        for (Team team : getAllTeamName) {
            TeamInfoDto teamInfoDto = new TeamInfoDto(team.getId(), team.getPartName());
            teamInfoDtoList.add(teamInfoDto);
        }

        return new WrappingTeam(teamInfoDtoList);
    }   //등록되어 있는 팀들을 모두 조회하는 메서드(List 형태 방지용 wrapping 클래스로 처리)

}
