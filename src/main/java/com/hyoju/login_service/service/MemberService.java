package com.hyoju.login_service.service;

import com.hyoju.login_service.domain.auth.JwtTokenProvider;
import com.hyoju.login_service.domain.member.Member;
import com.hyoju.login_service.domain.member.MemberRepository;
import com.hyoju.login_service.web.dto.MemberInfoResponse;
// import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.GetMapping;

// import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 기능
    public void join(String name, String loginId, String password) {
        Optional<Member> findMember = memberRepository.findByLoginId(loginId);

        // 중복 확인
        if (findMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        Member member = new Member(name, loginId, encodedPassword, "USER");
        memberRepository.save(member); // 이때 자동으로 PK 값이 만들어져 같이 저장됨
    }


    // 로그인 기능
    public String login(String loginId, String password) {

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password,member.getPassword())) { // 인자 순서 중요 ‼️ (평문,해시)
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(member.getId(),member.getRole()); // 반환타입은 String
    }

    public MemberInfoResponse getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return new MemberInfoResponse(member.getName(), member.getLoginId());
    }

}
