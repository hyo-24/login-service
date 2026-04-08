package com.hyoju.login_service.service;

import com.hyoju.login_service.domain.auth.JwtTokenProvider;
import com.hyoju.login_service.domain.member.Member;
import com.hyoju.login_service.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 기능
    public void join(String name, String loginId, String password) {
        Member member = new Member(name, loginId, password);
        memberRepository.save(member); // 이때 자동으로 PK 값이 만들어져 같이 저장됨
    }

    // 로그인 기능
    public String login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!member.getPassword().equals(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(member.getId()); // 반환타입은 String
    }



}
