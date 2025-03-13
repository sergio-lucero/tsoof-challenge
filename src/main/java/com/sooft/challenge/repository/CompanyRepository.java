package com.sooft.challenge.repository;

import com.sooft.challenge.model.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID>,
    JpaSpecificationExecutor<Company> {

}
