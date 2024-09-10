package com.example.tricount.repository;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        return settlement;
    }

    public Optional<Settlement> findById(Long settlementNo) {
        String sql = "select settlement_no, title from settlement where settlement_no = :settlementNo";

        try {
            Map<String, Object> param = Map.of("settlementNo", settlementNo);
            Settlement settlement = template.queryForObject(sql, param, rowMapper());
            return Optional.of(settlement);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

//    todo: 테이블 관계 개념 공부하고 만들 것 - userNo 받아서 관련 정산들 갖고오기
//    public List<Settlement> findByUserNo(Long userNo) {
//
//    }

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
}
