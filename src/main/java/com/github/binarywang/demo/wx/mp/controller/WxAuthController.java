package com.github.binarywang.demo.wx.mp.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Edward
 */
@AllArgsConstructor
@RestController
@RequestMapping("/wx/auth/{appid}")
public class WxAuthController {
    private final WxMpService wxService;

    @RequestMapping("/user")
    public ResultModel<WxOAuth2UserInfo> greetUser(@PathVariable String appid, @RequestParam String code) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        ResultModel res = new ResultModel<WxOAuth2UserInfo>();
        try {
            WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            System.out.println(accessToken);
            WxOAuth2UserInfo user = wxService.getOAuth2Service().getUserInfo(accessToken, null);
            System.out.println(user);
            res.setCode(200);
            res.setMessage("操作成功");
            res.setData(user);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return res;
    }
}
