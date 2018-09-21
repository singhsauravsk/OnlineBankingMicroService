package com.zycus.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zycus.entity.accounts.Account;
import com.zycus.entity.users.User;
import com.zycus.services.Services;

@Controller
public class LoginController {

	@Autowired
	Services <User, String> userService;
	
	@Autowired
	Services <Account, Long> accountService;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request) {
        return "loginPage";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    public @ResponseBody String showHomePage(@RequestBody User user, HttpServletRequest request, Model model) {
    	
    	if(userService.validateUser(user, request)) {
    		return "success";
    	}
    	else {
    		return "Fail";
    	}
    }
    
    @RequestMapping(value="/home", method = RequestMethod.GET)
    public String showHomePage(HttpServletRequest request, Model model) {
    	model.addAttribute("adminName", request.getSession().getAttribute("adminName"));
		model.addAttribute("allAccounts", accountService.fetchAllSorted(request));
		
		return "homePage";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, Model model) {
    	HttpSession session = request.getSession();
    	session.invalidate();
    	
        return "loginPage";
    }
}
