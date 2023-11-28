package com.goormthon.tricount.service;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.repository.JdbcMemberRepository;
import com.goormthon.tricount.repository.MemberRepository;
import com.goormthon.tricount.utils.TricountApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public Member signup(Member member) {
        return repository.save(member);
    }

    public Member login(String loginId, String password) {
        Optional<Member> findMembers = repository.findByLoginId(loginId);
        return findMembers.filter(m -> m.getPassword().equals(password))
                .orElseThrow(() -> new TricountApiException("cannot found member", TricountApiErrorCode.NOT_FOUND));
    }
}
