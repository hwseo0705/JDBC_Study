package com.jdbc.basic.schedule.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.schedule.domain.Schedule;
import com.jdbc.basic.schedule.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ScheduleOracleRepo implements ScheduleRepository {
    @Override
    public boolean save(Schedule schedule) {
        String sql = "INSERT INTO schedule VALUES (seq_schedule.nextval, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connect.makeConnection()) {
            // transaction 처리
            conn.setAutoCommit(false); // 자동 커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, schedule.getCategory());
            pstmt.setString(2, schedule.getScheduleName());
            pstmt.setString(3, schedule.getDateTime());
            pstmt.setString(4, schedule.getLocation());
            pstmt.setString(5, schedule.getNote());
            pstmt.setString(6, schedule.getUserId());

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(int scheduleId) {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";

        try (Connection conn = Connect.makeConnection()) {
            // transaction 처리
            conn.setAutoCommit(false); // 자동 커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, scheduleId);

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modify(Schedule schedule) {
        String sql = "UPDATE schedule SET category = ?, schedule_name = ?, date_time = ?, location = ?, note = ?, user_id = ? WHERE schedule_id = ?";

        try (Connection conn = Connect.makeConnection()) {
            // transaction 처리
            conn.setAutoCommit(false); // 자동 커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, schedule.getCategory());
            pstmt.setString(2, schedule.getScheduleName());
            pstmt.setString(3, schedule.getDateTime());
            pstmt.setString(4, schedule.getLocation());
            pstmt.setString(5, schedule.getNote());
            pstmt.setString(6, schedule.getUserId());
            pstmt.setInt(7, schedule.getScheduleId());

            int result = pstmt.executeUpdate();

            if (result != 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<Integer, Schedule> findAll(User user) {
        Map<Integer, Schedule> scheduleMap = new HashMap<>();

        String sql = "SELECT * FROM schedule WHERE user_id = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUserId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Schedule s = new Schedule(rs.getInt("schedule_id")
                        , rs.getString("category")
                        , rs.getString("schedule_name")
                        , rs.getString("date_time")
                        , rs.getString("location")
                        , rs.getString("note")
                        , rs.getString("user_id"));

                scheduleMap.put(s.getScheduleId(), s);
            }

            return scheduleMap;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    @Override
    public Schedule findOne(int scheduleId) {
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, scheduleId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Schedule s = new Schedule(rs.getInt("schedule_id")
                        , rs.getString("category")
                        , rs.getString("schedule_name")
                        , rs.getString("date_time")
                        , rs.getString("location")
                        , rs.getString("note")
                        , rs.getString("user_id"));
                return s;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, Schedule> findByCategory(String category, User user) {
        Map<Integer, Schedule> scheduleMap = new HashMap<>();

        String sql = "SELECT * FROM schedule WHERE category = ?";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, category);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("user_id").equals(user.getUserId())) {
                    Schedule s = new Schedule(rs.getInt("schedule_id")
                            , rs.getString("category")
                            , rs.getString("schedule_name")
                            , rs.getString("date_time")
                            , rs.getString("location")
                            , rs.getString("note")
                            , rs.getString("user_id"));
                    scheduleMap.put(s.getScheduleId(), s);
                }
            }

            return scheduleMap;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

}
