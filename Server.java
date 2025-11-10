import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class Server {
    private static final int DEFAULT_PORT = 1234;     
    private static final int THREAD_POOL_SIZE = 10;    

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        System.out.println("[Server] Starting on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // 클라이언트 연결 대기
            while (true) {
                Socket clientSocket = serverSocket.accept(); // 새 클라이언트 연결 수락
                System.out.println("[Server] Client connected: " + clientSocket.getInetAddress());
                
                
                executor.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            // 서버 소켓 오류 처리
            System.out.println("[Server] Error: " + e.getMessage());
        } finally {
            // 서버 종료 시 스레드 풀 정리
            executor.shutdown();
            System.out.println("[Server] Executor service shut down.");
        }
    }
}