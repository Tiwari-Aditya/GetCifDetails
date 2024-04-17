package in.co.indusnet.java21poc.directSoapCalling;

import in.co.indusnet.java21poc.dto.DirectSoapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.web.util.HtmlUtils.htmlUnescape;

@Configuration
@PropertySource("classpath:constant/constantEnv.properties")
@PropertySource("classpath:constant/constant.properties")
public class DirectSoapClient {
    @Autowired
    private static Environment environment;

    public static DirectSoapResponse callSoapService(String soapRequest, String url, String soapAction) {
        DirectSoapResponse directSoapResponse = new DirectSoapResponse();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // String env=environment.getProperty("spring.profiles.active");
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            con.setRequestProperty("Accept", "text/xml");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("SOAPAction", soapAction);
            con.setRequestProperty("Content-length", "" + soapRequest.length());


            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(soapRequest);
            wr.flush();
            wr.close();
            String responseStatus = con.getResponseMessage();
            directSoapResponse.setResponseStatus(responseStatus);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String finalvalue = response.toString();

            finalvalue = htmlUnescape(finalvalue);

            directSoapResponse.setResponse(finalvalue);

        } catch (Exception e) {
            e.printStackTrace();
            directSoapResponse.setResponse(e.getMessage());
            directSoapResponse.setResponseStatus(HttpStatus.NO_CONTENT.getReasonPhrase());
        }
        return directSoapResponse;
    }

}
