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
import org.springframework.data.redis.core.RedisTemplate;
import wang.dreamland.www.common.CodeCaptchaServlet;
import wang.dreamland.www.common.MD5Util;
import wang.dreamland.www.common.StringUtil;
import wang.dreamland.www.entity.User;
import wang.dreamland.www.mail.SendEmail;
import wang.dreamland.www.service.RoleUserService;
import wang.dreamland.www.service.UserService;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 徐杰
 * @Date: 2019/5/5 0005 20:58
 */
/*使用@Controller注解表明这是一个控制器*/
@Controller
public class RegisterController {
    private final static Logger log = Logger.getLogger(RegisterController.class);
    /*@Autowired自动装配接口类*/
    @Autowired
    private UserService userService;

    /*使用 @RequestMapping 来映射 Request 请求与处理器*/
    @RequestMapping("/checkPhone")
    /*@ResponseBody 后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中。
     比如异步获取 json 数据，加上 @ResponseBody 后，会直接返回 json 数据*/
    @ResponseBody
    public Map<String, Object> checkPhone(Model model, @RequestParam(value = "phone", required = false) String phone) {
        log.debug("注册-判断手机号" + phone + "是否可用");
        Map map = new HashMap<String, Object>();
        User user = userService.findByPhone(phone);
        if (user == null) {
            //未注册
            map.put("message", "success");
        } else {
            //已注册
            map.put("message", "fail");
        }

        return map;
    }

    /*同上*/
    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String, Object> checkCode(Model model, @RequestParam(value = "code", required = false) String code) {
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
    @RequestMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(Model model, @RequestParam(value = "email", required = false) String email) {
        log.debug("注册-判断邮箱" + email + "是否可用");
        Map map = new HashMap<String, Object>();
        User user = userService.findByEmail(email);
        if (user == null) {
            //未注册
            map.put("message", "success");
        } else {
            //已注册
            map.put("message", "fail");
        }

        return map;
    }

    @Autowired// redis数据库操作模板
    private RedisTemplate<String, String> redisTemplate;
    @RequestMapping("/doRegister")
    public String doRegister(Model model, @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "password", required = false) String password,
                             @RequestParam(value = "phone", required = false) String phone,
                             @RequestParam(value = "nickName", required = false) String nickname,
                             @RequestParam(value = "code", required = false) String code) {

        log.debug("注册...");
        if (StringUtils.isBlank(code)) {
            model.addAttribute("error", "非法注册，请重新注册！");
            return "../register";
        }

        int b = checkValidateCode(code);
        if (b == -1) {
            model.addAttribute("error", "验证码超时，请重新注册！");
            return "../register";
        } else if (b == 0) {
            model.addAttribute("error", "验证码不正确,请重新输入!");
            return "../register";
        }


        User user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("error", "该用户已经被注册！");
            return "../register";
        } else {
            user = new User();
            user.setNickName(nickname);

            user.setPassword(MD5Util.encodeToHex("salt" + password));
            user.setPhone(phone);
            user.setEmail(email);
            user.setState("0");
            user.setEnable("0");
            user.setImgUrl("/images/icon_m.jpg");
            //邮件激活码
            String validateCode = MD5Util.encodeToHex("salt"+email + password);
            /*往redis中存值
            *  24小时 有效激活 redis保存激活码*/
            redisTemplate.opsForValue().set(email, validateCode, 24, TimeUnit.HOURS);
            userService.regist(user);

            log.info("注册成功");
            SendEmail.sendEmailMessage(email, validateCode);
            String message = email + "," + validateCode;
            model.addAttribute("message", message);
            return "/regist/registerSuccess";

        }
    }

    // 匹对验证码的正确性
    public int checkValidateCode(String code) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object vercode = attrs.getRequest().getSession().getAttribute("VERCODE_KEY");
        if (null == vercode) {
            return -1;
        }
        if (!code.equalsIgnoreCase(vercode.toString())) {
            return 0;
        }
        return 1;
    }

    /*重新发送邮件*/
    @RequestMapping("/sendEmail")
    @ResponseBody
    public  Map<String,Object> sendEmail(Model model) {
        Map map = new HashMap<String,Object>(  );
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String validateCode = StringUtil.safeToString(attrs.getRequest().getParameter( "validateCode" ),"");
        String email =StringUtil.safeToString(attrs.getRequest().getParameter( "email" ),"");
        SendEmail.sendEmailMessage(email,validateCode);
        map.put( "success","success" );
        return map;
    }

    @RequestMapping("/register")
    public String register(Model model) {

        log.info("进入注册页面");

        return "../register";
    }

    @Autowired
    private RoleUserService roleUserService;
    @RequestMapping("/activecode")
    public String active(Model model) {
        log.info( "==============激活验证==================" );
        //判断   激活有无过期 是否正确
        //validateCode=
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /*通过 request.getParameter 方法获取请求参数：邮箱和激活码*/
        String validateCode = StringUtil.safeToString(attrs.getRequest().getParameter( "validateCode" ),"");
        String email =StringUtil.safeToString(attrs.getRequest().getParameter( "email" ),"");
        /*通过键 email 去 Redis 中取出之前存入 Redis 中的激活码，赋值给 code。*/
        String code = redisTemplate.opsForValue().get( email );
        log.info( "验证邮箱为："+email+",邮箱激活码为："+code+",用户链接的激活码为："+validateCode );
        //判断是否已激活

        User userTrue = userService.findByEmail( email );
        if(userTrue!=null && "1".equals( userTrue.getState() )){
            //已激活
            model.addAttribute( "success","您已激活,请直接登录！" );
            return "../login";
        }

        if(code==null){
            //激活码过期
            model.addAttribute( "fail","您的激活码已过期,请重新注册！" );
            userService.deleteByEmail( email );
            return "/regist/activeFail";
        }

        if(StringUtils.isNotBlank( validateCode ) && validateCode.equals( code )){
            //激活码正确
            userTrue.setEnable( "1" );
            userTrue.setState( "1" );
            userService.update( userTrue );
            model.addAttribute( "email",email );
            return "/regist/activeSuccess";
        }else {
            //激活码错误
            model.addAttribute( "fail","您的激活码错误,请重新激活！" );
            return "/regist/activeFail";
        }

    }
}