package com.example.tricount.repository;

import com.example.tricount.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public MemberRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("user_no");
    }

    public Member save(Member member) {

//        String sql = "insert into member (user_id, password, nickname) values (:userId, :password, :nickname)";
//        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        template.update(sql, param, keyHolder);
//        Long key = keyHolder.getKey().longValue();
//        member.setUserNo(key);

        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        Number key = jdbcInsert.executeAndReturnKey(param);
        member.setUserNo(key.longValue());

        return member;
    }

    public Optional<Member> findById(Long userNo) {
        String sql = "select user_no, user_id, password, nickname from member where user_no = :userNo";
        // Todo expense와의 관계,settlement와의 관계 추가 필요

        try {
            Map<String, Object> param = Map.of("userNo", userNo);
            Member member = template.queryForObject(sql, param, rowMapper());
            return Optional.of(member);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Member> findByLoginId(String userId) {
        String sql = "select user_no, user_id, password, nickname from member where user_id=:userId";

        try {
            Map<String, Object> param = Map.of("userId", userId);
            Member member = template.queryForObject(sql, param, rowMapper());
            return Optional.of(member);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void update(Long userNo, Member member) {
        String sql = "update member set user_id = :userId, password=:password , nickname=:nickname where user_no = :userNo";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userId", member.getUserId())
                .addValue("password", member.getPassword())
                .addValue("nickname", member.getNickname())
                .addValue("userNo", userNo);

        template.update(sql, param);
    }

    public void delete(Long userNo) {
        String sql = "delete from member where user_no = :userNo";

        Map<String, Object> param = Map.of("userNo", userNo);
        template.update(sql, param);
    }

    private RowMapper<Member> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class); //camel 변환 지원
    }
}
