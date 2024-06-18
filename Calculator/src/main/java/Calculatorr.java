import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Calculatorr {
    private static ArrayList<String> history = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("--------------MÁY TÍNH CƠ BẢN--------------");
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Nhập phép tính của bạn: ");
                    String inputCalculation = scanner.nextLine();
                    runCalculationInBackground(inputCalculation);
                    break;
                case 2:
                    runDisplayHistoryInBackground();
                    break;
                case 3:
                    runClearHistoryInBackGround();
                    break;
                case 4:
                    System.out.println("GOODBYE");
                    System.out.println("---------------------------------------------");
                    running = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("|" + "                                           |");
        System.out.println("|" + "  1. Thực hiện phép tính" + "                   | ");
        System.out.println("|" + "  2. Hiển thị lịch sử" + "                      | ");
        System.out.println("|" + "  3. Xóa lịch sử" + "                           | ");
        System.out.println("|" + "  4. Thoát" + "                                 | ");
        System.out.print("|" + "  Nhập lựa chọn của bạn " + "                  |\n ");
    }

    // Phương thức để tính toán chạy dưới dạng background
    private static void runCalculationInBackground(String input) {
        Thread calculationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                calculate(input);
            }
        });
        calculationThread.start();
    }

    // Phương thức để hiển thị lịch sử chạy dưới dạng background
    private static void runDisplayHistoryInBackground() {
        Thread historyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                displayHistory();
            }
        });
        historyThread.start();
    }

    private static void runClearHistoryInBackGround(){
        Thread clearHistoryThread = new Thread(new Runnable() {
            @Override
            public void run() {
                clearHistory();
            }
        });
        clearHistoryThread.start();
    }

    public static void calculate(String input) {
        String[] tokens = input.split(" ");

        try {
            double result = Double.parseDouble(tokens[0]);
            String calculation = tokens[0];
            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                    double operand = Double.parseDouble(tokens[i + 1]);
                calculation += " " + operator + " " + tokens[i + 1];

                switch (operator) {
                    case "+":
                        result += operand;
                        break;
                    case "-":
                        result -= operand;
                        break;
                    case "*":
                        result *= operand;
                        break;
                    case "/":
                        if (operand != 0) {
                            result /= operand;
                        } else {
                            System.out.println("Lỗi : không chia hết cho 0!");
                            return;
                        }
                        break;
                    default:
                        System.out.println(" Phép tính không hợp lể , vui lòng thử lại ");
                        return;
                }
            }

//            String resultString = Double.toString(result);
//            //Thêm phép tính vào lịch sử
//            history.add(calculation + " = " + resultString);

//            try {
//                Thread.sleep(10000);
                System.out.println("Kết quả: " + input + " = " + result);
                System.out.println("---------------------------------------------");
                String resultString = Double.toString(result);
                //Thêm phép tính vào lịch sử
                history.add(calculation + " = " + resultString);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public static void displayHistory() {
        System.out.println("\nLịch sử phép tính:");
        if (history.isEmpty()) {
            System.out.println(" Rỗng, chưa có phép tính nào được thực hiện ");
            System.out.println("---------------------------------------------");
        } else {
            for (String entry : history) {
                System.out.println(entry);
            }
            System.out.println("---------------------------------------------");
        }
    }

    public static void clearHistory(){
        history.clear();
        System.out.println("Xóa dữ liệu thành công");
        System.out.println("---------------------------------------------");
    }
}
