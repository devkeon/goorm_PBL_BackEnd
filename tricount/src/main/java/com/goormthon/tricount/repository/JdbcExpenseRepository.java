package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Expense;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class JdbcExpenseRepository implements ExpenseRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcExpenseRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("expense")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Expense> findAllExpenses(Long settlementId) {
        String sql = "select m.name as memberName, e.name as name, e.id as id, e.price as price, e.expense_date_time as expenseDateTime" +
                " from expense e join member m on e.member_id=m.id where settlement_id=:settlementId";
        Map<String, Object> param = Map.of("settlementId", settlementId);
        return template.query(sql, param, expenseRowMapper());
    }

    @Override
    public Expense addExpense(Long settlementId, Expense expense) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", expense.getName())
                .addValue("settlement_id", settlementId)
                .addValue("member_id", expense.getMemberId())
                .addValue("expense_date_time", expense.getExpenseDateTime())
                .addValue("price", expense.getPrice());
        Number key = jdbcInsert.executeAndReturnKey(params);
        expense.setId(key.longValue());
        return expense;
    }

    @Override
    public void deleteExpense(Long expenseId, Long settlementId) {
        String sql = "delete from expense where id=:id and settlement_id=:settlementId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", expenseId)
                .addValue("settlementId", settlementId);
        int update = template.update(sql, params);
        log.info("delete expenseId={} deleted row={}", expenseId, update);
    }

    @Override
    public void clear() {
        String sql = "truncate expense";
        template.update(sql, Map.of());
    }

    private RowMapper<Expense> expenseRowMapper() {
        return new BeanPropertyRowMapper<>(Expense.class);
    }
}
