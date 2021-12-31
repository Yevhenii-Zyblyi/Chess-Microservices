package com.claims.serviceclaims.repo;

import com.claims.serviceclaims.repo.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimsRepo extends JpaRepository<Claim, Long> {
    List<Claim> findByViolatorId(Long violator_id);
}
