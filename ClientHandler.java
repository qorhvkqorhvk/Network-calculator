import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String request;
            System.out.println("[Server] New Client connected: " + socket.getInetAddress());

            while ((request = in.readLine()) != null) {
                System.out.println("[Server] Received from " + socket.getInetAddress() + ": " + request);
                String response = handleRequest(request);
                
                //결과를 클라이언트로 전송
                out.println(response); 
                System.out.println("[Server] Sent response to " + socket.getInetAddress() + ".");
            }
        } catch (IOException e) {
            System.out.println("[Server] Client disconnected: " + socket.getInetAddress());
        } finally {
            //연결 종료시 소켓 닫기
            try {
                if (!socket.isClosed()) socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
    private String handleRequest(String request) {
        String[] parts = Protocol.parseRequest(request);
        
        // argument 개수가 많은 경우
        if (parts.length > 3) {
             return Protocol.createErrorMessage(Protocol.ERR_TOO_MANY_ARGUMENTS);
        }
        // 명령어만 있거나 argument가 부족한 경우
        if (parts.length < 3) {
            return Protocol.createErrorMessage(Protocol.ERR_INVALID_ARGUMENTS);
        }
        String op = parts[0];
        try {
            double a = Double.parseDouble(parts[1]);
            double b = Double.parseDouble(parts[2]);
            
            // Calculator를 통해 계산 및 예외 처리
            double result = Calculator.calculate(op, a, b);
            return Protocol.createResultMessage(result);
        } catch (NumberFormatException e) {
            // 숫자가 아닌 argument 처리
            return Protocol.createErrorMessage(Protocol.ERR_NOT_A_NUMBER);
        } catch (IllegalArgumentException e) {
            // Calculator에서 발생한 오류 처리
            return Protocol.createErrorMessage(e.getMessage());
        }
    }
}