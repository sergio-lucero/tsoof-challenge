package com.sooft.challenge.repository;

import com.sooft.challenge.model.Transfer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID>,
    JpaSpecificationExecutor<Transfer> {

}
