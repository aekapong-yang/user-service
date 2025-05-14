package com.example.userservice.utils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {
    private static final Set<String> SENSITIVE_FIELDS = Set.of("username", "password", "email");

    public static String maskSensitiveJson(String json) {
        if (StringUtils.isBlank(json)) {
            return StringUtils.EMPTY;
        }

        Configuration config = Configuration.builder()
                .options(Option.SUPPRESS_EXCEPTIONS)
                .build();

        DocumentContext context = JsonPath.using(config).parse(json);
        for (String field : SENSITIVE_FIELDS) {
            context.set("$.." + field, "***");
        }

        return context.jsonString();
    }
}
