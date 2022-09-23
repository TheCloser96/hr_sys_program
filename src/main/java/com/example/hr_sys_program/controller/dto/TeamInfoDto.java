package com.example.hr_sys_program.controller.dto;

import com.example.hr_sys_program.domain.roletype.PartName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(of = {"teamId", "teamPartName"})
public class TeamInfoDto {

    private Long teamId;
    private PartName teamPartName;

}
