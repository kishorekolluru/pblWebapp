package org.pbl.utils;

import java.text.SimpleDateFormat;

public class DAOUtility {
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int ELEVEN = 11;

	public static final String LOGIN_FAILED = "User Email or password is incorrect";
	public static final String USER_EXISTS = "Email already exists";

	public static final String LOGIN_QUERY = "SELECT USERID FROM LOGIN WHERE EMAIL = ? AND PASSWORD = ? AND ISACTIVE =?;";
	public static final String USER_QUERY = "SELECT * FROM USER WHERE USERID = ?";
	public static final String CREATE_USER_QUERY = "INSERT INTO USER(FIRSTNAME,SECONDNAME,ADDRESS,CITY,STATE,ZIP,PHONE,EMAIL,COUNTRY,DOB, USERTYPE) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
	public static final String CREATE_LOGIN_USER_QUERY = "INSERT INTO LOGIN(EMAIL,PASSWORD,USERID,ISACTIVE,RESETPASSWORD) VALUES(?,?,?,?,?);";
	public static final String DONATED_ITEMS_QUERY = "SELECT * FROM ITEM WHERE DONORID = ?";
	public static final String CREATE_ITEM_QUERY = "INSERT INTO ITEM(DESCRIPTION,COLOR,ITEMTYPE,SIZE,BRAND,DATERECEIVED,DONORID,PICTURE,PROCESSED) VALUES(?,?,?,?,?,?,?,?,?);";
	public static final String CREATE_DONOR = "INSERT INTO DONOR(DONORID, DONATIONSMADE) VALUES(?,?);";
	public static final String CREATE_CUSTOMER = "INSERT INTO CUSTOMER(MEMBERID, DESIGNATION) VALUES(?,?);";

	public static final String INCREMENT_DONATED_COUNT = "{call getEmpName (?)}";
	   
	
}
