package com.fsoft.cursomc.controllers.utils;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QueryParams {

	public static String decodeParam(String param) {
		String converted = param;
		
		try  {
			converted = URLDecoder.decode(param, "UTF-8");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return converted;
	}
	
	public static List<Integer> convertIntegers(String param) { 
		return Arrays.asList(param.split(",")).stream().map(p -> Integer.parseInt(p)).collect(Collectors.toList());
	}
}
