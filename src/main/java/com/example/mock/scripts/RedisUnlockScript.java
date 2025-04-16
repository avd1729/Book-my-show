package com.example.mock.scripts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RedisUnlockScript {

    @Value("classpath:lua/unlock_seat.lua")
    private Resource luaScriptResource;

    public DefaultRedisScript<Long> getUnlockScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        try {
            String scriptText = new String(luaScriptResource.getInputStream().readAllBytes());
            script.setScriptText(scriptText);
            script.setResultType(Long.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Lua script", e);
        }
        return script;
    }
}
