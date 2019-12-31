package com.kevin.tool.spring.mvc.bind;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 *  {@link org.springframework.web.bind.annotation.ControllerAdvice}  相关请求Demo
 *
 * @author lihongmin
 * @date 2019/12/30 13:57
 * @since 1.0.0
 */
//@RestController
public class ControllerAdviceDemoController {

    @ResponseBody
    @RequestMapping("bindException")
    public String bindException() {
        getMessage();
        return "ok";
    }

    private void getMessage() {
         throw new RuntimeException("未知异常！");
    }

    /**
     *  {@link ModelAttribute} 注释参数
     *
     * @param str 请参数
     * @return 结果
     */
    @ResponseBody
    @GetMapping("modelAttribute/{str}")
    public String modelAttribute(@ModelAttribute("str") @PathVariable("str") String str) {
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "/initBind", method = RequestMethod.GET)
    public String detail(@RequestParam("id") long id, Date date) {

        System.out.println(date);
        System.out.println(id);

        return "ok";
    }

    @GetMapping("/modelAttributeTest")
    private String hello(@ModelAttribute("msg") String msg,
                        @ModelAttribute("info") Map<String, String> info) {
        String result = "msg：" + msg + "<br>" + "info：" + info;
        return result;
    }
}
