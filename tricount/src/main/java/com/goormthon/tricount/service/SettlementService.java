package com.goormthon.tricount.service;

import com.goormthon.tricount.TricountApiErrorCode;
import com.goormthon.tricount.model.Member;
import com.goormthon.tricount.model.Settlement;
import com.goormthon.tricount.repository.SettlementRepository;
import com.goormthon.tricount.utils.TricountApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository repository;

    //create new settle
    public Settlement create(String name) {
        return repository.create(name);
    }

    //find all settlement as list
    public List<Settlement> findAllSettlement() {
        return repository.findAll();
    }

    //find one specific settlement by id
    public Settlement findSettlementById(Long id) {
        return repository.findById(id)
                .stream()
                .filter(s -> s.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new TricountApiException("cannot found settlement", TricountApiErrorCode.NOT_FOUND));
    }

    public List<Member> findSettlementMembers(Long id) {
        List<Member> settlementMembers = repository.findSettlementMembers(id);
        return settlementMembers;
    }

    //delete settlement by id parameter
    public void delete(Long id) {
        try{
            repository.deleteSettlement(id);
        } catch (DataAccessException e){
            log.warn(e.getMessage());
            throw new TricountApiException("cannot found settlement", TricountApiErrorCode.NOT_FOUND);
        }
    }

    //join settlement by settlement and member from session
    public void joinSettlement(Long id, Member member) {
        List<Member> settlementMembers = repository.findSettlementMembers(id);
        if (settlementMembers.stream()
                .noneMatch((m) -> m.getId().equals(member.getId()))
        ) {
            repository.addMemberInSettlement(id, member);
        } else {
            throw new TricountApiException("you are already joining settlement", TricountApiErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    //create new settlement and join this settlement
    public void createAndJoin(String name, Member member) {
        Settlement settlement = repository.create(name);
        repository.addMemberInSettlement(settlement.getId(), member);
    }
}
