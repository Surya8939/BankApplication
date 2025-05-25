package com.example.Bank.Application.Services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Bank.Application.Entity.AccountDetails;
import com.example.Bank.Application.Entity.LoginDetails;
import com.example.Bank.Application.Entity.Transaction;
import com.example.Bank.Application.Repo.AccountRepo;
import com.example.Bank.Application.Repo.LoginRepo;
import com.example.Bank.Application.Repo.TransactionRepo;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BankServices {

//	@Autowired
//	ResponseData responseData;
	@Autowired
	LoginRepo loginRepo;
	@Autowired
	AccountRepo accountRepo;
	@Autowired
	TransactionRepo transactionRepo;

	public String  saveUser(LoginDetails userDetails) {
		Optional<LoginDetails> login = loginRepo.findByGmailIdANDMobileNumber(userDetails.getEmailId(),
				userDetails.getMobileNumber());
		if (login.isEmpty()) {
			loginRepo.save(userDetails);
			return "Success";
		}
		return "Already_UseThis_EmailId_AND_Mobile_Number";
	}

	public String accountCreated(AccountDetails accountDetails) {
		Optional<AccountDetails> existingAccount = accountRepo.findByMobileNumber(accountDetails.getMobileNumber());
		if (existingAccount.isPresent()) {
			return "Already_useThis_mobileNumber_kindly_giveMe_a_another_MobileNumber";
		}
		if (accountDetails.getLogin() == null || accountDetails.getLogin().getId() == null) {
			return "Login details missing. Please provide valid login ID.";
		}

		Long loginId = accountDetails.getLogin().getId();
		Optional<LoginDetails> loginOpt = loginRepo.findById(loginId);
		if (loginOpt.isEmpty()) {
			return "LoginDetails with id " + loginId + " not found.";
		}

		accountDetails.setLogin(loginOpt.get());

		// Now save accountDetails with proper login reference
		accountRepo.save(accountDetails);

		return "Success";
	}

	public List<AccountDetails> getAllAccount(Long id) {
	    return accountRepo.getAllAccount(id);
	}

	public List<Transaction> getAccountTransaction(Long accountId, String mobileNumber) {
		List<Transaction> transation = transactionRepo.getAllTransation(accountId);
		if (transation.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No transactions found for given account and mobile number.");
		}
		return transation;
	}

	public String getCollectTheCash(ReportData reportData) {
		Optional<AccountDetails> accountOpt = accountRepo.findByNameAndPassword(reportData.getName(),
				reportData.getPassword());
		if (!accountOpt.isPresent()) {
			return "Invalid name or password";
		}

		AccountDetails account = accountOpt.get();
		Optional<Transaction> latestTransaction = transactionRepo.findTopByAccountOrderByIdDesc(account);
		Double currentBalance = latestTransaction.map(Transaction::getAvailableAmount).orElse(0.0);
		if (currentBalance < reportData.getAmount()) {
			return "Insufficient balance";
		}
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setDebitAmount(reportData.getAmount());
		transaction.setCreditAmount(0.0);
		transaction.setAvailableAmount(currentBalance - reportData.getAmount());

		transactionRepo.save(transaction);

		return "Withdrawal successful. New balance: " + transaction.getAvailableAmount();
	}

	public String creditAmount(ReportData reportData) {
		Optional<AccountDetails> optionalAccount = accountRepo.findByMobileNumber(reportData.getMobileNumber());

		if (!optionalAccount.isPresent()) {
			throw new RuntimeException("Account not found with mobile number: " + reportData.getMobileNumber());
		}
		AccountDetails account = optionalAccount.get();

		Optional<Transaction> latestTransaction = transactionRepo.findTopByAccountOrderByIdDesc(account);

		double currentBalance = latestTransaction.map(Transaction::getAvailableAmount).orElse(0.0);
		double creditAmount = reportData.getAmount();
		double newBalance = currentBalance + creditAmount;
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setCreditAmount(creditAmount);
		transaction.setAvailableAmount(newBalance);
		transaction.setDebitAmount(0.0); // No debit

		transactionRepo.save(transaction);

		return "Amount credited successfully. New Balance: " + newBalance;
	}
	
	public String getBalanceAmount(String mobileNumber) {
	    Optional<AccountDetails> optionalAccount = accountRepo.findByMobileNumber(mobileNumber);

	    if (!optionalAccount.isPresent()) {
	        throw new RuntimeException("Account not found with mobile number: " + mobileNumber);
	    }

	    AccountDetails account = optionalAccount.get();
	    Optional<Transaction> latestTransaction = transactionRepo.findTopByAccountOrderByIdDesc(account);

	    Double availableAmount = latestTransaction.map(Transaction::getAvailableAmount).orElse(0.0);
	    return "Available Balance: " + availableAmount;
	}


}
