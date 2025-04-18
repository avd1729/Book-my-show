package com.example.mock.scripts;

import com.example.mock.entity.ReservedSeat;
import com.example.mock.repo.ReservationRepository;
import com.example.mock.repo.ReservedSeatRepository;
import com.example.mock.repo.ShowTimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowTimeExpirationService {

    private final ReservedSeatRepository reservedSeatRepository;
    private final ReservationRepository reservationRepository;
    private final ShowTimeRepository showTimeRepository;

    public ShowTimeExpirationService(ReservedSeatRepository reservedSeatRepository,
                                     ReservationRepository reservationRepository,
                                     ShowTimeRepository showTimeRepository) {
        this.reservedSeatRepository = reservedSeatRepository;
        this.reservationRepository = reservationRepository;
        this.showTimeRepository = showTimeRepository;
    }

    // Scheduled task that runs every 2 hours
    @Scheduled(fixedRate = 120000) // Every 2 minutes
    @Transactional
    public void deleteExpiredReservedSeats() {
        LocalDateTime now = LocalDateTime.now();

        // Step 1: Find expired showtime IDs
        List<Integer> expiredShowtimeIds = showTimeRepository.findExpiredShowtimeIds(now);

        if (expiredShowtimeIds.isEmpty()) return;

        // Step 2: Find reservation IDs for expired showtimes
        List<Integer> reservationIds = reservationRepository.findReservationIdsByShowtimeIds(expiredShowtimeIds);

        if (reservationIds.isEmpty()) return;

        // Step 3: Delete reserved seats linked to those reservations
        reservedSeatRepository.deleteByReservationIds(reservationIds);
    }

    // Method to delete or mark the reserved seat as expired for an expired ShowTime
    private void expireReservedSeat(ReservedSeat reservedSeat) {
        reservedSeatRepository.delete(reservedSeat);
    }

}
