import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by admin on 2017/4/4.
 */
public class BaiduJavaDemo extends AbstractJavaSamplerClient {
    private SampleResult sampleResult;
    private HttpClient httpClient;
    private HttpResponse httpResponse;

    public void setupTest(JavaSamplerContext javaSamplerContext){

        httpClient=new DefaultHttpClient();
    }

    public org.apache.jmeter.samplers.SampleResult runTest(JavaSamplerContext javaSamplerContext){
        sampleResult=new SampleResult();
        HttpGet httpGet=new HttpGet("http://www.baidu.com");
        boolean flag=true;
        try {
            sampleResult.sampleStart();
            httpResponse= httpClient.execute(httpGet);
        } catch (IOException e) {
            flag=false;
        }finally {
            sampleResult.sampleEnd();
            if (httpResponse.getStatusLine().getStatusCode()!=200){
                flag=false;
            }
            sampleResult.setSuccessful(flag);
            sampleResult.setSamplerData(httpGet.toString());
            sampleResult.setResponseData(getResponseContent(httpResponse));
        }
        return sampleResult;
    }

    public void teardownTest(JavaSamplerContext javaSamplerContext){

    }

    public Arguments getDefaultParameters(){
        Arguments params = new Arguments();
        params.addArgument("from","北京");
        params.addArgument("to", "上海");
        return params;
    }

    public static String getResponseContent(HttpResponse httpResponse){
        String content="";
        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(httpResponse.getEntity().getContent()));
            String line="";
            while((line = rd.readLine()) != null) {
                content=content+line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {
        HttpClient httpClient1=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet("http://www.baidu.com");
        HttpResponse httpResponse1=null;
        try {
            httpResponse1=httpClient1.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(httpResponse1.getStatusLine().getStatusCode());
    }

}
