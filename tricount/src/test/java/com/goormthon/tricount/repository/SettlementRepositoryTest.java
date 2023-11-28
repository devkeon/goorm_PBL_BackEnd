package com.goormthon.tricount.repository;

import com.goormthon.tricount.model.Settlement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class SettlementRepositoryTest {

    @Autowired
    private final SettlementRepository settlementRepository;

    @BeforeEach
    void beforeEach(){
        settlementRepository.create("test1");
        settlementRepository.create("test2");
    }

    @AfterEach
    void afterEach(){
        settlementRepository.clear();
    }

    @Test
    void findAllSettlementTest(){
        List<Settlement> allSettlement = settlementRepository.findAll();
        assertThat(allSettlement.size()).isEqualTo(2);
    }

}
