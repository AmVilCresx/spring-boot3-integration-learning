package io.github.amvilcresx.langchain4j.mapper;

import io.github.amvilcresx.langchain4j.entity.Reservation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReservationMapper {

    @Insert( """
       INSERT INTO `reservation`
       (`id`, `name`, `gender`, `phone`, `communication_time`, `provice`, `estimated_score`) 
       VALUES 
       (#{id}, #{name}, #{gender}, #{phone}, #{communicationTime}, #{provice}, #{estimatedScore}); 
        """
    )
    void insert(Reservation reservation);

    @Select("select * from reservation where phone=#{phone}")
    Reservation selectByPhone(String phone);
}
