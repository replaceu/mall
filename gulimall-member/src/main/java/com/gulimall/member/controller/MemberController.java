package com.gulimall.member.controller;

import com.gulimall.common.utils.CommonResult;
import com.gulimall.common.utils.R;
import com.gulimall.member.convert.MemberConvert;
import com.gulimall.member.entity.MemberEntity;
import com.gulimall.member.exception.MemberErrorCode;
import com.gulimall.member.service.MemberService;
import com.gulimall.member.vo.MemberLoginVo;
import com.gulimall.member.vo.MemberRegisterVo;
import com.gulimall.service.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 会员
 *
 * @author aqiang9
 * @email ${email}
 * @date 2020-07-27 10:19:25
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    /**
     * 用户注册
     *
     * @param registerVo 注册的用户数据
     * @return
     */
    @PostMapping("/register")
    public CommonResult<Object> register(@RequestBody MemberRegisterVo registerVo) {
        MemberEntity memberEntity = MemberConvert.INSTANCE.vo2entity(registerVo);
        memberService.register(memberEntity);
        return CommonResult.ok();
    }

    @PostMapping("/login")
    public CommonResult<MemberEntity> login(@RequestBody MemberLoginVo  loginVo) {
        MemberEntity memberEntity =  memberService.login(loginVo.getLoginAccount()  , loginVo.getPassword() );
        if (memberEntity == null) {
           return CommonResult.fail(MemberErrorCode.LOGIN_PASSWORD_INVALID_EXCEPTION ) ;
        }
        return CommonResult.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/list")
//    @RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
