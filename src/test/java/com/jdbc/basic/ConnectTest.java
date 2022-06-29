package com.jdbc.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConnectTest {

    @Test
    @DisplayName("DB Connection 정상 수행되어야 한다.")
    void connectTest() {
        Connection conn = Connect.makeConnection();

        if (conn != null) {
            System.out.println("Connection Successful");
        } else {
            System.out.println("Connection Failed");
        }

        // null이 아니면 TEST PASS, null이면 TEST FAIL
        assertNotNull(conn); // conn MUST NOT BE null
    }
}