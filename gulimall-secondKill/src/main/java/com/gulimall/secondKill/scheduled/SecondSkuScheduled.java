package com.gulimall.secondKill.scheduled;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.gulimall.secondKill.service.SecondKillService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecondSkuScheduled {

	@Autowired
	SecondKillService secondKillService;

	@Autowired
	RedissonClient redissonClient;

	private final String uploadLock = "secondKill:upload:lock";

	@Scheduled(cron = "0 0 3 * *")
	public void uploadSecondKillSkuLatest3Days() {
		//重复上架无需处理
		log.info("上架秒杀的信息");

		//分布式锁。锁的业务执行完成，状态已经更新完成。释放锁以后。其他人获取到就会拿到最新的状态
		RLock lock = redissonClient.getLock(uploadLock);
		lock.lock(10, TimeUnit.SECONDS);
		try {
			secondKillService.uploadSecondKillSkuLatest3Days();
		} finally {
			lock.unlock();
		}

	}

}
