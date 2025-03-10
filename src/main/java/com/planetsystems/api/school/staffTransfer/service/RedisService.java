package com.planetsystems.api.school.staffTransfer.service;


import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;


    public RedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void storeAuthToken(String username, String deviceId, String token) {
        String key = "auth:" + username + ":" + deviceId;

        // Creating a map to store both token and status
        Map<String, Object> authData = new HashMap<>();
        authData.put("token", token);
        authData.put("status", String.valueOf(true)); // Storing the status along with token

        // Store the map in Redis for 24 hours
        redisTemplate.opsForHash().putAll(key, authData);

        // Setting the expiration for the key
        redisTemplate.expire(key, 24, TimeUnit.HOURS);

        
    }

    public void updateAuthTokenStatus(String username, String deviceId, boolean newStatus) {
        String key = "auth:" + username + ":" + deviceId;
    
        // Ensure the key exists and is a Hash before updating
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key)) &&
                DataType.HASH.equals(redisTemplate.type(key))) {
    
            // Update the status field in Redis
            redisTemplate.opsForHash().put(key, "status", String.valueOf(newStatus));
            
        } else {
            
        }
    }
    

    public void updateAuthTokenStatusByUsername(String username, boolean newStatus) {
        String pattern = "auth:" + username + ":*";

        // Fetch matching keys
        Set<String> keys = redisTemplate.keys(pattern);

        if (keys != null && !keys.isEmpty()) {
            for (String key : keys) {
                

                // Ensure the key is actually a Hash before updating
                if (Boolean.TRUE.equals(redisTemplate.hasKey(key)) &&
                        DataType.HASH.equals(redisTemplate.type(key))) {

                    redisTemplate.opsForHash().put(key, "status", String.valueOf(newStatus)); // Store as String
                } else {
                    
                }
            }
            
        } else {
            
        }
    }



    public String getAuthToken(String username, String deviceId) {
        String key = "auth:" + username + ":" + deviceId;
        Object token = redisTemplate.opsForHash().get(key, "token");
        return token != null ? token.toString() : "";
    }

    public Boolean getAuthStatus(String username, String deviceId) {
        String key = "auth:" + username + ":" + deviceId;
        Object status = redisTemplate.opsForHash().get(key, "status");
        return status != null ? Boolean.parseBoolean(status.toString()) : false;
    }


    public void refreshTokenExpiry(String username, String deviceId) {
        String key = "auth:" + username + ":" + deviceId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.expire(key, 24, TimeUnit.HOURS);
        }
    }

    public void removeAuthToken(String username, String deviceId) {
        String key = "auth:" + username + ":" + deviceId;
        redisTemplate.delete(key);
    }


}
