package com.hyoju.login_service.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberJoinRequest { // 데이터 요청 객체

    private String name;
    private String loginId;
    private String password;
}
