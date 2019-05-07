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
    public String doLogin(Model model, @RequestParam(value = "username", required = false) String email,
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
        User user = userService.login(email, password);
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

    @RequestMapping("/checkLoginCode")
    @ResponseBody
    public Map<String, Object> checkLoginCode(Model model, @RequestParam(value = "code", required = false) String code) {
        log.debug("注册-判断验证码" + code + "是否可用");
        Map map = new HashMap<String, Object>();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /*通过 session.getAttribute 方法获取之前生成验证码时存入 session 中的验证码，赋值给 vcode
        * 存入session的地方是在加载注册界面时调用的CodeCaptchaServlet工具类*/
        String vcode = (String) attrs.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        /*判断前台传来的验证码和 Session 中存入的验证码是否相同。如果相同则代表验证码输入正确*/
        if (code.equals(vcode)) {
            map.put("message", "success");
        } else {
            map.put("message", "fail");
        }

        return map;
    }

    /**
     * 判断邮箱是否已经被注册
     *
     * @param model
     * @param email
     * @return
     */
    @RequestMapping("/checkLoginEmail")
    @ResponseBody
    public Map<String, Object> checkLoginEmail(Model model, @RequestParam(value = "email", required = false) String email) {
        log.debug("注册-判断邮箱" + email + "是否存在");
        Map map = new HashMap<String, Object>();
        User user = userService.findByEmail(email);
        if (user == null) {
            //未注册
            map.put("message", "fail");
        } else {
            //已注册
            map.put("message", "success");
        }

        return map;
    }

}
