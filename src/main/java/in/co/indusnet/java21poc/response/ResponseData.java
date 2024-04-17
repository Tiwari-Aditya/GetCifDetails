package in.co.indusnet.java21poc.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class ResponseData {
    public Map<String, String> responseData = new HashMap<>();
    private String statusMessage;
    private int statusCode;
}
