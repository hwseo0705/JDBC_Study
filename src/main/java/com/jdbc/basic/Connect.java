package com.jdbc.basic;


import java.sql.*;

// Connect Oracle DB
public class Connect {

    // 데이터 베이스 연결을 위한 정보 저장
    private final static String ACCOUNT = "sqld";
    private final static String PASSWORD = "1234"; // 비번 일치 안하면 FAIL : ex) 12345

    // 데이터 베이스의 위치정보 (DB URL) - DB 회사마다 패턴이 다름
    // oracle xe 11g jdbc url 검색
    private final static String URL = "jdbc:oracle:thin:@localhost:1521:xe";

    // 데이터베이스 접속에 쓸 드라이버 클래스 - DB 회사마다 다름
    // oracle jdbc driver class name 검색
    private final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

    // DB 연결 메서드
    public static Connection makeConnection() {

        try {
            // 1. 드라이버 클래스를 동적 로딩
            Class.forName(DRIVER);

            // 2. 연결정보를 담아 연결 객체를 생성 (try 안에 넣어 auto closing)
            Connection conn = DriverManager.getConnection(URL, ACCOUNT, PASSWORD);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
