package com.gulimall.auth.feign;

import com.gulimall.auth.vo.SocialUser;
import com.gulimall.auth.vo.UserLoginVo;
import com.gulimall.auth.vo.UserRegisterVo;
import com.gulimall.common.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author aqiang9  2020-09-02
 */
@FeignClient("gulimall-member")
@RequestMapping("/member")
public interface MemberFeignService {


    @PostMapping("/member/register")
    CommonResult<Object> register(@RequestBody UserRegisterVo registerVo);


    @PostMapping("/member/login")
    CommonResult<Object> login(@RequestBody UserLoginVo loginVo) ;

    @PostMapping("/oauth2/login")
    CommonResult<Object> oauthLogin(@RequestBody SocialUser socialUser) ;

}
