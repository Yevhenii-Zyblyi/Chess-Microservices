package com.claims.serviceclaims.api;

import com.claims.serviceclaims.api.dro.ClaimDto;
import com.claims.serviceclaims.repo.model.Claim;
import com.claims.serviceclaims.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/claims")
@RestController
public class ClaimController {

    @Autowired
    private final ClaimService claimService;

    @GetMapping
    public ResponseEntity<List<Claim>> index() {
        final List<Claim> claims = claimService.fetchAll();
        return ResponseEntity.ok(claims);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Claim> showById(@PathVariable long id) {
        try {
            final Claim claim = claimService.fetchClaimById(id);

            return ResponseEntity.ok(claim);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Claim>> showClaimsByUserId(@PathVariable Long id) {
        try {
            final List<Claim> claims = claimService.fetchClaimsByUserId(id);

            return  ResponseEntity.ok(claims);
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ClaimDto claim) {
        final String reason = claim.reason();
        final String description = claim.description();
        final long violator_id = claim.violatorId();
        final long claimId = claimService.createClaim(reason, description, violator_id);
        final String claimUri = String.format("/claims/%d", claimId);

        return ResponseEntity.created(URI.create(claimUri)).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> change(@PathVariable long id, @RequestBody ClaimDto claim) {
        final String reason = claim.reason();
        final String description = claim.description();
        final long violator_id = claim.violatorId();

        try {
            claimService.updateClaim(id, reason, description, violator_id);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deleteById(@PathVariable long id) {

        claimService.deleteClaim(id);

        return ResponseEntity.noContent().build();
    }

}
