package com.ruoyi.web.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@Controller
public class SysLoginController extends BaseController
{
	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private SysPasswordService passwordService;
	
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response)
    {
        // 如果是Ajax请求，返回Json字符串。
        if (ServletUtils.isAjaxRequest(request))
        {
            return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
        }

        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe)
    {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(token);
            return success();
        }
        catch (AuthenticationException e)
        {
            String msg = "用户或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))
            {
                msg = e.getMessage();
            }
            return error(msg);
        }
    }
    
    @GetMapping("/register")
    public String register(HttpServletRequest request, HttpServletResponse response)
    {

        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public AjaxResult ajaxRegister(String username, String password)
    {
    	if (sysUserService.checkLoginNameUniqueRegister(username)) {
    		SysUser user = new SysUser();
    		//给注册的用户写死角色和部门
    		Long roles[] = { 2L };
    		user.setRoleIds(roles);
    		Long postIds[] = { 2L };
    		user.setPostIds(postIds);
    		
    		
    		user.setUserName(username);
    		user.setLoginName(username);
    		user.setSalt(ShiroUtils.randomSalt());
    		user.setPassword(passwordService.encryptPassword(user.getLoginName(), password, user.getSalt()));
    		sysUserService.insertUser(user);
    		String msg = "用户"+username+",注册成功";
    		return success(msg);
		}else {
			String msg = "用户名已存在";
			return error(msg);
		}
    }

    @GetMapping("/unauth")
    public String unauth()
    {
        return "error/unauth";
    }
}
