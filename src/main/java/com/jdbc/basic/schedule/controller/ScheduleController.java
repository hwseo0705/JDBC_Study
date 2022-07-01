package com.jdbc.basic.schedule.controller;

import com.jdbc.basic.schedule.domain.Schedule;
import com.jdbc.basic.schedule.repository.ScheduleOracleRepo;
import com.jdbc.basic.schedule.repository.ScheduleRepository;
import com.jdbc.basic.schedule.repository.TrashOracleRepo;
import com.jdbc.basic.schedule.repository.TrashRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleController {

    private static Map<Integer, Schedule> scheduleMap;
    private static Map<Integer, Schedule> trashMap;

    private final ScheduleRepository scheduleRepository;
    private final TrashRepository trashRepository;

    public ScheduleController() {
        this.scheduleRepository = new ScheduleOracleRepo();
        this.trashRepository = new TrashOracleRepo();
    }

    static {
        scheduleMap = new HashMap<>();
        trashMap = new HashMap<>();
    }

    public void insertSchedule(Schedule schedule) {
        scheduleMap.put(schedule.getScheduleId(), schedule);
        scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        Map<Integer, Schedule> schedules = scheduleRepository.findAll();
        scheduleMap = schedules;

        List<Schedule> scheduleList = new ArrayList<>();
        for (Integer scheduleId : schedules.keySet()) {
            scheduleList.add(schedules.get(scheduleId));
        }

        return scheduleList;
    }

    public Schedule findOneSchedule(int scheduleId) {
        return scheduleRepository.findOne(scheduleId);
    }

    public List<Schedule> findScheduleByCategory(String category) {
        Map<Integer, Schedule> schedules = scheduleRepository.findByCategory(category);
        scheduleMap = schedules;

        List<Schedule> scheduleList = new ArrayList<>();
        for (Integer scheduleId : schedules.keySet()) {
            scheduleList.add(schedules.get(scheduleId));
        }

        return scheduleList;
    }

    public boolean updateSchedule(int scheduleId, String category, String name, String dateTime, String location, String note) {
        // 1. DB에서 해당 학생을 조회한다.
        Schedule target = findOneSchedule(scheduleId);

        if (target != null) {
            // 2. 수정 진행
            target.setCategory(category);
            target.setScheduleName(name);
            target.setDateTime(dateTime);
            target.setLocation(location);
            target.setNote(note);

            // 3. DB에 수정 반영
            return scheduleRepository.modify(target);
        }
        return false;
    }

    public boolean deleteSchedule(int scheduleId) {
        Schedule target = scheduleRepository.findOne(scheduleId);
        trashMap.put(target.getScheduleId(), target);
        trashRepository.save(target);
        return scheduleRepository.remove(scheduleId);
    }

    public boolean hasSchedule(int scheduleId) {
        return scheduleRepository.findOne(scheduleId) != null;
    }

    public void emptyTrash() {
        trashRepository.remove();
    }

    public List<Schedule> viewTrash() {
        Map<Integer, Schedule> schedules = trashRepository.findAll();
        trashMap = schedules;

        List<Schedule> trashList = new ArrayList<>();
        for (Integer scheduleId : schedules.keySet()) {
            trashList.add(schedules.get(scheduleId));
        }

        return trashList;
    }
}
