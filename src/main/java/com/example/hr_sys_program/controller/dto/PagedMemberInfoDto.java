package com.example.hr_sys_program.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString(of = {"totalPagesCnt", "pageIndexNum", "contentCnt", "memberInfoDtoList"})
public class PagedMemberInfoDto {

    private int totalPagesCnt;
    private int pageIndexNum;
    private int contentCnt;
    private List<MemberInfoDto> memberInfoDtoList;

}
