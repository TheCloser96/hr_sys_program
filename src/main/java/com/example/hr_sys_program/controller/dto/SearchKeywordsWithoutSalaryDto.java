package com.example.hr_sys_program.controller.dto;

import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchKeywordsWithoutSalaryDto {

    private String memberName;
    private Position memberPosition;
    private PartName teamPartName;

}
