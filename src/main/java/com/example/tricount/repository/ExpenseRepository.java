package com.example.tricount.repository;

import com.example.tricount.domain.Expense;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class ExpenseRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ExpenseRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("expense")
                .usingGeneratedKeyColumns("expense_no");
    }

    public Expense save(Expense expense) {
        String sql = "INSERT INTO expense (title, user_no, amount, date, settlement_no) " +
                "VALUES (:title, :user_no, :amount, :date, :settlementNo)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", expense.getTitle())
                .addValue("user_no", expense.getPaidBy().getUserNo())
                .addValue("amount", expense.getAmount())
                .addValue("date", expense.getDate())
                .addValue("settlement_no", expense.getSettlement().getSettlementNo());

        Number key = jdbcInsert.executeAndReturnKey(param);
        expense.setExpenseNo(key.longValue());

        return expense;
    }

    public Optional<Expense> findById(Long expenseNo) {
        String sql = "select expense_no, title, amount, date from expense where expense_no = :expenseNo";

        String memberSql = "SELECT m.user_no, m.user_id, m.nickname " +
                "FROM expense e " +
                "JOIN member m ON e.user_no = m.user_no " +
                "WHERE e.expense_no = :expenseNo";

        String settlementSql = "SELECT s.settlement_no, s.title " +
                "FROM expense e " +
                "JOIN settlement s ON e.settlement_no = s.settlement_no " +
                "WHERE e.expense_no = :expenseNo";

        try {
            Map<String, Object> param = Map.of("expenseNo", expenseNo);
            Expense expense = template.queryForObject(sql, param, rowMapper());

            Member member = template.queryForObject(memberSql, param, memberRowMapper());
            Settlement settlement = template.queryForObject(settlementSql, param, settlementRowMapper());

            expense.setPaidBy(member);
            expense.setSettlement(settlement);

            return Optional.of(expense);
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Expense> findBySettlementNo(Long settlementNo) {
        String settlementSql = "select expense_no from expense where settlement_no = :settlementNo";
        Map<String, Object> param = Map.of("settlementNo", settlementNo);
        List<Long> expenseNos = template.queryForList(settlementSql, param, Long.class);

        List<Expense> expenses = new ArrayList<>();
        for (Long expenseNo : expenseNos) {
            Expense expense = findById(expenseNo).get();
            expenses.add(expense);
        }

        return expenses;
    }

    private RowMapper<Expense> rowMapper() {
        return BeanPropertyRowMapper.newInstance(Expense.class); //camel 변환 지원
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setUserNo(rs.getLong("user_no"));
            member.setUserId(rs.getString("user_id"));
            member.setNickname(rs.getString("nickname"));
            return member;
        };
    }

    private RowMapper<Settlement> settlementRowMapper() {
        return (rs, rowNum) -> {
            Settlement settlement = new Settlement();
            settlement.setSettlementNo(rs.getLong("settlement_no"));
            settlement.setTitle(rs.getString("title"));
            return settlement;
        };
    }
}
