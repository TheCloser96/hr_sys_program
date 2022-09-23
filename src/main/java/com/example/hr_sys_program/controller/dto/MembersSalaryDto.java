package com.example.hr_sys_program.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"membersId", "inputSalary"})
public class MembersSalaryDto {
    List<Long> membersId;
    int inputSalary;
}
