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

import com.zycus.dto.AccountDTO;
import com.zycus.dto.CardDTO;
import com.zycus.dto.CardViewDTO;
import com.zycus.entity.accounts.Account;
import com.zycus.entity.cards.Card;
import com.zycus.services.Services;

@Controller
public class AccountController {

	@Autowired
	Services <Account, Long> accountService;
	
	@Autowired 
	Services <Card, Long> cardService; 
	
	private CardViewDTO cardViewDetails;
	
	@RequestMapping(value = "/account/add", method = RequestMethod.GET)
    public String addAccountGet(HttpServletRequest request) {
		request.getSession().invalidate();
    
        return "redirect:/login";
    }
	
	@RequestMapping(value = "/account/add", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    public @ResponseBody String addAccountPost(@RequestBody AccountDTO accountDetails, HttpServletRequest request, Model madel) {
		Account account = new Account();
		account.setHolderName(accountDetails.getHolderName());
		account.setAccountType(accountDetails.getAccountType());
		
		accountService.addNew(account, request);
		
		return "success";
    }
	
	@RequestMapping(value = "/card/add", method = RequestMethod.GET)
    public String addCardGet(HttpServletRequest request) {
		request.getSession().invalidate();
    
        return "redirect:/login";
    }
	
	@RequestMapping(value = "/card/add", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    public @ResponseBody String addCardPost(@RequestBody CardDTO cardDetails, HttpServletRequest request, Model madel) {
		Card card = new Card();
		card.setMaximumLimit(cardDetails.getMaximumLimit());
		card.setCardType(cardDetails.getCardType());
		card.setAccount(accountService.fetchById(cardDetails.getForeignKey(), request));

		cardService.addNew(card, request);
		
		return "success";
    }
	
	@RequestMapping(value = "/card/view", method = RequestMethod.GET)
    public String viewCardGet(HttpServletRequest request) {
		request.getSession().invalidate();
    
        return "redirect:/login";
    }
	
	@RequestMapping(value = "/card/view", method = RequestMethod.POST, consumes = "application/json", produces = "plain/text")
    public @ResponseBody String viewCardPost(@RequestBody CardViewDTO cardDetails, HttpServletRequest request, Model model) {
		cardViewDetails = cardDetails;
		
		return "success";
    }
	
    @RequestMapping(value="/listCard", method = RequestMethod.GET)
    public String showHomePage(HttpServletRequest request, Model model) {
    	model.addAttribute("adminName", request.getSession().getAttribute("adminName"));
		Account account = accountService.fetchById(cardViewDetails.getForeignKey(), request);
		
		model.addAttribute("linkedCards", cardService.fetchByForeignKey(account, request));
		model.addAttribute("accountNumber", account.getAccountNumber());
		
		return "viewCard";
    }
}
