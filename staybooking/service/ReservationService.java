package com.laioffer.staybooking.service;
import org.springframework.stereotype.Service;
import com.laioffer.staybooking.repository.ReservationRepository;
import com.laioffer.staybooking.repository.StayAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.laioffer.staybooking.exception.ReservationCollisionException;
import com.laioffer.staybooking.exception.ReservationNotFoundException;
import com.laioffer.staybooking.model.Reservation;
import com.laioffer.staybooking.model.Stay;
import com.laioffer.staybooking.model.User;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;


@Service

public class ReservationService {
    private ReservationRepository reservationRepository;
    private StayAvailabilityRepository stayAvailabilityRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, StayAvailabilityRepository stayAvailabilityRepository) {
        this.reservationRepository = reservationRepository;
        this.stayAvailabilityRepository = stayAvailabilityRepository;
    }
    public List<Reservation> listByGuest(String username) {
        return reservationRepository.findByGuest(new User.Builder().setUsername(username).build());
    }

    public List<Reservation> listByStay(Long stayId) {
        return reservationRepository.findByStay(new Stay.Builder().setId(stayId).build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE) // 保证里面的method依次执行，同时成功/失败
    public void add(Reservation reservation) throws ReservationCollisionException {
        List<LocalDate> dates = stayAvailabilityRepository.countByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        int duration = (int) Duration.between(reservation.getCheckinDate().atStartOfDay(), reservation.getCheckoutDate().atStartOfDay()).toDays();
        if (duration != dates.size()) { //判断可以订的天数是否符合需要订的天数
            throw new ReservationCollisionException("Duplicate reservation");
        } //保证不race
        stayAvailabilityRepository.reserveByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1)); //-1 的效果：允许同天check in 和 heck out
        reservationRepository.save(reservation);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long reservationId) throws ReservationNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ReservationNotFoundException("Reservation is not available"));
        stayAvailabilityRepository.cancelByDateBetweenAndId(reservation.getStay().getId(), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        reservationRepository.deleteById(reservationId);
    }

}
