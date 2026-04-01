package com.hyoju.login_service.domain.member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    // 클라이언트에게 받는 정보
    private String loginId;
    private String password;
    private String name;

    // DB 의 PK값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }


}
