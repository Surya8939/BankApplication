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
@Table(name="AccountDetails")
public class AccountDetails {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "mobileNumber", unique = true)
	private String mobileNumber;
	
	@Column(name = "emailId", unique = true)
	private String emailId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "loginId", referencedColumnName = "id")
	private LoginDetails login;
	
	@Column(name = "password")
	private String password;
	
	
}
