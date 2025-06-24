package io.github.amvilcresx.langchain4j.service;

import io.github.amvilcresx.langchain4j.entity.Reservation;
import io.github.amvilcresx.langchain4j.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    public void add(Reservation reservation) {
        reservationMapper.insert(reservation);
    }

    public Reservation findByPhone(String phone) {
        return reservationMapper.selectByPhone(phone);
    }
}
