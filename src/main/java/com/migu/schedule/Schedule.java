package com.migu.schedule;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.Task;
import com.migu.schedule.info.TaskInfo;

/*
*类名和方法不能修改
 */
public class Schedule {

	private final List<Integer> server = new ArrayList<Integer>();  //维护已注册服务器
	
	private final LinkedBlockingDeque<Task> blockTask = new LinkedBlockingDeque<Task>(); // 维护注册任务
	
	private final List<TaskInfo> serverTask = new LinkedList<TaskInfo>();  // 维护服务器及对应任务
	
	private final Map<Integer, Integer> serverConsumption = new HashMap<Integer, Integer>(); // 维护各服务器总消耗率
	
    public int init() {
        // TODO 方法未实现
    	server.clear();;
    	blockTask.clear();
    	serverTask.clear();
    	serverConsumption.clear();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {
        // TODO 方法未实现
    	if(nodeId <= 0){
    		return ReturnCodeKeys.E004;
    	}
    	for(int i=0; i<server.size(); i++){
    		if(server.get(i).intValue() == nodeId)
    			return ReturnCodeKeys.E005;
    	}
    	server.add(new Integer(nodeId));
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {
        // TODO 方法未实现
    	if(nodeId <= 0){
    		return ReturnCodeKeys.E004;
    	}
    	for(int i=0; i<server.size(); i++){
    		if(server.get(i).intValue() == nodeId)
    			break;
    		if(i == (server.size() - 1) && server.get(i).intValue() != nodeId)
    			return ReturnCodeKeys.E007;
    	}
    	server.remove(new Integer(nodeId));
        return ReturnCodeKeys.E006;
    }


    public int addTask(int taskId, int consumption) {
        // TODO 方法未实现
    	if(taskId <= 0){
    		return ReturnCodeKeys.E009;
    	}
    	Iterator<Task> it = blockTask.iterator();
    	while(it.hasNext()){
    		Task temp = it.next();
    		if(temp.getTaskId() == taskId)
    			return ReturnCodeKeys.E010;
    	}
    	Task task = new Task(taskId, consumption);
    	blockTask.add(task);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        // TODO 方法未实现
    	if(taskId <= 0){
    		return ReturnCodeKeys.E009;
    	}
    	Iterator<Task> it = blockTask.iterator();
    	boolean contains = false;
    	while(it.hasNext()){
    		Task temp = it.next();
    		if(temp.getTaskId() == taskId){
    			it.remove();
    			contains = true;
    			break;
    		}
    	}
    	if(!contains){
    		return ReturnCodeKeys.E012;
    	}
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        // TODO 方法未实现
    	if(threshold < 0)
    		return ReturnCodeKeys.E002;
    	while(blockTask.size() > 0){
    		int nodeId = getMinConsumptionSerer();
    		Task temp = blockTask.poll();
    		TaskInfo taskInfo = new TaskInfo();
    		taskInfo.setNodeId(nodeId);
    		taskInfo.setTaskId(temp.getTaskId());
    		Integer nodeIdFace = new Integer(nodeId);
    		int totalConsumption = serverConsumption.get(nodeIdFace) + temp.getConsumption();
    		serverConsumption.put(nodeIdFace, totalConsumption);
    	}
    	if(getMaxThreshold() > threshold){
    			
    		
    	}
        return ReturnCodeKeys.E013;
    }
    private int getMaxThreshold(){
    	Collections.sort(server);
    	int minConsumption = serverConsumption.get(server.get(0));
    	int maxConsumption = serverConsumption.get(server.get(0));
    	for(int i=1; i<server.size(); i++){
    		int temp = serverConsumption.get(new Integer(server.get(i)));
    		if(temp < minConsumption){
    			minConsumption = temp;
    		}
    		if(temp > maxConsumption){
    			maxConsumption = temp;
    		}
    	}
    	return maxConsumption - minConsumption;
    }

    private int getMinConsumptionSerer(){
    	Collections.sort(server);
    	Integer nodeId = server.get(0);
    	int minConsumption = serverConsumption.get(new Integer(nodeId));
    	for(int i=0; i<server.size(); i++){
    		int temp = serverConsumption.get(new Integer(server.get(i)));
    		if(temp < minConsumption){
    			minConsumption = temp;
    			nodeId = server.get(i).intValue();
    		}
    	}
    	return nodeId.intValue();
    }

    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
    	if(tasks == null)
    		return ReturnCodeKeys.E016;
    	
        return ReturnCodeKeys.E000;
    }

}
