package in.co.indusnet.java21poc.controller;

import in.co.indusnet.java21poc.directSoapCalling.IblWebSchema;
import in.co.indusnet.java21poc.response.ResponseData;
import in.co.indusnet.java21poc.utility.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CifController {
    @Autowired
    private IblWebSchema service;

    @GetMapping("/healthCheck")
    public String cifDetials(){
        return "Sucess " + LocalDateTime.now();
    }

    @GetMapping("/getCifDetialsAuth")
    public  ResponseEntity<ResponseData> getCifDetailsAuth(@RequestBody String cifID, @RequestBody Long reqId ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

        Map<String ,String> responseData = new HashMap<>();
        String data =  service.getCifDetails(cifID,reqId);
        responseData.put(data,data);
        ResponseData response = ResponseHelper.responseSenderData("Success",
                HttpStatus.OK.value(),responseData);
        return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String test(){
        return "Hello World";
    }

    @GetMapping("/getCifDetials")
    public ResponseEntity<ResponseData> getCifDetails(@RequestBody String cifID, @RequestBody Long reqId ){

        Map<String ,String> responseData = new HashMap<>();
        String data =  service.getCifDetails(cifID,reqId);
        responseData.put(data,data);
        ResponseData response = ResponseHelper.responseSenderData("Success",
                HttpStatus.OK.value(),responseData);
        return new ResponseEntity<ResponseData>(response,HttpStatus.OK);
    }


}
