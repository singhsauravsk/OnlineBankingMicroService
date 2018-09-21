package com.zycus.services;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.zycus.entity.accounts.Account;
import com.zycus.entity.cards.Card;
import com.zycus.log.AuditLog;
import com.zycus.proxy.AuditLogProxy;
import com.zycus.repository.CardRepository;

@Service
@Transactional
public class CardService implements Services <Card, Long> {

	@Autowired
	CardRepository cardRepository;
	
	@Autowired
	AuditLogProxy proxy;
	
	@Caching(evict = {
			@CacheEvict(value = "allCards.cache", allEntries = true),
			@CacheEvict(value = "allCardsByFK.cache", allEntries = true)})
	public void addNew(Card card, HttpServletRequest request) {
		card.setExpiryDate(System.currentTimeMillis() + 453216217699l);
		card.setPrimaryKey(System.nanoTime());
		card.setCardNumber(System.currentTimeMillis() % 10000000000000000l);
		
		cardRepository.save(card);
		
		String message = "Card is created with card number " + card.getCardNumber() + " with maximum limit of Rs." + card.getMaximumLimit();
		updateLog(request, card.getAccount().getAccountNumber(), message);
	}

	@Cacheable(value = "allCards.cache")
	public Iterable<Card> fetchAll(HttpServletRequest request) {
		return cardRepository.findAll();
	}

	public Card fetchById(Long id, HttpServletRequest request) {
		return cardRepository.findById(id).get();
	}

	public boolean validateUser(Card t, HttpServletRequest request) {
		return false;
	}

	public List<Card> fetchAllSorted(HttpServletRequest request) {
		return null;
	}

	@Cacheable(value = "allCardsByFK.cache")
	public List <Card> fetchByForeignKey(Account account, HttpServletRequest request) {
		return cardRepository.findByForeignKey(account);
	}

	public void updateLog(HttpServletRequest request, Long account, String message) {
		AuditLog log = new AuditLog();
		
		log.setAccount(account);
		log.setMessage(message);
		log.setUser((String)request.getSession().getAttribute("adminName"));
		log.setDate(new Date());
		
		proxy.updateLog(log);
	}
}
