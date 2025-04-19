package com.example.mock.service.seat;

import com.example.mock.dto.SeatDTO;
import com.example.mock.entity.Seat;
import com.example.mock.repo.SeatRepository;
import com.example.mock.scripts.RedisUnlockScript;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SeatService implements ISeatService{

    private final SeatRepository seatRepository;

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisUnlockScript redisUnlockScript;

    public SeatService(SeatRepository seatRepository, RedisTemplate<String, String> redisTemplate, RedisUnlockScript redisUnlockScript) {
        this.seatRepository = seatRepository;
        this.redisTemplate = redisTemplate;
        this.redisUnlockScript = redisUnlockScript;
    }

    public List<Seat> getSeatsForScreen(int screenId) {
        return seatRepository.findByScreen_ScreenIdAndIsActiveTrue(screenId);
    }

    public boolean checkAndLockSeat(String showtimeId, String seatId, String userId, long ttlSeconds) {
        String redisKey = "seat_lock:" + showtimeId + ":" + seatId;

        String currentOwner = redisTemplate.opsForValue().get(redisKey);

        if (currentOwner == null || currentOwner.equals(userId)) {
            return lockSeat(showtimeId, seatId, userId, ttlSeconds);
        }

        return false;
    }

    public boolean lockSeat(String showtimeId, String seatId, String userId, long ttlSeconds) {
        String redisKey = "seat_lock:" + showtimeId + ":" + seatId;

        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(redisKey, userId, ttlSeconds, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(isLocked)) {
            return true; // New lock acquired
        }

        // Already locked, check if it's locked by the same user
        String currentOwner = redisTemplate.opsForValue().get(redisKey);
        if (userId.equals(currentOwner)) {
            redisTemplate.expire(redisKey, ttlSeconds, TimeUnit.SECONDS);
            return true; // Refresh TTL
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

    public Seat getSeatById(Integer seatId){
        return seatRepository.findById(seatId).orElse(null);
    }

    public Seat updateSeat(SeatDTO seatDTO, Integer seatId){
        return null;
    }
}
