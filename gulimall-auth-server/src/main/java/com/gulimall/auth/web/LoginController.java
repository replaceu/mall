package com.gulimall.auth.web;

import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.common.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author aqiang9  2020-09-01
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    MemberFeignService memberFeignService ;

    @PostMapping("/login")
    public String login(UserLoginVo loginVo , RedirectAttributes redirectAttributes){

        CommonResult<Object> login = memberFeignService.login(loginVo);
        if (login.isOk()) {
//            登陆成功
            return "redirect:http://www.gulimall.com" ;
        }

//        Map<>  error = new HashMap<>() ;
        redirectAttributes.addAttribute("msg" , login.getMsg() ) ;
        return "redirect:http://auth.gulimall.com/login.html" ;
    }



}
