package com.zycus.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zycus.entity.accounts.Account;

public interface Services <T, ID> {
	
	void addNew(T t, HttpServletRequest request);
	Iterable <T> fetchAll(HttpServletRequest request);
	T fetchById(ID id, HttpServletRequest request);
	boolean validateUser(T t, HttpServletRequest request);
	List <T> fetchAllSorted(HttpServletRequest request);
	List <T> fetchByForeignKey(Account account, HttpServletRequest request);
	void updateLog(HttpServletRequest request, Long account, String message);
}
