package com.goormthon.tricount.controller;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.dto.NameDto;
import com.goormthon.tricount.model.ApiResponse;
import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.model.Settlement;
import com.goormthon.tricount.service.SettlementService;
import com.goormthon.tricount.utils.SessionConst;
import com.goormthon.tricount.utils.TricountApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/settle")
public class SettleController {

    private final SettlementService service;

    @GetMapping
    public ApiResponse<Settlement> getSettlementList() {
        List<Settlement> settlements = service.findAllSettlement();
        return new ApiResponse<Settlement>().ok(settlements);
    }

    @PostMapping("/add")
    public ApiResponse createAndJoinSettlement(@RequestBody NameDto name,
                                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                        Member loginMember) {
        if (loginMember == null) {
            return new ApiResponse().fail(TricountApiErrorCode.LOGIN_NEEDED.getCode(), "session expired");
        }
        log.info("create settlement name={}", name);
        service.createAndJoin(name.getName(), loginMember);
        return new ApiResponse().ok();
    }

    @GetMapping("/{settleNo}")
    public ApiResponse<Settlement> findSettlementById(@PathVariable Long settleNo) {
        Settlement findSettlement = service.findSettlementById(settleNo);
        return new ApiResponse<Settlement>().ok(findSettlement);
    }

    @DeleteMapping("/{settleNo}")
    public ApiResponse deleteSettlementById(@PathVariable Long settleNo) {
        try{
            service.delete(settleNo);
        } catch (TricountApiException e){
            return new ApiResponse().fail(e.getErrorCode().getCode(), e.getMessage());
        }
        return new ApiResponse().ok();
    }

    @PostMapping("/{settleNo}/join")
    public ApiResponse joinSettlementById(@PathVariable Long settleNo,
                                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
                                          Member loginMember) {
        if (loginMember == null) {
            return new ApiResponse().fail(TricountApiErrorCode.LOGIN_NEEDED.getCode(), "session expired");
        }
        log.info("current loginId ={} loginName={}", loginMember.getLoginId(), loginMember.getName());
        try{
            service.joinSettlement(settleNo, loginMember);
        } catch (TricountApiException e) {
            return new ApiResponse().fail(e.getErrorCode().getCode(), e.getMessage());
        }
        return new ApiResponse().ok();
    }

    @GetMapping("/{settleNo}/members")
    public ApiResponse<Member> findSettlementMembers(@PathVariable Long settleNo) {
        List<Member> settlementMembers = service.findSettlementMembers(settleNo);
        return new ApiResponse<Member>().ok(settlementMembers);
    }
}
