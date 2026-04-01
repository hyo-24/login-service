package com.hyoju.login_service.domain.member;


public class Member {

    private Long id;
    private String name;

    private String loginId;
    private String password;

    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }


}
