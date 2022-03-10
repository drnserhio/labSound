package com.sound.labsound.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sound.labsound.model.CustomHttpResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class CustomEntyPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        CustomHttpResponse res =
                new CustomHttpResponse(
                        FORBIDDEN.value(),
                        FORBIDDEN,
                        FORBIDDEN.getReasonPhrase(),
                        ""
                );
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());

        OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, res);
        outputStream.flush();
    }
}
