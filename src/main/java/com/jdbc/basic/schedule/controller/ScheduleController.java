package com.jdbc.basic.schedule.controller;

import com.jdbc.basic.Connect;
import com.jdbc.basic.schedule.domain.Schedule;
import com.jdbc.basic.schedule.domain.User;
import com.jdbc.basic.schedule.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleController {

    private static Map<Integer, Schedule> scheduleMap;
    private static Map<Integer, Schedule> trashMap;
    private static Map<String, User> userMap;

    private final ScheduleRepository scheduleRepository;
    private final TrashRepository trashRepository;
    private final StartRepository startRepository;

    public ScheduleController() {
        this.scheduleRepository = new ScheduleOracleRepo();
        this.trashRepository = new TrashOracleRepo();
        this.startRepository = new StartOracleRepo();
    }

    static {
        scheduleMap = new HashMap<>();
        trashMap = new HashMap<>();
        userMap = new HashMap<>();
    }

    public void insertSchedule(Schedule schedule) {
        scheduleMap.put(schedule.getScheduleId(), schedule);
        scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules(User user) {
        Map<Integer, Schedule> schedules = scheduleRepository.findAll(user);
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

    public Schedule findOneTrash(int scheduleId) {
        return trashRepository.findOne(scheduleId);
    }

    public List<Schedule> findScheduleByCategory(String category, User user) {
        Map<Integer, Schedule> schedules = scheduleRepository.findByCategory(category, user);
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

    public boolean hasSchedule(int scheduleId, User user) {
        Schedule result = scheduleRepository.findOne(scheduleId);
        if (scheduleRepository.findOne(scheduleId) != null) {
            if (result.getUserId().equals(user.getUserId())) return true;
        }
        return false;
    }

    public boolean hasTrash(int scheduleId) {
        return trashRepository.findOne(scheduleId) != null;
    }

    public void recover(int scheduleId) {
        insertSchedule(trashRepository.findOne(scheduleId));
//        trashRepository.remove(scheduleId);
    }

    public void emptyTrash(User user) {
        trashRepository.remove(user);
    }

    public List<Schedule> viewTrash(User user) {
        Map<Integer, Schedule> schedules = trashRepository.findAll(user);
        trashMap = schedules;

        List<Schedule> trashList = new ArrayList<>();
        for (Integer scheduleId : schedules.keySet()) {
            trashList.add(schedules.get(scheduleId));
        }

        return trashList;
    }

    public void removePreviousSchedule(User user) {
        // get current date
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd kk:mm");

        Map<Integer, Schedule> schedules = scheduleRepository.findAll(user);
        trashMap = schedules;

        for (Integer scheduleId : schedules.keySet()) {
            if (schedules.get(scheduleId).getDateTime().compareTo(localDateTime.format(dtf)) < 0) {
                scheduleRepository.remove(scheduleId);
                trashRepository.save(schedules.get(scheduleId));
            }
        }

    }

    public boolean checkUserId(String userId) {
        return startRepository.checkId(userId);
    }

    public void insertUser(User user) {
        userMap.put(user.getUserId(), user);
        startRepository.save(user);
    }

    public String getUserPwd(String id) {
        return startRepository.getPwd(id);
    }
}
