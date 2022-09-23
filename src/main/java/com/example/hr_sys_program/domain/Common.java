/**
 * 공통적으로 기재해야 하는 것들을 모아서 작성한 클래스
 */
package com.example.hr_sys_program.domain;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Getter
public abstract class Common {
    private LocalDateTime createTime;   //해당 정보가 최초로 입력된 시간
    private LocalDateTime modifiedTime; //해당 정보가 변경된 시간


    public void firstRecord() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        this.createTime = now;
        this.modifiedTime = now;
    }   //정보를 처음으로 입력할 경우

    public void modifiedRecord() {
        this.modifiedTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }   //정보를 변경한 경우
}
