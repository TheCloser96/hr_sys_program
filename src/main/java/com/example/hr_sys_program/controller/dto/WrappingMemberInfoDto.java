package com.example.hr_sys_program.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString(of = {"memberData"})
public class WrappingMemberInfoDto<T> {
    private T memberData;
}
