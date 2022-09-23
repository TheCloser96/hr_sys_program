/**
 * Member의 정보와 해당 Member의 Team객체와 관련한 정보까지 추출하는 용도로 사용되는 DTO
 */
package com.example.hr_sys_program.controller.dto;

import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.Team;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@ToString(of = {"memberId", "memberName", "memberPosition", "memberSalary", "memberTeam"})
public class MemberInfoDto {

    private Long memberId;
    private String memberName;
    private Position memberPosition;
    private int memberSalary;
    private TeamInfoDto memberTeam;

    @Data
    @AllArgsConstructor
    @ToString(of = {"teamId", "teamPartName"})
    private static class TeamInfoDto {
        private Long teamId;
        private PartName teamPartName;
    }   //memberTeam 변수에 기입하는 용도로 사용되는 내부 static 클래스

    public MemberInfoDto(Member memberInfo) {
        //Member 객체와 직접덕으로 관련한 것들 작업
        
        this.memberId = memberInfo.getId();
        this.memberName = memberInfo.getName();
        this.memberPosition = memberInfo.getPosition();
        this.memberSalary = memberInfo.getSalary();

        //Team 객체와 관련한 것들은 따로 작업
        
        Long teamId = memberInfo.getTeam().getId();
        PartName teamPartName = memberInfo.getTeam().getPartName();
        this.memberTeam = new TeamInfoDto(teamId, teamPartName);
    }
}
