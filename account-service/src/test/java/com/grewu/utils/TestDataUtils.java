package com.grewu.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Objects;

@UtilityClass
public class TestDataUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String ACCOUNT_REQUEST = "";
    public static final String ACCOUNT_RESPONSE = "/data/account_response.json";
    public static final String RESPONSE_USER_DTO_01 = "/data/response_user_dto_01.json";
    public static final String RESPONSE_USER_DTO_02 = "/data/response_user_dto_02.json";
    public static final String USER_01 = "/data/user_01.json";
    public static final String USER_01_BEFORE_SAVE = "/data/user_01_before_save.json";

    @SneakyThrows
    public <T> T getTestData(Class<T> t, String filename) {
        InputStream resourceAsStream = TestDataUtils.class.getResourceAsStream(filename);
        Objects.requireNonNull(resourceAsStream, "Can not find resources/" + filename);
        return MAPPER.readValue(resourceAsStream, t);
    }
}
