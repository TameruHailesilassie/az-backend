package com.softech.ehr.repository;
import java.util.Optional;
import com.softech.ehr.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends
    CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);


}
