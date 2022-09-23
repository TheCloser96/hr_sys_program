/**
 * 소속된 팀과 관련한 정보가 있는 클래스
 */
package com.example.hr_sys_program.domain;

import com.example.hr_sys_program.domain.roletype.PartName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "partName"})
public class Team extends Common{

    @Id 
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;    //해당 테이블의 고유 아이디값

    @NotNull
    @Enumerated(STRING)
    private PartName partName;  //해당 팀의 부서명

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<Member> members = new ArrayList<>();   //해당 팀원의 사원들


    /**
     * 생성메서드 전용 생성자
     */

    private Team(PartName partName) {
        
        firstRecord();  //초회시간을 기록

        /*
            입력된 정보 기입
         */
        this.partName = partName;
    }


    /**
     * 생성 메서드
     */

    public static Team createTeam(PartName partName) {
        return new Team(partName);
    }
}
