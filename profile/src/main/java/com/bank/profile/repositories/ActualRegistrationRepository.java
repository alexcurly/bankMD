package com.bank.profile.repositories;


import com.bank.profile.models.Actual_registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualRegistrationRepository extends JpaRepository<Actual_registration, Long> {
}
