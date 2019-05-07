package wang.dreamland.www.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.dreamland.www.common.CodeCaptchaServlet;
import wang.dreamland.www.common.Constants;
import wang.dreamland.www.common.MD5Util;
import wang.dreamland.www.common.StringUtil;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 徐杰
 * @Date: 2019/5/7 0007 23:50
 */
@Controller
public class LoginController extends BaseController {
    private final static Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/doLogin")
    public String doLogin(Model model, @RequestParam(value = "email", required = false) String email,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "code", required = false) String code,
                          @RequestParam(value = "state", required = false) String state,
                          @RequestParam(value = "pageNum", required = false) Integer pageNum,
                          @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error", "fail");
            return "../login";
        }
        int b = checkValidateCode(code);
        if (b == -1) {
            model.addAttribute("error", "fail");
            return "../login";
        } else if (b == 0) {
            model.addAttribute("error", "fail");
            return "../login";
        }
        password = MD5Util.encodeToHex(Constants.SALT + password);
        /*根据email和password判断*/
        User user = userService.login(StringUtil.safeToString(email,""), StringUtil.safeToString(password,""));
        if (user != null) {
            if ("0".equals(user.getState())) {
                //未激活
                model.addAttribute("email", email);
                model.addAttribute("error", "active");
                return "../login";
            }
            log.info("用户登录登录成功");
            model.addAttribute("user", user);
            return "/personal/personal";
        } else {
            log.info("用户登录登录失败");
            model.addAttribute("email", email);
            model.addAttribute("error", "fail");
            return "../login";
        }
    }

    // 匹对验证码的正确性
    public int checkValidateCode(String code) {
        Object vercode = getRequest().getSession().getAttribute("VERCODE_KEY");
        if (null == vercode) {
            return -1;
        }
        if (!code.equalsIgnoreCase(vercode.toString())) {
            return 0;
        }
        return 1;
    }

}
