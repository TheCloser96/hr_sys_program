package com.example.hr_sys_program.controller;

import com.example.hr_sys_program.controller.dto.*;
import com.example.hr_sys_program.domain.Member;
import com.example.hr_sys_program.domain.roletype.PartName;
import com.example.hr_sys_program.domain.roletype.Position;
import com.example.hr_sys_program.service.MemberService;
import com.example.hr_sys_program.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final TeamService teamService;


    @PostMapping("/api/v1/createNewMember")
    public Long createNewMemberV1(@RequestBody CreateMemberInfoDto dto) {
        log.info("method: createNewMember 진입");

        return memberService.saveMember(dto);
    }   //사원 정보를 입력 후 저장하고 해당 사원의 id값 반환


    @GetMapping("/api/v1/findMemberInfo/{name}")
    public WrappingMemberInfoDto findMemberInfoByMemberNameV1(@PathVariable(value = "name") String name) {
        log.info("method: findMemberInfoByMemberName 진입");

        List<Member> members = memberService.searchByMemberName(name);  //입력된 사원의 이름을 바탕으로 리스트를 가져옴

        log.info("검색된 이름: {}", name);
        log.info("=====조회 결과=====");
        for (Member member : members) {
            log.info("{}\n", member);
        }
        log.info("=====조회 결과=====");
        
        List<MemberInfoDto> memberInfoDtoList = convertMemberListToMemberInfoDtoList(members);  //List<Member> -> List<MemberInfoDto> 형태로 바꾸는 메서드

        return new WrappingMemberInfoDto(memberInfoDtoList);    //반환시 배열 형태로 반환을 방지 하기 위해 랩핑하여 반환함
    }   //사원의 이름을 입력시 해당 사원과 관련한 정보들을 반환


    @GetMapping("/api/v1/findMemberInfo")
    public WrappingMemberInfoDto findMemberInfoV1(@RequestBody InputSearchKeywordsDto dto, Pageable pageable) {
        log.info("method: findMemberInfo 진입");
        log.info("검색 조건: {}", dto);

        SearchKeywordsWithoutSalaryDto queryDto = convertInputSearchKeywordsDtoToSearchKeywordsWithoutSalaryDto(dto);   //InputSearchKeywordsDto -> SearchKeywordsWithoutSalaryDto 전환
        Page<Member> members = memberService.searchWithoutSalary(queryDto, pageable);   //특정한 조건으로 검색된 Page 처리가 되어있는 Member 객체들

        WrappingMemberInfoDto memberInfoDtoWrapping = convertPagedMemberToMemberInfoDto(members);   //Page<Member> -> MemberInfoDtoWrapping
        log.info("조회 결과: {}", memberInfoDtoWrapping);

        return memberInfoDtoWrapping;
    }   //사원의 이름, 직위, 소속된 팀 부서명을 종합적으로 받아서 사원과 관련한 정보들을 페이지 형식으로 반환


    @PatchMapping("/api/v1/changeMemberSalary/{memberId}")
    public int changeMemberSalary(@PathVariable(value = "memberId") Long memberId, @RequestParam int updateSalary) {
        log.info("method: findMemberInfo 진입");

        int modifiedSalary = memberService.updateMemberSalary(memberId, updateSalary);

        log.info("변경 결과");
        log.info("memberId: {}, memberSalary: {}", memberId, modifiedSalary);

        return modifiedSalary;
    }   //해당 사원의 고유 Id값을 이용하여 찾아낸 후 해당 사원의 연봉을 조정 후 조정된 연봉값을 반환


    @PatchMapping("/api/v1/changeMembersSalary")
    public Long changeMembersSalary(@RequestBody MembersSalaryDto membersSalaryDto) {
        log.info("method: changeMembersSalary 진입");

        log.info("해당 사원 ID: {}", membersSalaryDto.getMembersId());
        log.info("감가 연봉: {}", membersSalaryDto.getInputSalary());

        //해당되는 사원의 고유 ID값들과 연봉을 감가할 금액을 받아옴
        List<Long> getMembersId = membersSalaryDto.getMembersId();  //해당 사원들 ID 리스트
        int getInputSalary = membersSalaryDto.getInputSalary(); //해당 사원들 대상으로 감가할 금액

        Long memberCnt = memberService.updateMemberSalaryBulk(getMembersId, getInputSalary);
        log.info("연봉 조정된 사원 인원수: {}", memberCnt);
        
        return memberCnt;
    }   //특정 사원들의 연봉을 한번에 조정 후 조정된 사원들의 수를 반환




    private List<MemberInfoDto> convertMemberListToMemberInfoDtoList(List<Member> members) {
        List<MemberInfoDto> memberInfoDtoList = members.stream()
                .map(MemberInfoDto::new)
                .collect(Collectors.toList());  //entity -> dto 변환

        return memberInfoDtoList;
    }   //List<Member>의 Entity 관련 정보노출을 방지하기 위해 List<MemberInfoDto>로 전환해서 반환하는 메서드

    private SearchKeywordsWithoutSalaryDto convertInputSearchKeywordsDtoToSearchKeywordsWithoutSalaryDto(InputSearchKeywordsDto dto) {

        //SearchKeywordsWithoutSalaryDto 객체에 값을 기입하기 위해 알맞게 값들을 변환하는 작업
        String memberName = dto.getMemberName();
        Position memberPosition = Position.from(dto.getStrMemberPosition());
        PartName teamPartName = PartName.from(dto.getStrTeamPartName());

        return new SearchKeywordsWithoutSalaryDto(memberName, memberPosition, teamPartName);
    }   //입력받은 InputSearchKeywordsDto 객체로 부터 값들을 추출 후 SearchKeywordsWithoutSalaryDto 객체로 값들을 알맞게 설정후 이식하는 메서드

    private WrappingMemberInfoDto convertPagedMemberToMemberInfoDto(Page<Member> pagedMembers) {
        
        //Page<Member> 와 관련한 값들을 추출

        List<Member> content = pagedMembers.getContent();    //해당 조건에 맞게 페이징 된 Member 객체들
        int contentCnt = content.size();  //해당 페이지의 컨텐츠 개수
        int totalPages = pagedMembers.getTotalPages();//전체 페이지 수
        int number = pagedMembers.getNumber();//현재 페이지 번호


        List<MemberInfoDto> memberInfoDtoList = convertMemberListToMemberInfoDtoList(content);  //List<Member> -> List<MemberInfoDto>
        PagedMemberInfoDto pagedMemberInfoDto = new PagedMemberInfoDto(totalPages, number, contentCnt, memberInfoDtoList);  //List<MemberInfoDto> 및 Page 관련한 정보도 같이 있는 Dto

        return new WrappingMemberInfoDto(pagedMemberInfoDto);   //반환시 랩핑하여 반환함
    }   //페이징 처리가 된 Member 객체들 및 관련한 정보들을 dto 객체로 전환 후 랩핑하는 메서드
}
