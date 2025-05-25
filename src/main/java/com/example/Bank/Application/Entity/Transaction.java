package com.example.Bank.Application.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
@Getter
@Setter
@Entity
@Table(name="Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "availableAmount")
	private Double availableAmount;
	
	@Column(name = "debitAmount")
    private Double debitAmount;
	
	@Column(name = "creditAmount")
    private Double creditAmount;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "accountId", referencedColumnName = "id")
	private AccountDetails account;
}
