package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.model.Settlement;

import java.util.List;
import java.util.Optional;

public interface SettlementRepository {

    public List<Settlement> findAll();

    public Optional<Settlement> findById(Long id);

    public Settlement create(String name);

    public void addMemberInSettlement(Long id, Member member);

    public List<Member> findSettlementMembers(Long settlementId);

    public void deleteSettlement(Long id);

    public void clear();

}
