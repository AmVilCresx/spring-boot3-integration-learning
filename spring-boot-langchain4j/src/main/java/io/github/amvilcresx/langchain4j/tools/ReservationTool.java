package io.github.amvilcresx.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import io.github.amvilcresx.langchain4j.entity.Reservation;
import io.github.amvilcresx.langchain4j.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 工具方法
@Component
public class ReservationTool {

    @Autowired
    private ReservationService reservationService;

    @Tool("预约志愿填报服务")
    public void addReservation(
            @P("考生姓名") String name,
            @P("考生性别")String gender,
            @P("考生手机号")String phone,
            @P("预约沟通时间，格式为: yyyy-MM-dd'T'HH:mm")String communicationTime,
            @P("考生所在省份")String province,
            @P("考生预估分数") Integer estimatedScore
    ) {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setGender(gender);
        reservation.setPhone(phone);
        reservation.setCommunicationTime(LocalDateTime.parse(communicationTime));
        reservation.setProvince(province);
        reservation.setEstimatedScore(estimatedScore);
        reservationService.add(reservation);
    }

    @Tool("根据考生手机号查询预约单")
    public Reservation getReservationByPhone(@P("考生手机号") String phone) {
        return reservationService.findByPhone(phone);
    }
}
