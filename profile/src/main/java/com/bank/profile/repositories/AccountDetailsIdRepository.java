package com.bank.profile.repositories;


import com.bank.profile.models.Account_details_id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsIdRepository extends JpaRepository<Account_details_id, Long> {
}
