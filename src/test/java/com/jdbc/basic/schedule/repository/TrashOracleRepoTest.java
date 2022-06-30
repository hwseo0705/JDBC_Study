package com.jdbc.basic.schedule.repository;

import com.jdbc.basic.schedule.domain.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrashOracleRepoTest {
    TrashRepository repository = new TrashOracleRepo();

    @Test
    @DisplayName("스케쥴 정보를 DB에 삽입해야 한다.")
    void insertTest() {
        Schedule s = new Schedule();
        s.setCategory("Work");
        s.setScheduleName("DB에 삽입");
        s.setDateTime("22/08/01 14:00");
        s.setLocation("집");
        s.setNote("그냥 테스트 해보는 것");

        boolean result = repository.save(s);
        assertTrue(result);
    }

    @Test
    @DisplayName("휴지통 비워야한다.")
    void removeTest() {
        boolean result = repository.remove();
        assertTrue(result);
    }

    @Test
    @DisplayName("전체 스케쥴 정보를 조회해야 한다")
    void findAllTest() {
        Map<Integer, Schedule> scheduleMap = repository.findAll();
        for (Integer id : scheduleMap.keySet()) {
            System.out.println(scheduleMap.get(id));
        }
    }

}