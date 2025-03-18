package model;

import java.util.List;

public class Member {
	private String id;
	private String firstName;
	private String lastName;
	private String membershipNumber;
	private List<Loan> loans;

	public Member(String id, String firstName, String lastName, String membershipNumber, List<Loan> loans) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.membershipNumber = membershipNumber;
		this.loans = loans;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

}
