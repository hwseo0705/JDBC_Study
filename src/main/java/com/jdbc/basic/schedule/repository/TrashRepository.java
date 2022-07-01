package com.jdbc.basic.schedule.repository;

import com.jdbc.basic.schedule.domain.Schedule;

import java.util.Map;

public interface TrashRepository {

    // 휴지통에 저장
    boolean save(Schedule schedule);

    // 휴지통 비우기
    boolean remove();

    // 선택된 복구
    boolean remove(int scheduleId);

    // 휴지통 불러오기
    Map<Integer, Schedule> findAll();

    // 휴지통
    Schedule findOne(int scheduleId);

}
