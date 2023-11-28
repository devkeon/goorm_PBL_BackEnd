package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.model.Settlement;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;

@Repository
public class JdbcSettlementRepository implements SettlementRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcSettlementRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("settlement")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Settlement> findAll() {
        String sql = "select * from settlement";
        List<Settlement> settlements = template.query(sql, settlementMapper());
        return settlements;
    }

    @Override
    public Optional<Settlement> findById(Long id) {
        String sql = "select * from settlement where id=:id";
        Map<String, Object> param = Map.of("id", id);
        List<Settlement> settlements = template.query(sql, param, settlementMapper());
        return settlements.stream()
                .findAny();
    }

    @Override
    public Settlement create(String name) {
        Map<String, Object> param = Map.of("name", name);
        Number id = jdbcInsert.executeAndReturnKey(param);
        Settlement settlement = new Settlement(id.longValue(), name);
        return settlement;
    }

    @Override
    public void addMemberInSettlement(Long id, Member member) {
        String sql = "insert into member_settlement (settlement_id, member_id) values (:settlementId, :memberId)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("settlementId", id)
                .addValue("memberId", member.getId());
        template.update(sql, params);
    }

    // Used for getting balance
    @Override
    public List<Member> findSettlementMembers(Long settlementId) {
        String sql = "select m.id as id, m.login_id as login_id, m.password as password, m.name as name from member_settlement as s" +
                " join member m on m.id=s.member_id where settlement_id=:settlementId";
        Map<String, Object> param = Map.of("settlementId", settlementId);
        List<Member> members = template.query(sql, param, memberMapper());
        return members;
    }

    // if there is participant in settlement, it could cause error
    // because member_settlement have FK for settlement_id
    //so need to erase member_settlement that have equal settlement ids
    @Override
    public void deleteSettlement(Long id) {
        Map<String, Object> param = Map.of("id", id);
        String sqlMemberSettlement = "delete from member_settlement where settlement_id=:id";
        String sqlExpense = "delete from expense where settlement_id:id";
        template.update(sqlMemberSettlement,param);
        template.update(sqlExpense, param);
        String sql = "delete from settlement where id=:id";
        template.update(sql, param);
    }

    @Override
    public void clear() {
        template.update("truncate settlement", Map.of());
    }

    private RowMapper<Settlement> settlementMapper() {
        return BeanPropertyRowMapper.newInstance(Settlement.class);
    }

    private RowMapper<Member> memberMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
