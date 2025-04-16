package com.example.mock.service.seat;

import com.example.mock.entity.Seat;
import com.example.mock.repo.SeatRepository;
import com.example.mock.scripts.RedisUnlockScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatService implements ISeatService{
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisUnlockScript redisUnlockScript;

    public List<Seat> getSeatsForScreen(int screenId) {
        return seatRepository.findByScreen_ScreenIdAndIsActiveTrueAndReservedSeatIsNull(screenId);
    }

    public boolean lockSeat(String showtimeId, String seatId, String userId, long ttlSeconds) {
        String redisKey = "seat_lock:" + showtimeId + ":" + seatId;

        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(redisKey, userId, ttlSeconds, TimeUnit.SECONDS);

        if (Boolean.TRUE.equals(isLocked)) {
            return true; // New lock acquired
        }

        // If already locked, check if it's locked by the same user
        String currentOwner = redisTemplate.opsForValue().get(redisKey);
        if (userId.equals(currentOwner)) {
            // Optional: Refresh TTL if you want to extend the lock
            redisTemplate.expire(redisKey, ttlSeconds, TimeUnit.SECONDS);
            return true; // Same user trying again, allow it
        }

        return false; // Locked by someone else
    }


    public boolean unlockSeat(String showtimeId, String seatId, String userId) {
        String redisKey = "seat_lock:" + showtimeId + ":" + seatId;

        Long result = redisTemplate.execute(
                redisUnlockScript.getUnlockScript(),
                Collections.singletonList(redisKey),
                userId
        );

        return result != null && result == 1;
    }
}
