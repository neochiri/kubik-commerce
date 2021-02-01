package com.ryc.user.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileJsonConverter {

	public static Object getObjectFromJsonFile(String filePath, Class clazz){
		try {
			ObjectMapper mapper = new ObjectMapper();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			File fileObject = new File(classLoader.getResource(filePath).getFile());
			return mapper.readValue(fileObject, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getJsonFromObject(Object object) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
