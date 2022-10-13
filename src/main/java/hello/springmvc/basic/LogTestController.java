package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//view를 제공하지 않는 Controller
//lombok이 제공하는 로그, private final Logger log = LoggerFactory.getLogger(getClass()); 대신 쓰면 됨.
@Slf4j
@RestController
public class LogTestController {
//    getClass() = LogTestController.class
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        //운영 중에 이게 남아 있으면 100명치가 남아서 어지러움. log만 남기기 위해 배포 전에는 다 주석 하거나 삭제.
//        System.out.println("name = " + name);
        log.trace("trace log={}", name);
/**
 *  log.trace("trace log={}"+ name); 잘못된 이유
 *  + 를 쓰게되면 trace를 출력하지 않아도 덧셈 연산이 적용되어서 쓸모없는 곳에 리소스를 사용하게 됨.
 * 그래서 메서드에 파라미터를 넘기는 식이로 동작 시켜야 함
 */
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }

}
