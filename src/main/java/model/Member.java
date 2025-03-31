package model;

import java.util.List;

public class Member {
	private Long id;
	private String firstName;
	private String lastName;
	private String membershipNumber;
	private List<Loan> loans;

	public Member() {
	}

	public Member(Long id, String firstName, String lastName, String membershipNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.membershipNumber = membershipNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	@Override
	public String toString() {
		return "Member{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
				+ ", membershipNumber='" + membershipNumber + '\'' + '}';
	}
}