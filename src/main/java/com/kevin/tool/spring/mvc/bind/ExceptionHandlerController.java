package com.kevin.tool.spring.mvc.bind;

import org.springframework.format.datetime.DateFormatter;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *   系统异常处理
 *
 * @author lihongmin
 * @date 2019/12/30 13:56
 * @since 1.0.0
 */
@ControllerAdvice(basePackages = "com.kevin.tool")
public class ExceptionHandlerController {

    /**
     *  错误后返回json
     *  如果想跳转到专门的异常界面，则可以返回{@link org.springframework.web.servlet.ModelAndView}
     *
     * @return 标准异常json
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handler() {
        Map<String, Object> errorMap= new HashMap(16);
        errorMap.put("code", "500");
        errorMap.put("msg", "系统异常，请稍后重试");
        return errorMap;
    }

    @InitBinder("date")
    public void globalInitBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("msg", "hello");

        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hangge");
        map.put("age", "100");
        model.addAttribute("info", map);
    }

    /*@ModelAttribute(value = "msg")
    public String message() {
        return "hello";
    }

    @ModelAttribute(value = "info")
    public Map<String, String> userinfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "hangge");
        map.put("age", "100");
        return map;
    }*/

}
