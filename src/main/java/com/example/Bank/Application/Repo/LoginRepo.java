package com.example.Bank.Application.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Bank.Application.Entity.LoginDetails;

@Repository
public interface LoginRepo extends JpaRepository<LoginDetails, Long>{

	
	@Query("SELECT l FROM LoginDetails l WHERE l.emailId = :emailId AND l.mobileNumber = :mobileNumber")
	Optional<LoginDetails> findByGmailIdANDMobileNumber(@Param("emailId") String emailId, @Param("mobileNumber") String mobileNumber);

}
