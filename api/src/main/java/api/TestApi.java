package api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api")
public class TestApi {
    TestApi(){
        System.out.println("testApi");
    }
    @RequestMapping(value="test",produces= MediaType.APPLICATION_JSON_VALUE)
    public Object test(HttpServletRequest httpServletRequest){
        Map<String,String> map = new HashMap<>();
        map.put("name","王玉川");
        map.put("age","21");
        return map;
    }
}
