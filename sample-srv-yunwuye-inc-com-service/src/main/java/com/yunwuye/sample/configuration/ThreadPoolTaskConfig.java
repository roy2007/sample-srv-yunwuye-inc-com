package com.yunwuye.sample.configuration;

import io.micrometer.core.instrument.util.StringUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 *
 * @author Roy
 *
 * @date 2020年5月3日-下午5:18:28
 */
@Configuration
public class ThreadPoolTaskConfig {
  private final String DEFAULT_THREAD_NAME_PREFIX = "sample-asyn-pool-task-";

  private static int DEFAULT_CORE = Runtime.getRuntime().availableProcessors();
  private static int DEFAULT_KEEP_ALIVE_SECONDS = 3;
  private static int DEFAULT_QUEUE_CAPACITY = 40;

  @Value("${thread.name.prefix}")
  private String threadNamePrefix;

  @Value("${thread.pool.max.pool.size}")
  private int maxPoolSize;

  @Value("${thread.pool.queue.capacity.size}")
  private int queueCapacity;

  @Value("${thread.pool.keep.alive.seconds}")
  private int keepAlive;

  @Bean(name = "threadPoolTaskExecutor")
  public Executor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix(StringUtils.isNotBlank(threadNamePrefix) ? threadNamePrefix
        : DEFAULT_THREAD_NAME_PREFIX);
    executor.setCorePoolSize(DEFAULT_CORE);
    executor.setMaxPoolSize(DEFAULT_CORE * 2 + 1);
    executor.setKeepAliveSeconds(keepAlive > 0 ? keepAlive : DEFAULT_KEEP_ALIVE_SECONDS);
    executor.setQueueCapacity(queueCapacity > 0 ? queueCapacity : DEFAULT_QUEUE_CAPACITY);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    executor.initialize();
    return executor;
  }
}
