import java.io.*;

public class Config {
    private String host = "localhost";
    private int port = 1234;

    public Config(String filename) {
        System.out.println("[Config] Attempting to load " + filename);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#") || line.isEmpty()) continue; //주석,공백 무시
                
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    
                    if (key.equals("SERVER_IP")) {
                        this.host = value;
                    } else if (key.equals("SERVER_PORT")) {
                        try {
                            this.port = Integer.parseInt(value);
                        } catch (NumberFormatException ignored) {} //실패시 기본 포트 유지
                    }
                }
            }
            System.out.println("[Config] Server info loaded: " + host + ":" + port);
        } catch (IOException e) { //파일 없거나 읽기 오류시 기본값 사용
            System.out.println("[Config] File not found or error. Using default server info (" + host + ":" + port + ")");
        }
    }

    public String getHost() { return host; }
    public int getPort() { return port; }
}