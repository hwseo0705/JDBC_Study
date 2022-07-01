package com.jdbc.basic.schedule.repository;

import com.jdbc.basic.Connect;
import com.jdbc.basic.schedule.domain.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrashOracleRepo implements TrashRepository {
    @Override
    public boolean save(Schedule schedule) {
        String sql = "INSERT INTO trash VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connect.makeConnection()) {
            // transaction 처리
            conn.setAutoCommit(false); // 자동 커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, schedule.getScheduleId());
            pstmt.setString(2, schedule.getCategory());
            pstmt.setString(3, schedule.getScheduleName());
            pstmt.setString(4, schedule.getDateTime());
            pstmt.setString(5, schedule.getLocation());
            pstmt.setString(6, schedule.getNote());

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
    public boolean remove() {
        String sql = "DELETE FROM trash";

        try (Connection conn = Connect.makeConnection()) {
            // transaction 처리
            conn.setAutoCommit(false); // 자동 커밋 설정 끄기

            PreparedStatement pstmt = conn.prepareStatement(sql);

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
        }    }

    @Override
    public Map<Integer, Schedule> findAll() {
        Map<Integer, Schedule> scheduleMap = new HashMap<>();

        String sql = "SELECT * FROM trash";

        try (Connection conn = Connect.makeConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Schedule s = new Schedule(rs.getInt("schedule_id")
                        , rs.getString("category")
                        , rs.getString("schedule_name")
                        , rs.getString("date_time")
                        , rs.getString("location")
                        , rs.getString("note"));

                scheduleMap.put(s.getScheduleId(), s);
            }

            return scheduleMap;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }    }
}
