package com.hyoju.login_service.web.control;


import com.hyoju.login_service.service.MemberService;
import com.hyoju.login_service.web.dto.LoginRequest;
import com.hyoju.login_service.web.dto.LoginResponse;
import com.hyoju.login_service.web.dto.MemberJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController // JSON 방식은 이 애노테이션으로 컨트롤러 등록
@RequiredArgsConstructor
public class MemberController {

    private final MemberService service;

    @PostMapping(value = "/member/login")
    public LoginResponse login(@RequestBody LoginRequest login) {
        String token = service.login(login.getLoginId(), login.getPassword());
        return new LoginResponse(token, "Bearer");
    }

    // 회원가입
    @PostMapping("/member/join")
    public String join(@RequestBody MemberJoinRequest request) { // JSON 으로 받아서 서비스에는 데이터로 전달
        service.join(request.getName(), request.getLoginId(), request.getPassword());
        System.out.println("request.getLoginId() = " + request.getLoginId());
        System.out.println("request.getPassword() = " + request.getPassword());

        return "ok";
    }


}
