package com.myimooc.helloquartz.three;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 任务调度类  触发器通用属性
 * @author ZhangCheng on 2017-06-26
 *
 */
public class HelloScheduler {
	
	public static void main(String[] args) throws SchedulerException {
		// 打印当前的执行时间，格式为2017-01-01 00:00:00
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Current Time Is : " + sf.format(date));
		
		// 创建一个 JobDetail 实例，将该实例与 HelloJob 实例绑定
		JobDetail jobDeatil = JobBuilder.newJob(HelloJob.class)
				.withIdentity("myjob", "jobgroup1")// 定义标识符
				.build();
		
		// 获取距离当前时间3秒后的时间
		date.setTime(date.getTime() + 3000);
		// 获取距离当前时间6秒后的时间
		Date endDate = new Date();
		endDate.setTime(endDate.getTime() + 6000);
		
		// 创建一个 Trigger 实例，定义该 job 立即执行，并且每隔两秒重复执行一次，直到永远
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger","trigroup1")// 定义标识符
				.startAt(date)// 定义3秒后执行
				.endAt(endDate)// 定义6秒后结束
				.withSchedule(SimpleScheduleBuilder
						.simpleSchedule()
						.withIntervalInSeconds(2)
						.repeatForever())// 定义执行频度
				.build();
		
		// 创建 Scheduler 实例
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();
		
		// 绑定 JobDetail 和 trigger
		scheduler.scheduleJob(jobDeatil, trigger);
		
		// 执行任务
		scheduler.start();
	}
	
}
