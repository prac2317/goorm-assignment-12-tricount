package com.example.tricount.repository;

import com.example.tricount.domain.Member;
import com.example.tricount.domain.Settlement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class SettlementRepositoryTest {

    @Autowired
    SettlementRepository settlementRepository;

    @Test
    void save() {
        Settlement settlement = new Settlement("title");
        Settlement savedSettlement = settlementRepository.save(settlement);
        Settlement findBySettlement = settlementRepository.findById(savedSettlement.getSettlementNo()).get();
        Assertions.assertThat(findBySettlement.getTitle()).isEqualTo(savedSettlement.getTitle());
    }

    @Test
    void delete() {
        Settlement settlement = new Settlement("title");
        Settlement savedSettlement = settlementRepository.save(settlement);

        settlementRepository.delete(savedSettlement.getSettlementNo());
        Optional<Settlement> deletedSettlementOptional = settlementRepository.findById(savedSettlement.getSettlementNo());
        Assertions.assertThat(deletedSettlementOptional).isEmpty();
    }
}
