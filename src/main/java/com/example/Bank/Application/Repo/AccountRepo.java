package com.example.Bank.Application.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Bank.Application.Entity.AccountDetails;

@Repository
public interface AccountRepo extends JpaRepository<AccountDetails, Long> {

	@Query("SELECT a FROM AccountDetails a WHERE a.mobileNumber = :mobileNumber")
	Optional<AccountDetails> findByMobileNumber(@Param("mobileNumber") String mobileNumber);

	@Query("SELECT a FROM AccountDetails a WHERE a.login.id = :id")
	List<AccountDetails> getAllAccount(@Param("id") Long id);
	
	@Query("SELECT a FROM AccountDetails a WHERE a.name = :name AND a.password = :password")
	Optional<AccountDetails> findByNameAndPassword(@Param("name") String name, @Param("password") String password);

}
