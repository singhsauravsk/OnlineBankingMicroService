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
import com.zycus.log.AuditLog;
import com.zycus.proxy.AuditLogProxy;
import com.zycus.repository.AccountRepository;

@Service
@Transactional
public class AccountService implements Services <Account, Long> {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	AuditLogProxy proxy;
	
	@Caching(evict = {
			@CacheEvict(value = "allAccounts.cache", allEntries = true),
			@CacheEvict(value = "allAccountsSorted.cache", allEntries = true)})
	public void addNew(Account account, HttpServletRequest request) {
		account.setAmount(12345.4d);
		account.setAccountNumber(System.nanoTime() + System.currentTimeMillis());
		account.setOpeningDate(System.currentTimeMillis());
		account.setPrimaryKey(System.currentTimeMillis());
		
		accountRepository.save(account);
		
		String message = "Account is created with account number " + account.getAccountNumber() + " with amount Rs." + account.getAmount();
		updateLog(request, account.getAccountNumber(), message);
	}

	@Cacheable(value = "allAccounts.cache")
	public Iterable<Account> fetchAll(HttpServletRequest request) {
		return accountRepository.findAll();
	}

	@Cacheable(value = "allAccountsSorted.cache")
	public List <Account> fetchAllSorted(HttpServletRequest request) {
		return accountRepository.findAllSorted();
	}
	
	public Account fetchById(Long id, HttpServletRequest request) {
		return accountRepository.findById(id).get();
	}

	public boolean validateUser(Account t, HttpServletRequest request) {
		return false;
	}

	public List <Account> fetchByForeignKey(Account account, HttpServletRequest request) {
		return null;
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
