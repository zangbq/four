package com.jk.service;

import com.jk.serviceapi.CurriculumServiceApl;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-eduction")
public interface ConsumerService extends CurriculumServiceApl {
}
