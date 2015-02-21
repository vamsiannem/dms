/*
 * Copyright (c) 2015. All Rights Reserved
 */ 

/**
 * 
 */
package com.dms.unit.sync;

import com.dms.dto.UnitSyncResponse;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * In memory Cache Implementation for Query History
 * @author vamsikrishna_a
 *
 * 
 */

public class SyncResponseCache implements Cache<UnitSyncResponse> {
	
	private final Queue<UnitSyncResponse> linkedQueue;
	
	/**
	 *   Constructor
	 */
	public SyncResponseCache() {
		linkedQueue = new ConcurrentLinkedQueue<UnitSyncResponse>();
		
	}

	@Override
	public Integer getSize() {
		return linkedQueue.size();
	}
	
	@Override
	public boolean isEmpty() {
		return linkedQueue.isEmpty();
	}	

	@Override
	public void pushItem(UnitSyncResponse item) {
		linkedQueue.add(item);
	}

	@Override
	public UnitSyncResponse popItem() {
		return linkedQueue.remove();
	}


}
