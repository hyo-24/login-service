package com.hyoju.login_service.member;

import com.hyoju.login_service.domain.auth.JwtTokenProvider;
import com.hyoju.login_service.domain.member.Member;
import com.hyoju.login_service.domain.member.MemberRepository;
import com.hyoju.login_service.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    // DI 처럼 작동하게 하는 것 (스프링 사용 안하는 순수 자바코드라서 필요)
    @Mock // 가짜 객체 생성
    MemberRepository repository;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @InjectMocks // 가짜 객체를 이 객체에 DI
    MemberService service;

    @Test
    public void 회원가입() {
        // when
        service.join("kim", "hyo39", "hyo45");

        // then (반환값이 void)
        verify(repository, times(1)).save(any(Member.class));
    }

    @Test
    void 로그인_성공() {
        // given
        Member member = new Member("kim", "3783", "2f83");
        BDDMockito.given(repository.findByLoginId("3783")).willReturn(Optional.of(member));
        BDDMockito.given(jwtTokenProvider.createToken(any())).willReturn("testToken"); // given 은 DI 된 가짜 객체의 반환값을 지정

        // when
        String login = service.login(member.getLoginId(), member.getPassword());

        // then
        Assertions.assertThat(login).isEqualTo("testToken");

    }

}
