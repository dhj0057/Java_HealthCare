import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class HealthRecordApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HealthManager manager = new HealthManager();

        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("     하루핏 건강 관리 프로그램");
            System.out.println("=================================");
            System.out.println("1. 식단 기록 추가");
            System.out.println("2. 운동 기록 추가");
            System.out.println("3. 수분 섭취량 입력");
            System.out.println("4. 하루 건강 점수 확인");
            System.out.println("5. 전체 기록 보기");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            int menu = sc.nextInt();
            sc.nextLine();

            if (menu == 1) {
                System.out.println();
                System.out.println("[식단 기록 추가]");
                System.out.print("음식 이름: ");
                String foodName = sc.nextLine();

                System.out.print("칼로리 입력(kcal): ");
                int calorie = sc.nextInt();
                sc.nextLine();

                manager.addFood(foodName, calorie);
                System.out.println("식단 기록이 추가되었습니다.");

            } else if (menu == 2) {
                System.out.println();
                System.out.println("[운동 기록 추가]");
                System.out.print("운동 이름: ");
                String exerciseName = sc.nextLine();

                System.out.print("운동 시간 입력(분): ");
                int minute = sc.nextInt();
                sc.nextLine();

                manager.addExercise(exerciseName, minute);
                System.out.println("운동 기록이 추가되었습니다.");

            } else if (menu == 3) {
                System.out.println();
                System.out.println("[수분 섭취량 입력]");
                System.out.print("오늘 마신 물의 양 입력(L): ");
                double water = sc.nextDouble();
                sc.nextLine();

                manager.setWater(water);
                System.out.println("수분 섭취량이 저장되었습니다.");

            } else if (menu == 4) {
                manager.printHealthResult();

            } else if (menu == 5) {
                manager.printAllRecords();

            } else if (menu == 0) {
                System.out.println("하루핏 프로그램을 종료합니다.");
                break;

            } else {
                System.out.println("잘못된 메뉴입니다. 다시 선택하세요.");
            }
        }

        sc.close();
    }
}

class Food {
    private String name;
    private int calorie;

    public Food(String name, int calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public int getCalorie() {
        return calorie;
    }
}

class Exercise {
    private String name;
    private int minute;

    public Exercise(String name, int minute) {
        this.name = name;
        this.minute = minute;
    }

    public String getName() {
        return name;
    }

    public int getMinute() {
        return minute;
    }
}

class HealthManager {
    private ArrayList<Food> foodList;
    private ArrayList<Exercise> exerciseList;
    private double water;

    public HealthManager() {
        foodList = new ArrayList<>();
        exerciseList = new ArrayList<>();
        water = 0;
    }

    public void addFood(String name, int calorie) {
        Food food = new Food(name, calorie);
        foodList.add(food);
    }

    public void addExercise(String name, int minute) {
        Exercise exercise = new Exercise(name, minute);
        exerciseList.add(exercise);
    }

    public void setWater(double water) {
        this.water = water;
    }

    public int getTotalCalorie() {
        int total = 0;

        for (Food food : foodList) {
            total += food.getCalorie();
        }

        return total;
    }

    public int getTotalExerciseMinute() {
        int total = 0;

        for (Exercise exercise : exerciseList) {
            total += exercise.getMinute();
        }

        return total;
    }

    public int calculateHealthScore() {
        int score = 100;

        int totalCalorie = getTotalCalorie();
        int totalExerciseMinute = getTotalExerciseMinute();

        if (totalCalorie < 1500) {
            score -= 10;
        } else if (totalCalorie > 2500) {
            score -= 10;
        }

        if (totalExerciseMinute < 30) {
            score -= 15;
        }

        if (water < 1.5) {
            score -= 10;
        }

        if (score < 0) {
            score = 0;
        }

        return score;
    }

    public void printHealthResult() {
        DecimalFormat df = new DecimalFormat("0.0");

        int totalCalorie = getTotalCalorie();
        int totalExerciseMinute = getTotalExerciseMinute();
        int score = calculateHealthScore();

        System.out.println();
        System.out.println("=================================");
        System.out.println("        하루 건강 분석 결과");
        System.out.println("=================================");
        System.out.println("날짜: " + LocalDate.now());
        System.out.println("총 섭취 칼로리: " + totalCalorie + " kcal");
        System.out.println("총 운동 시간: " + totalExerciseMinute + "분");
        System.out.println("수분 섭취량: " + df.format(water) + " L");
        System.out.println("---------------------------------");
        System.out.println("건강 점수: " + score + "점");
        System.out.println("---------------------------------");
        System.out.println("분석 결과:");

        if (totalCalorie == 0) {
            System.out.println("- 식단 기록이 없습니다. 식단을 먼저 입력하세요.");
        } else if (totalCalorie < 1500) {
            System.out.println("- 섭취 칼로리가 부족합니다. 균형 잡힌 식사를 추가하세요.");
        } else if (totalCalorie > 2500) {
            System.out.println("- 섭취 칼로리가 높은 편입니다. 고칼로리 음식 섭취를 조절하세요.");
        } else {
            System.out.println("- 칼로리 섭취량은 적정 범위입니다.");
        }

        if (totalExerciseMinute == 0) {
            System.out.println("- 운동 기록이 없습니다. 가벼운 걷기부터 시작하세요.");
        } else if (totalExerciseMinute < 30) {
            System.out.println("- 운동 시간이 부족합니다. 하루 30분 이상 운동을 권장합니다.");
        } else {
            System.out.println("- 운동 시간이 충분합니다.");
        }

        if (water < 1.5) {
            System.out.println("- 수분 섭취량이 부족합니다. 물을 조금 더 섭취하세요.");
        } else {
            System.out.println("- 수분 섭취량이 적절합니다.");
        }

        System.out.println("=================================");
    }

    public void printAllRecords() {
        System.out.println();
        System.out.println("=================================");
        System.out.println("          전체 기록 보기");
        System.out.println("=================================");
        System.out.println("날짜: " + LocalDate.now());

        System.out.println();
        System.out.println("[식단 기록]");
        if (foodList.isEmpty()) {
            System.out.println("식단 기록이 없습니다.");
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                Food food = foodList.get(i);
                System.out.println((i + 1) + ". " + food.getName() + " - " + food.getCalorie() + " kcal");
            }
        }

        System.out.println();
        System.out.println("[운동 기록]");
        if (exerciseList.isEmpty()) {
            System.out.println("운동 기록이 없습니다.");
        } else {
            for (int i = 0; i < exerciseList.size(); i++) {
                Exercise exercise = exerciseList.get(i);
                System.out.println((i + 1) + ". " + exercise.getName() + " - " + exercise.getMinute() + "분");
            }
        }

        System.out.println();
        System.out.println("[수분 섭취량]");
        System.out.println(water + " L");

        System.out.println("=================================");
    }
}