package com.zycus.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zycus.log.AuditLog;

@FeignClient(name="audit-log-service")
@RibbonClient(name="audit-log-service")
public interface AuditLogProxy {
	
	@RequestMapping(value = "/update/log", method = RequestMethod.POST)
	public void updateLog(@RequestBody AuditLog log);
}
