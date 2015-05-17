package edu.sjsu.cmpe.cache.client;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class Client {	
	public static void main(String[] args) {
        String server = null;
        CacheServiceInterface cache = null;
        String[] values =  {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        List<String> nodes = getNodes();
    	      
        @SuppressWarnings({"unchecked","rawtypes"})
        ConsistentHash<String> consistentHash = new ConsistentHash(
        		Hashing.md5(), nodes);
                     
        System.out.println("****************************Consistent Hash Input state***************************");
        for (int i=0; i<values.length; i++) {
			int key = i + 1;
			server = consistentHash.get("" + key);					
			cache = new DistributedCacheService(server);
	        cache.put(key, values[i]);
	        System.out.println( key + " ---------> " + values[i] + "------->" + server);
		}
		
        System.out.println("****************************Consistent Hash Output state***************************");
        for (int i=0; i<values.length; i++) {
			int key = i + 1;
			server = consistentHash.get("" + key);					
			cache = new DistributedCacheService(server);
			System.out.println( key + " ---------> " + values[i] + "------->" + server);
		}    			
	}
	
	private static List<String> getNodes() {
		List<String> nodes = Lists.newArrayList();
		for(int i = 0 ; i < 3; i ++) {
			nodes.add("http://localhost:300"+i);
		}
		return nodes;
	}
	
	
}