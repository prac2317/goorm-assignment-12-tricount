package com.example.tricount.repository;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class SettlementRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public SettlementRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("settlement")
                .usingGeneratedKeyColumns("settlement_no");
    }

    public Settlement save(Settlement settlement) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(settlement);
        Number key = jdbcInsert.executeAndReturnKey(param);
        settlement.setSettlementNo(key.longValue());

        //돌아가는지 테스트해보고 다시 고치기... 돌아가면 꼭 다시 고치기!!
        //Todo 1. 위쪽 param 등과 통합시키기, 2. param 여러 번 생성하는 과정 조금 더 가독성 있게 만들기
        String insertMemberSettlementSql = "INSERT INTO member_settlement (user_no, settlement_no) VALUES (:userNo, :settlementNo)";
        List<Member> participants = settlement.getParticipants();
        for (Member member : participants) {
            template.update(insertMemberSettlementSql, new MapSqlParameterSource()
                    .addValue("userNo", member.getUserNo())
                    .addValue("settlementNo", settlement.getSettlementNo()));
        }

        return settlement;
    }

    // todo 이거 위쪽과 겹치는지 나중에 확인
    public void join(Settlement settlement, Member member) {
        String insertMemberSettlementSql = "INSERT INTO member_settlement (user_no, settlement_no) VALUES (:userNo, :settlementNo)";
        template.update(insertMemberSettlementSql, new MapSqlParameterSource()
                .addValue("userNo", member.getUserNo())
                .addValue("settlementNo", settlement.getSettlementNo()));
    }

    public Optional<Settlement> findById(Long settlementNo) {
        String sql = "select settlement_no, title from settlement where settlement_no = :settlementNo";
        String memberSettlementSql = "select user_no from member_settlement where settlement_no = :settlementNo";
        String memberSql = "select user_no, user_id, nickname from member where user_no = :userNo";

        try {
            Map<String, Object> param = Map.of("settlementNo", settlementNo);
            Settlement settlement = template.queryForObject(sql, param, rowMapper());
            List<Long> userNos = template.queryForList(memberSettlementSql, param, Long.class);


            List<Member> members = new ArrayList<>();
            for (Long userNo : userNos) {
                Map<String, Object> userParam = Map.of("userNo", userNo);
                Member member = template.queryForObject(memberSql, userParam, memberRowMapper());
                members.add(member);
            }

            settlement.setParticipants(members);

            return Optional.of(settlement);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

//    todo: 테이블 관계 개념 공부하고 만들 것 - userNo 받아서 관련 정산들 갖고오기
    public List<Settlement> findByUserNo(Long userNo) {
        String memberSettlementSql = "select settlement_no from member_settlement where user_no = :userNo";
        Map<String, Object> param = Map.of("userNo", userNo);
        List<Long> settlementNos = template.queryForList(memberSettlementSql, param, Long.class);

        List<Settlement> settlements = new ArrayList<>();
        for (Long settlementNo : settlementNos) {
            Settlement settlement = findById(settlementNo).get();
            settlements.add(settlement);
        }

        return settlements;
    }

//    todo: update는 시간 남으면 만들자
//    public void update() {
//
//    }

    public void delete(Long settlementNo) {
        String sql = "delete from settlement where settlement_no = :settlementNo";

        Map<String, Object> param = Map.of("settlementNo", settlementNo);
        template.update(sql, param);
    }

    private RowMapper<Settlement> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Settlement.class); //camel 변환 지원
    }

    //나중에 고치기
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setUserNo(rs.getLong("user_no"));
            member.setUserId(rs.getString("user_id"));
            member.setNickname(rs.getString("nickname"));
            return member;
        };
    }
}
