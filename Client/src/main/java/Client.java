import com.yyz.elasticsearch.SearchService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Created by yaoyongzhen on 18/4/29
 */
public class Client {
    public static void main(String[] args) {
        String configName = "client.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{configName});
        context.start();
        SearchService searchService = (SearchService) context.getBean("demoService");

        while (true) {
            try {
                Thread.sleep(1000);
                // 执行远程方法
                String hello = searchService.search("你怕黑吗？");
                // 显示调用结果
                System.out.println( hello );
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }
}
