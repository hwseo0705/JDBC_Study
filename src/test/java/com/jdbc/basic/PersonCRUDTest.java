package com.jdbc.basic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.jdbc.basic.PersonCRUD.*;
import static org.junit.jupiter.api.Assertions.*;

class PersonCRUDTest {

    @Test
    @DisplayName("사람 정보가 데이터베이스에 저장되어야 한다.")
    void saveTest() {

        // 사람 객체 생성
        Person p = makePerson("980705-1234567", "서해원", 25);

        // 사람 저장 명령
        boolean result = save(p);

        // 단언
        assertTrue(result);

    }

    @Test
    @DisplayName("주어진 주민번호에 맞는 사람정보를 데이터베이스에서 삭제해야 한다.")
    void removeTest() {

        boolean flag = remove("850201-123499 ");
        assertTrue(flag);

    }

    @Test
    @DisplayName("주어진 주민번호에 맞는 사람의 이름을 업데이트 시켜줘야 한다.")
    void modifyTest() {

        boolean flag = modify("980705-1234567", "Haewon");
        assertTrue(flag);

    }

    @Test
    void bulkInsertTest() {
        String[] firstNames = {"김", "이", "박", "최", "송", "라"};
        for (int i = 0; i < 10; i++) {
            String f = firstNames[(int) (Math.random() * firstNames.length)];
            save(makePerson("991112-123456"+i, f+"철수", 24));
        }
    }

    @Test
    @DisplayName("전체 사람데이터를 조회해야 한다.")
    void findAllTest() {
        List<Person> people = findAll();

        for (Person p : people) {
            System.out.println(p);
        }
    }
}