/**
 * 부서의 이름들을 나열
 */
package com.example.hr_sys_program.domain.roletype;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PartName {
    hr, lab, sales, pr, account, planning, non;

    @JsonCreator
    public static PartName from(String strPartName) {
        return PartName.valueOf(strPartName.toLowerCase());
    }
}
