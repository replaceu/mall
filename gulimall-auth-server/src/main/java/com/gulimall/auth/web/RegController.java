package com.gulimall.auth.web;

import com.gulimall.auth.constant.AuthConstant;
import com.gulimall.auth.exception.AuthErrorCode;
import com.gulimall.auth.feign.MemberFeignService;
import com.gulimall.auth.feign.ThreadPartFeignService;
import com.gulimall.auth.vo.UserRegisterVo;
import com.gulimall.common.utils.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author aqiang9  2020-09-01
 */
@Controller
@Slf4j
public class RegController {

    @Autowired
    ThreadPartFeignService threadPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;


    @GetMapping("/sms/sendcode")
    @ResponseBody
    public CommonResult<Object> sendCode(@RequestParam("phone") String phone) {
        // 1、接口防刷
//  2、验证码校验
        String code = UUID.randomUUID().toString().substring(0, 5);

//        60 秒过期
        Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent(AuthConstant.SMS_CODE_CACHE_PREFIX + phone, code, 60, TimeUnit.SECONDS);
        // 保存成功
        if (ifAbsent != null && ifAbsent) {
            threadPartFeignService.sendSmsCode(phone, code);
            log.info("验证码： phone: {} , code : {}  ", phone, code);
            return CommonResult.ok("发送成功");
        } else {
            return CommonResult.fail(AuthErrorCode.SMS_FREQUENCY_TOO_HIGH_ERROR);
        }
    }

    /**
     * redirectAttributes  ： 利用 session 进行处理
     *
     * @param userRegisterVo     注册信息
     * @param result             错误消息
     * @param redirectAttributes 重定向
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterVo userRegisterVo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            //
            Map<String, String> errorMap = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
//            model.addAttribute("errors" , errorMap ) ;
            // Request method ‘POST’ not suppors
//            请求转发 的区别
            redirectAttributes.addAttribute("errors", errorMap);

            return "redirect:http://auth.gulimall.com/reg.html";
        }

        // 真正的 注册功能

//        1、比对验证码
        String codeKey = AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterVo.getMobile();

        String code = redisTemplate.opsForValue().get(codeKey);

        Map<String, String> errorMessage = new HashMap<>(4);
        if (StringUtils.isEmpty(code)) {
            errorMessage.put("code", "验证码已失效, 请从新获取！！！");
            redirectAttributes.addAttribute("errors", errorMessage);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        if (!code.equals(userRegisterVo.getCode())) {
            errorMessage.put("code", "验证码错误！！！");
            redirectAttributes.addAttribute("errors", errorMessage);
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        // 调用注册逻辑
        CommonResult<Object> register = memberFeignService.register(userRegisterVo);
        // 登陆成功
        //  TODO 已登录
        if (register.isOk()) {
            // 清除redis 缓存
            redisTemplate.delete(codeKey);
            // 放入缓存就可以了
            return "redirect:http://auth.gulimall.com/login.html";
        }
        redirectAttributes.addAttribute("msg", register.getMsg());
        return "redirect:http://auth.gulimall.com/reg.html";
    }

}
