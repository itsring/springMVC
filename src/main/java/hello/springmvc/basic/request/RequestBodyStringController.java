package hello.springmvc.basic.request;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // String 인코딩은 항상 Byte 코드 - > 어떤 인코딩으로 받을건지 생각할 것

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

// 위와 같은 코드
//HttpServletRequest -> InputStream (Servlet이 필요한게 아니라면)
    //HttpServletResponse -> Writer
    @PostMapping("request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");

    }

//message converter
    @PostMapping("request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("ok");

    }
    @PostMapping("request-body-string-v3_1")
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        return new ResponseEntity<>("ok",HttpStatus.CREATED);

    }
    // @RequestBody : 요청 / 헤더 정보가 필요하다면 HttpEntity를 사용하던가 @RequestHeader 사용
    // @ResponseBody : 응답
    @ResponseBody
    @PostMapping("request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {


        log.info("messageBody={}", messageBody);

        return "ok";

    }

}
