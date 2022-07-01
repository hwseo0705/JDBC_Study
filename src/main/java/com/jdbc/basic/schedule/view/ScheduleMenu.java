package com.jdbc.basic.schedule.view;

import com.jdbc.basic.schedule.controller.ScheduleController;
import com.jdbc.basic.schedule.domain.Schedule;

import java.util.List;
import java.util.Scanner;

public class ScheduleMenu {
    private final ScheduleController controller;
    private final Scanner sc;

    public ScheduleMenu() {
        controller = new ScheduleController();
        sc = new Scanner(System.in);
    }


    public void mainMenu() {

        while (true) {
            System.out.println("\n======= 스케쥴 관리 프로그램 ========");
            System.out.println("# 1. 스케쥴 정보 입력");
            System.out.println("# 2. 스케쥴 전체 조회");
            System.out.println("# 3. 스케쥴 개별 조회");
            System.out.println("# 4. 스케쥴 정보 수정");
            System.out.println("# 5. 스케쥴 정보 삭제");
            System.out.println("# 6. 휴지통");
            System.out.println("# 9. 끝내기");

            int menu = inputN("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    insertMenu();
                    break;
                case 2:
                    findAllMenu();
                    break;
                case 3:
                    findEachMenu();
                    break;
                case 4:
                    modifyMenu();
                    break;
                case 5:
                    removeMenu();
                    break;
                case 6:
                    trashMenu();
                    break;
                case 9:
                    System.out.println("\n# 프로그램을 종료합니다.");
                    System.exit(0);
                    return;
                default:
                    System.out.println("\n# 메뉴를 다시 입력하세요.");
            }
        }


    }

    private void findEachMenu() {
        while (true) {
            System.out.println("\n======= 스케쥴 개별 조회 메뉴 ========");
            System.out.println("# 1. 스케쥴 아이디로 조회하기");
            System.out.println("# 2. 스케쥴 카테고리로 조회하기");
            System.out.println("# 9. 메뉴로 돌아가기");

            int menu = inputN("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    findOneMenu();
                    break;
                case 2:
                    sc.nextLine();
                    System.out.print("조회할 카테고리: ");
                    String category = sc.nextLine();
                    findCategoryMenu(category);
                    break;
                case 9:
                    System.out.println("메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("\n# 메뉴를 다시 입력하세요.");
            }
        }
    }


    private void trashMenu() {
        while (true) {
            System.out.println("\n======= 휴지통 ========");
            System.out.println("# 1. 휴지통 보기");
            System.out.println("# 2. 휴지통 비우기");
            System.out.println("# 9. 메뉴로 돌아가기");

            int menu = inputN("\n메뉴 입력: ");

            switch (menu) {
                case 1:
                    viewTrash();
                    break;
                case 2:
                    emptyTrash();
                    break;
                case 9:
                    System.out.println("메뉴로 돌아갑니다.");
                    return;
                default:
                    System.out.println("\n# 메뉴를 다시 입력하세요.");
            }
        }
    }

    private void emptyTrash() {
        System.out.println("정말로 휴지통을 비우시겠습니까? Y/N");
        sc.nextLine();
        String choice = sc.nextLine().toLowerCase();

        switch (choice) {
            case "y":
                controller.emptyTrash();
                break;
            case "n":

                break;
            default:
                System.out.println("\n# Y 또는 N으로 다시 입력하세요.");
        }

    }

    private void viewTrash() {
        List<Schedule> schedules = controller.viewTrash();

        System.out.println("\n================================================================== 휴지통 리스트 ==================================================================");
        System.out.printf("%10s%15s%30s%30s%30s%15s\n"
                , "아이디", "카테고리", "이름", "날짜/시간", "장소", "메모");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Schedule s : schedules) {
            System.out.printf("%10d%20s%30s%30s%30s%15s\n"
                    , s.getScheduleId(), s.getCategory(), s.getScheduleName(), s.getDateTime(), s.getLocation(), s.getNote());
        }
    }

    // 5번 메뉴
    private void removeMenu() {
        System.out.println("\n# 삭제할 스케쥴 번호를 입력하세요!");
        int scheduleId = inputN(">>> ");

        if (controller.hasSchedule(scheduleId)) {
            boolean flag = controller.deleteSchedule(scheduleId);
            if (flag) {
                System.out.println("# 삭제가 완료되었습니다.");
            } else {
                System.out.println("# 삭제를 실패했습니다.");
            }
        } else {
            System.out.println("\n# 해당 스케쥴은 존재하지 않습니다.");
        }
    }

    // 4번 메뉴
    private void modifyMenu() {

        System.out.println("\n# 수정할 스케쥴의 번호를 입력하세요!");
        int scheduleId = inputN(">>> ");

        if (controller.hasSchedule(scheduleId)) {
            sc.nextLine();
            System.out.println("# 수정할 정보들을 입력하세요.");
            System.out.print("- Category: ");
            String category = sc.nextLine();
            System.out.print("- Schedule Name: ");
            String name = sc.nextLine();
            System.out.print("- Date and Time: ");
            String dateTime = sc.nextLine();
            System.out.print("- Location: ");
            String location = sc.nextLine();
            System.out.print("- Note: ");
            String note = sc.nextLine();

            boolean flag = controller.updateSchedule(scheduleId, category, name, dateTime, location, note);
            if (flag) {
                System.out.println("# 수정이 완료되었습니다.");
            } else {
                System.out.println("# 수정이 실패했습니다.");
            }

        } else {
            System.out.println("\n# 해당 스케쥴은 존재하지 않습니다.");
        }
    }

    // 3번 메뉴
    private void findOneMenu() {
        System.out.println("\n조회할 스케쥴 번호를 입력하세요!");
        int scheduleId = inputN(">>> ");

        Schedule schedule = controller.findOneSchedule(scheduleId);
        if (schedule != null) {
            System.out.println("\n# 조회 결과");
            System.out.println("- 아이디: " + schedule.getScheduleId());
            System.out.println("- 카테고리: " + schedule.getCategory());
            System.out.println("- 이름: " + schedule.getScheduleName());
            System.out.println("- 날짜/시간: " + schedule.getDateTime());
            System.out.println("- 장소: " + schedule.getLocation());
            System.out.println("- 메모: " + schedule.getNote());
        } else {
            System.out.println("\n# 해당 스케쥴은 존재하지 않습니다.");
        }
    }

    // 2번 메뉴
    private void findAllMenu() {

        List<Schedule> schedules = controller.findAllSchedules();

        System.out.println("\n================================================================= 모든 스케쥴 정보 =================================================================");
        System.out.printf("%10s%15s%30s%30s%30s%15s\n"
                , "아이디", "카테고리", "이름", "날짜/시간", "장소", "메모");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Schedule s : schedules) {
            System.out.printf("%10d%20s%30s%30s%30s%15s\n"
                    , s.getScheduleId(), s.getCategory(), s.getScheduleName(), s.getDateTime(), s.getLocation(), s.getNote());
        }

    }

    private void findCategoryMenu(String category) {
        List<Schedule> schedules = controller.findScheduleByCategory(category);

        System.out.println("\n================================================================= 모든 스케쥴 정보 =================================================================");
        System.out.printf("%10s%15s%30s%30s%30s%15s\n"
                , "아이디", "카테고리", "이름", "날짜/시간", "장소", "메모");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Schedule s : schedules) {
            System.out.printf("%10d%20s%30s%30s%30s%15s\n"
                    , s.getScheduleId(), s.getCategory(), s.getScheduleName(), s.getDateTime(), s.getLocation(), s.getNote());
        }
    }

    // 1번 메뉴
    private void insertMenu() {

        System.out.println("\n# 스케쥴 정보 입력을 시작합니다.");
        sc.nextLine();
        System.out.print("- Category: ");
        String category = sc.nextLine();
        System.out.print("- Schedule Name: ");
        String name = sc.nextLine();
        System.out.print("- Date and Time: ");
        String dateTime = sc.nextLine();
        System.out.print("- Location: ");
        String location = sc.nextLine();
        System.out.print("- Note: ");
        String note = sc.nextLine();

        Schedule sc = new Schedule();
        sc.setCategory(category);
        sc.setScheduleName(name);
        sc.setDateTime(dateTime);
        sc.setLocation(location);
        sc.setNote(note);

        controller.insertSchedule(sc);

        System.out.println("\n스케쥴 정보가 저장되었습니다.");

    }

    // 숫자입력 메서드
    private int inputN(String msg) {
        int n;
        while (true) {
            try {
                System.out.print(msg);
                n = sc.nextInt();
                break;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("# 정수로만 입력하세요");
            }
        }
        return n;
    }
}
