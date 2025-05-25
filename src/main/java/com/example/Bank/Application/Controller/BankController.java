package com.example.Bank.Application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Bank.Application.Entity.AccountDetails;
import com.example.Bank.Application.Entity.LoginDetails;
import com.example.Bank.Application.Entity.Transaction;
import com.example.Bank.Application.Services.BankServices;
import com.example.Bank.Application.Services.ReportData;

@CrossOrigin
@RestController
@RequestMapping("api/")
public class BankController {

	@Autowired
	BankServices bankServices;

	@PostMapping("login/register")
	public String createUser(@RequestBody LoginDetails userDetails) {
		return bankServices.saveUser(userDetails);
	}
	
	@PostMapping("createAccount")
	public String AccountCreated(@RequestBody AccountDetails accountDetails) {
		return bankServices.accountCreated(accountDetails);
	}
	
	@GetMapping("getAll/account")
	public List<AccountDetails> getAllAccount(@RequestParam("Id") Long id) {
	    return bankServices.getAllAccount(id);
	}
	
	@GetMapping("get/accountTransaction")
	public List<Transaction> getAccountTransaction(@RequestParam("Id") Long id, @RequestParam("mobile") String mobileNumber) {
		return bankServices.getAccountTransaction(id,mobileNumber);
	}
	
	@PostMapping("cashWithdraw")
	public String cashWithDraw(@RequestBody ReportData reportData) {
		return bankServices.getCollectTheCash(reportData);
	}

	@PostMapping("credit")
	public String creditAmount(@RequestBody ReportData reportData) {
		return bankServices.creditAmount(reportData);
	}
	
	@GetMapping("checkBalance")
	public String checkBalance(@RequestParam("mobileNumber") String mobileNumber) {
	    return bankServices.getBalanceAmount(mobileNumber);
	}
	 

}
