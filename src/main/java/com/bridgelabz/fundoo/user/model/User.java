package com.bridgelabz.fundoo.user.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="user_details")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@NotEmpty(message="please fill the firstname")
	private String firstName;
	
	@NotEmpty(message="please fill the lastname")
	private String lastName;
	
	//@UniqueElements
	@Email(message="Please enter valid emailid")
	@Column(unique = true, nullable=false)
	@NotEmpty(message="please fill the email")
	private String email;
	
	@Pattern(regexp = "[0-9]{10}", message = "Number Should Only Be Digit And 10 digit only")
	@NotEmpty(message="please fill the mobilenumber")
	private String mobileNumber;
	
	@NotEmpty(message="please fill the password")
	private String password;
	
	@JsonIgnore
	private LocalDate registeredDate;
	
	@JsonIgnore
	private LocalDate accountUpdateDate;
	
	private boolean isVerification;
	
	private String profileImage;

	public User() {
		super();
	}

	public User(Long userId,  String firstName, String lastName, String email, String mobileNumber, String password, LocalDate registeredDate,LocalDate accountUpdateDate, boolean isVerification, String profileImage) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.password = password;
		this.registeredDate = registeredDate;
		this.accountUpdateDate = accountUpdateDate;
		this.isVerification = isVerification;
		this.profileImage = profileImage;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDate registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDate getAccountUpdateDate() {
		return accountUpdateDate;
	}

	public void setAccountUpdateDate(LocalDate accountUpdateDate) {
		this.accountUpdateDate = accountUpdateDate;
	}

	public boolean isVerification() {
		return isVerification;
	}

	public void setVerification(boolean isVerification) {
		this.isVerification = isVerification;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
}
