package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Member;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class JdbcMemberRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Member save(Member member) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("loginId", member.getLoginId())
                .addValue("name", member.getName())
                .addValue("password", member.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(params);
        member.setId(key.longValue());

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id =:id";
        Map<String, Object> param = Map.of("id", id);

        try {
            Member member = jdbcTemplate.queryForObject(sql, param, memberMapper());
            return Optional.ofNullable(member);
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "select * from member where login_id= :loginId";
        Map<String, Object> param = Map.of("loginId", loginId);
        List<Member> members = jdbcTemplate.query(sql, param, memberMapper());
        return members.stream().findAny();
    }

    private RowMapper<Member> memberMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
