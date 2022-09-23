package com.example.hr_sys_program.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"memberName", "strMemberPosition", "strTeamPartName"})
public class InputSearchKeywordsDto {

    private String memberName;
    private String strMemberPosition;
    private String strTeamPartName;
}
