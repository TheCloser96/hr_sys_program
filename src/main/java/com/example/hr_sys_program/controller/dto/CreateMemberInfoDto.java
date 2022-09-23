package com.example.hr_sys_program.controller.dto;

import com.example.hr_sys_program.domain.roletype.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberInfoDto {

    private String name;
    private String strPosition;
    private int salary;
    private String strTeam;

}
