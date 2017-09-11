package cn.bin2.demo.web;

import cn.bin2.demo.common.Const;
import cn.bin2.demo.common.aop.LoggerManage;
import cn.bin2.demo.domain.User;
import cn.bin2.demo.domain.result.ExceptionMsg;
import cn.bin2.demo.domain.result.ResponseData;
import cn.bin2.demo.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bing on 2017/9/8.
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LoggerManage(description="登陆")
    public ResponseData login(User user, HttpServletResponse response) {
        try {
            User loginUser = userRepository.findByUserNameOrEmail(user.getUserName(), user.getUserName());
            if (loginUser == null ) {
                return new ResponseData(ExceptionMsg.LoginNameNotExists);
            }else if(!loginUser.getPassWord().equals(getPwd(user.getPassWord()))){
                return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
            }
            Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUser.getId().toString()));
            cookie.setMaxAge(Const.COOKIE_TIMEOUT);
            cookie.setPath("/");
            response.addCookie(cookie);
            getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUser);
            String preUrl = "/";
            if(null != getSession().getAttribute(Const.LAST_REFERER)){
                preUrl = String.valueOf(getSession().getAttribute(Const.LAST_REFERER));
                if(preUrl.indexOf("/collect?") < 0 && preUrl.indexOf("/lookAround/standard/") < 0
                        && preUrl.indexOf("/lookAround/simple/") < 0){
                    preUrl = "/";
                }
            }
            if(preUrl.indexOf("/lookAround/standard/") >= 0){
                preUrl = "/lookAround/standard/ALL";
            }
            if(preUrl.indexOf("/lookAround/simple/") >= 0){
                preUrl = "/lookAround/simple/ALL";
            }
            return new ResponseData(ExceptionMsg.SUCCESS, preUrl);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("login failed, ", e);
            return new ResponseData(ExceptionMsg.FAILED);
        }
    }
}
