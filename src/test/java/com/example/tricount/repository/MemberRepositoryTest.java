package com.example.tricount.repository;

import com.example.tricount.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void crud() {

        // save 테스트
        Member member = new Member("lsj", "password", "sangjun");
        Member saveResult = memberRepository.save(member);
        Assertions.assertThat(saveResult).isEqualTo(member);

        // findById 테스트
        Member findByResult = memberRepository.findById(saveResult.getUserNo()).get();
        Assertions.assertThat(findByResult.getUserNo()).isEqualTo(member.getUserNo());
        Assertions.assertThat(findByResult.getUserId()).isEqualTo(member.getUserId());
        Assertions.assertThat(findByResult.getPassword()).isEqualTo(member.getPassword());
        Assertions.assertThat(findByResult.getNickname()).isEqualTo(member.getNickname());

        // update 테스트
        memberRepository.update(saveResult.getUserNo(), new Member("newId", "newPassword", "newName"));
        Member findByUpdatedResult = memberRepository.findById(saveResult.getUserNo()).get();
        Assertions.assertThat(findByUpdatedResult.getUserNo()).isEqualTo(saveResult.getUserNo());
        Assertions.assertThat(findByUpdatedResult.getUserId()).isEqualTo("newId");
        Assertions.assertThat(findByUpdatedResult.getPassword()).isEqualTo("newPassword");
        Assertions.assertThat(findByUpdatedResult.getNickname()).isEqualTo("newName");

        // delete 테스트
        memberRepository.delete(saveResult.getUserNo());
        Optional<Member> deletedResultOptional = memberRepository.findById(saveResult.getUserNo());
        Assertions.assertThat(deletedResultOptional).isEmpty();
    }
}
