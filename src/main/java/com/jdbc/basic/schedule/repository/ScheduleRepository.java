package com.jdbc.basic.schedule.repository;

import com.jdbc.basic.schedule.domain.Schedule;

import java.util.Map;

public interface ScheduleRepository {

    // 스케쥴 저장
    boolean save(Schedule schedule);

    // 스케쥴 삭제
    boolean remove(int scheduleId);

    // 스케쥴 수정
    boolean modify(Schedule schedule);

    // 스케쥴 전체 조회
    Map<Integer, Schedule> findAll();

    // 특정 스케쥴 조회
    Schedule findOne(int scheduleId);

    // 휴지통 불러오기


}
