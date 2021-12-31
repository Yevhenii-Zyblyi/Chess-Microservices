package com.claims.serviceclaims.service;

import com.claims.serviceclaims.repo.ClaimsRepo;
import com.claims.serviceclaims.repo.model.Claim;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClaimService {
    private final ClaimsRepo claimsRepo;

    public List<Claim> fetchAll() {
        return claimsRepo.findAll();
    }

    public Claim fetchClaimById(long id) throws IllegalArgumentException {
        final Optional<Claim> maybeClaim = claimsRepo.findById(id);

        if (maybeClaim.isPresent()) return maybeClaim.get();
        else
            throw new IllegalArgumentException("Invalid Claim Id");
    }

    public List<Claim> fetchClaimsByUserId(Long id) throws IllegalArgumentException {
        final List<Claim> maybeClaims = claimsRepo.findByViolatorId(id);

        if (maybeClaims.isEmpty()) throw new IllegalArgumentException("Invalid Claim Id");
        else {
            return maybeClaims;
        }
    }

    public long createClaim(String reason, String description, long violator_id) {
        final Claim claim = new Claim(reason, description, violator_id);
        final Claim savedClaim = claimsRepo.save(claim);

        return savedClaim.getId();
    }

    public void updateClaim(long id, String reason, String description, long violatorId) throws IllegalArgumentException {
        final Optional<Claim> maybeClaim = claimsRepo.findById(id);

        if (maybeClaim.isEmpty())
            throw new IllegalArgumentException("Invalid Claims Id");

        final Claim claim = maybeClaim.get();
        if (reason != null && !reason.isBlank()) claim.setReason(reason);
        if (description != null && !description.isBlank()) claim.setDescription(description);
        if (violatorId > 0) claim.setViolator_id(violatorId);

        claimsRepo.save(claim);
    }

    public void deleteClaim(long id ){
        claimsRepo.deleteById(id);
    }
}
