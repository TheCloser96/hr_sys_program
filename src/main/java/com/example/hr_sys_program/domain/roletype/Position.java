/**
 * 사원의 직급들을 나열
 */
package com.example.hr_sys_program.domain.roletype;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Position {
    staff, assistant_manager, manager, general_manager, director;

    @JsonCreator
    public static Position from(String strPosition) {
        return Position.valueOf(strPosition.toLowerCase());
    }
}
