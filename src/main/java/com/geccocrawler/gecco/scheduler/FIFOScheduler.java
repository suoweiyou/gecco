package com.geccocrawler.gecco.scheduler;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.geccocrawler.gecco.request.HttpRequest;

/**
 * 采用阻塞的先进先出队列，最大支持Integer.MAX_VALUE个元素
 * 
 * @author huchengyi
 *
 */
public class FIFOScheduler implements Scheduler {

	private LinkedBlockingQueue<HttpRequest> queue;
	
	public FIFOScheduler() {
		queue = new LinkedBlockingQueue<HttpRequest>();
	}
	
	/**
	 * 入队列，超过边界会阻塞等待
	 */
	@Override
	public void into(HttpRequest request) {
		try {
			queue.put(request);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 出队列，队列为空会阻塞等待
	 */
	@Override
	public HttpRequest out() {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void into(List<HttpRequest> requests) {
		for(HttpRequest request : requests) {
			into(request);
		}
	}

}