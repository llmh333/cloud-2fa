package org.example.cloud2fa.repository;

import java.util.List;

import org.example.cloud2fa.domain.entity.TotpAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotpAccountRepository extends JpaRepository<TotpAccount, String> {

   List<TotpAccount> findByUserId(String id);

}
