package org.project.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class ServletService {

    @SneakyThrows
    public static String getBody(HttpServletRequest req) {
        String jsonBody = new BufferedReader(new InputStreamReader(req.getInputStream())).lines().collect(
                Collectors.joining("\n"));
        if (jsonBody == null || jsonBody.trim().length() == 0) {
            log.error("Empty body");
            throw new RuntimeException("Empty body");
        }
        return jsonBody;
    }
    public static void setJsonResponse(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json");
    }
}
