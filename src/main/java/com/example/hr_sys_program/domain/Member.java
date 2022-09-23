/**
 * 사원과 관련한 정보가 있는 클래스
 */
package com.example.hr_sys_program.domain;

import com.example.hr_sys_program.domain.roletype.Position;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "position", "salary"})
public class Member extends Common{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;    //해당 테이블의 고유 아이디값

    @NotNull
    private String name;    //사원의 이름

    @NotNull
    @Enumerated(STRING)
    private Position position;    //사원의 직위

    @NotNull
    private int salary; //사원의 연봉

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;  //해당 사원의 소속팀


    /**
     * set관련 메서드
     */
    public void modifySalary(int inputSalary) {
        modifiedRecord();
        this.salary = inputSalary;
    }   //Member 객체의 연봉을 조정하는 메서드



    /**
     * 연관관계 메서드
     */

    public void settingTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }


    /**
     * 생성메서드 전용 생성자
     */

    private Member(String name, Position position, int salary, Team team) {

        firstRecord();  //초회 기록시간 기록
        
        /*
            입력된 정보 기입
         */
        this.name = name;
        this.position = position;
        this.salary = salary;
        settingTeam(team);
    }


    /**
     * 생성 메서드
     */

    public static Member createMember(String name, Position position, int salary, Team team) {
        return new Member(name, position, salary, team);
    }
}
