package com.hyoju.login_service.web.control;


import com.hyoju.login_service.domain.member.Member;
import com.hyoju.login_service.service.MemberService;
import com.hyoju.login_service.web.dto.LoginRequest;
import com.hyoju.login_service.web.dto.LoginResponse;
import com.hyoju.login_service.web.dto.MemberInfoResponse;
import com.hyoju.login_service.web.dto.MemberJoinRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController // JSON 방식은 이 애노테이션으로 컨트롤러 등록
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    @PostMapping(value = "/member/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) {
        String token = service.login(login.getLoginId(), login.getPassword());
        LoginResponse loginResponse  = new LoginResponse(token, "Bearer");
        return ResponseEntity.ok(loginResponse);
    }

    // 회원가입
    @PostMapping("/member/join")
    public String join(@RequestBody @Valid MemberJoinRequest request) { // JSON 으로 받아서 서비스에는 데이터로 전달
        service.join(request.getName(), request.getLoginId(), request.getPassword());
        log.info("회원가입 요청 - loginId: {}", request.getLoginId());

        return "ok";
    }

    @GetMapping("/member/info")
    public MemberInfoResponse getMemberInfo(HttpServletRequest request) {
        Long memberId = (Long) request.getAttribute("memberId");
        return service.getMemberInfo(memberId);
    }



}
