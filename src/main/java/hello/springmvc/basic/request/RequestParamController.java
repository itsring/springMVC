package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, abe={}", username, age);
        response.getWriter().write("ok");


    }
    //↓ 개선
    @ResponseBody // @Controller + @ResponseBody = @RestController
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge
    ){
        log.info("username={}, age{}", memberName, memberAge);
        return "ok";
    }
    //↓ 개선
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username, //    @RequestParam 괄호를 생략하려면 변수명이 같아야됨
            @RequestParam int age
    ){
        log.info("username={}, age{}", username, age);
        return "ok";
    }
    //↓ 개선
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4( // @RequestParam을 생략하려면 요청 파라미터 이름과 같아야됨
            String username,
            int age
    ){
        log.info("username={}, age{}", username, age);
        return "ok";
    }
    //↓ 개선
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired( // @RequestParam(required=true(default) / false)
                                  @RequestParam(required = false) String username,
                                        @RequestParam(required = true) Integer age // int 와 Integer의 차이 : null을 넣을수 있으면 Integer(객체형) , int(변수형)는 무조건 값이 들어가야됨.
    ){
        // required=true일때 빈 문자를 조심해야됨. null != ""  빈 문자는 통과
        log.info("username={}, age{}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault( // 빈 문자일 경우 defaultValue로 인식됨
                                        @RequestParam(required = false, defaultValue = "guest") String username,
                                        @RequestParam(required = true , defaultValue = "-1") Integer age
    ){
        // required=true일때 빈 문자를 조심해야됨. null != ""  빈 문자는 통과
        log.info("username={}, age{}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap // 모든 파라미터를 받고싶을 경우. 필요한 파라미터를 .get()으로 뽑아냄
            ){// 파라미터 값이 1개가 확실하다면 Map, 확실하지 않으면 MultiValueMap 사용
        // required=true일때 빈 문자를 조심해야됨. null != ""  빈 문자는 통과
        log.info("username={}, age{}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(
            @RequestParam String username, @RequestParam int age
    ){
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age{}", helloData.getUsername(), helloData.getAge());
//        log.info("username={}, age{}", helloData); 이것도 됨
        return "ok";
    }
    // 보통 ↑ 이렇게 만듬, 귀찮아서 만든게 @ModelAttribute 사용 법 ↓
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(
           @ModelAttribute HelloData helloData
    ){
        log.info("username={}, age{}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
    // Binding Exception = int인데 String 들어가면 나는 오류
    @ResponseBody
    @RequestMapping("/model-attribute-v3")
    public String modelAttributeV3(
            HelloData helloData // @ModelAttribute 생략 가능
    ){
        log.info("username={}, age{}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
    // String, int, Integer같은 단순한 타입은 @RequestParam으로 인식
    // 나머지는 @ModelAttribute로 인식한다.


}
