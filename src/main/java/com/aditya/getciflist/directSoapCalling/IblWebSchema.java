package com.aditya.getciflist.directSoapCalling;

import com.aditya.getciflist.dto.DirectSoapResponse;
import com.aditya.getciflist.exception.ServiceException;
import com.aditya.getciflist.model.TblRequestResponseLog;
import com.aditya.getciflist.repository.TblRequestResponseLogRepository;
import com.aditya.getciflist.utility.Converter;
import com.aditya.getciflist.utility.Generator;
import com.aditya.getciflist.utility.GetErrorMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class IblWebSchema {
    @Autowired
    private Environment environment;

    @Autowired
    private GetErrorMessage getErrorMessage;

    @Autowired
    private TblRequestResponseLogRepository tblRequestResponseLogRepository;

    @Transactional
    public String getCifDetails(String cifId, long reqId) {

        String reqXML = "";
        String endPointUrl = "";
        String activityType = "";

        if (reqId == 0) {
            throw new ServiceException(getErrorMessage.getErrorMessages("UNABLE_TO_PROCESS_ERR"),
                    HttpStatus.UNAUTHORIZED.value());
        } else {
            String getCifDetailsFinacleExecuteServiceRequestId = environment
                    .getProperty("getCifDetailsFinacleExecuteServiceRequestId");
            String getCifDetailsFinacleServiceRequestVersion = environment
                    .getProperty("getCifDetailsFinacleServiceRequestVersion");
            String getCifDetailsFinacleChannelId = environment.getProperty("getCifDetailsFinacleChannelId");
            String getCifDetailsrequestId = environment.getProperty("getCifDetailsrequestId");
            endPointUrl = environment.getProperty("finacleURL");
            activityType = environment.getProperty("getCifDetails");

            String requestUuid = "Req_" + Generator.randomNumberGenerator(13);
            LocalDateTime requestDateTime = LocalDateTime.now();

            reqXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.fiusb.ci.infosys.com\"><soapenv:Header/><soapenv:Body><ns3:executeService xmlns:ns3=\"http://endpoint.fip.infosys.com\"><arg_0_0><![CDATA[<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<FIXML xmlns=\"http://www.finacle.com/fixml\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.finacle.com/fixml executeFinacleScript.xsd\">\n" +
                    "   <Header>\n" +
                    "      <RequestHeader>\n" +
                    "         <MessageKey>\n" +
                    "            <RequestUUID>" + requestUuid + "</RequestUUID>\n" +
                    "            <ServiceRequestId>" + getCifDetailsFinacleExecuteServiceRequestId + "</ServiceRequestId>\n" +
                    "            <ServiceRequestVersion>" + getCifDetailsFinacleServiceRequestVersion + "</ServiceRequestVersion>\n" +
                    "            <ChannelId>" + getCifDetailsFinacleChannelId + "</ChannelId>\n" +
                    "            <LanguageId />\n" +
                    "         </MessageKey>\n" +
                    "         <RequestMessageInfo>\n" +
                    "            <BankId />\n" +
                    "            <TimeZone />\n" +
                    "            <EntityId />\n" +
                    "            <EntityType />\n" +
                    "            <ArmCorrelationId />\n" +
                    "            <MessageDateTime>" + requestDateTime + "</MessageDateTime>\n" +
                    "         </RequestMessageInfo>\n" +
                    "         <Security>\n" +
                    "            <Token>\n" +
                    "               <PasswordToken>\n" +
                    "                  <UserId />\n" +
                    "                  <Password />\n" +
                    "               </PasswordToken>\n" +
                    "            </Token>\n" +
                    "            <FICertToken />\n" +
                    "            <RealUserLoginSessionId />\n" +
                    "            <RealUser />\n" +
                    "            <RealUserPwd />\n" +
                    "            <SSOTransferToken />\n" +
                    "         </Security>\n" +
                    "      </RequestHeader>\n" +
                    "   </Header>\n" +
                    "   <Body>\n" +
                    "      <executeFinacleScriptRequest>\n" +
                    "         <ExecuteFinacleScriptInputVO>\n" +
                    "            <requestId>" + getCifDetailsrequestId + "</requestId>\n" +
                    "         </ExecuteFinacleScriptInputVO>\n" +
                    "         <executeFinacleScript_CustomData>\n" +
                    "            <CIF_ID>" + cifId + "</CIF_ID>\n" +
                    "         </executeFinacleScript_CustomData>\n" +
                    "      </executeFinacleScriptRequest>\n" +
                    "   </Body>\n" +
                    "</FIXML>]]></arg_0_0></ns3:executeService></soapenv:Body></soapenv:Envelope>";
            // Prepare Xml Request, endpointUrl, activity type
        }
        return  logSetAndApiCall(reqId, reqXML, endPointUrl, activityType);

    }

    public String logSetAndApiCall(long reqId, String request, String endPointUrl, String activityType) {

        final TblRequestResponseLog tblRequestResponseLog = new TblRequestResponseLog();
        String iblApiReqId = Generator.randomNumberGenerator(13);
        String jsonResult = "";
        tblRequestResponseLog.setCreatedAt(LocalDateTime.now());
        tblRequestResponseLog.setEndUrl(endPointUrl);
        tblRequestResponseLog.setIblApiReqId(iblApiReqId);
        tblRequestResponseLog.setReqId(reqId);
        tblRequestResponseLog.setActivityType(activityType);
        tblRequestResponseLog.setResponseStatus(null);
        DirectSoapResponse directSoapResponse = null;

        tblRequestResponseLog.setRequest(request);
        tblRequestResponseLog.setRequestTime(LocalDateTime.now());
        directSoapResponse = DirectSoapClient.callSoapService(request, tblRequestResponseLog.getEndUrl(),endPointUrl);


        /* check if any error */
        if (directSoapResponse.getResponseStatus().equalsIgnoreCase("OK")) {
            // no exception was thrown

            String soapResponse = directSoapResponse.getResponse();
            tblRequestResponseLog.setResponse(soapResponse);

            if (!soapResponse.isEmpty()) {
                jsonResult = Converter.xmlToJson(soapResponse);

            }

        } else {
            // exception occurred,

            tblRequestResponseLog.setResponse(directSoapResponse.getResponse());
        }

        /* save to request response log asynchronously, fallback silently if error */

        try {
            tblRequestResponseLog.setResponseTime(LocalDateTime.now());
            tblRequestResponseLog.setUpdatedAt(LocalDateTime.now());
            CompletableFuture.supplyAsync(() -> tblRequestResponseLogRepository.save(tblRequestResponseLog));
        } catch (Exception e) {
        }

        return jsonResult;
    }


}
