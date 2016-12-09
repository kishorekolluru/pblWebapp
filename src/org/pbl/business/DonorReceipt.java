package org.pbl.business;

public class DonorReceipt {

	private String donorName;

	private String donorAddress;

	private String donatedOn;

	private String receiptDate;

	private String receiptNo;

	private String item;

	private String email;

	private String taxGift;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDonorName() {
		return donorName;
	}

	public void setDonorName(String donorName) {
		this.donorName = donorName;
	}

	public String getDonorAddress() {
		return donorAddress;
	}

	public void setDonorAddress(String donorAddress) {
		this.donorAddress = donorAddress;
	}

	public String getDonatedOn() {
		return donatedOn;
	}

	public void setDonatedOn(String donatedOn) {
		this.donatedOn = donatedOn;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getTaxGift() {
		return taxGift;
	}

	public void setTaxGift(String taxGift) {
		this.taxGift = taxGift;
	}

}
