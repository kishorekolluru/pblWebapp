package org.pbl.utils;

import org.pbl.business.PblResponse;

import com.google.gson.Gson;

public class ServletUtility {
	public static Gson gson = new Gson();

	public static String toJson(Object object) {
		return gson.toJson(object, PblResponse.class);
	}

}
