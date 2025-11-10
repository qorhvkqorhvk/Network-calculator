import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Config config = new Config("server_info.dat");

        try (
            Socket socket = new Socket(config.getHost(), config.getPort()); //서버와 소켓 연결 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
        ) {
            System.out.println("[Client] Connected to server at " + config.getHost() + ":" + config.getPort());
            System.out.println("Type commands like: ADD 10 20");
            System.out.println("Type EXIT to quit.");

            while (true) {
                System.out.print(">> ");
                String command = sc.nextLine();
                if (command.equalsIgnoreCase("EXIT")) break;

                out.println(command);
                
                // 서버 응답 수신 
                String statusLine = in.readLine(); // 예: STATUS: OK 또는 STATUS: ERROR
                String fieldLine = in.readLine();  // 예: VALUE: 30.0 또는 ERROR_TYPE: TOO_MANY_ARGUMENTS
                // 서버와 연결이 끊어진 경우
                if (statusLine == null || fieldLine == null) {
                    System.out.println("[Client] Server closed connection.");
                    break;
                }
                
                System.out.println("--- Server Response ---");
                // 서버 응답 출력
                System.out.println("Status: " + statusLine);
                System.out.println("Result/Error: " + fieldLine);
                System.out.println("-----------------------");
            }

        } catch (IOException e) {
            //서버 연결 실패 처리   
            System.out.println("[Client] Connection failed: " + e.getMessage());
        }
    }
}