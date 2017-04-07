package cz.najmanko.controller;

import com.instantor.api.InstantorException;
import cz.najmanko.service.InstantorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class InstantorController {

    @Autowired
    private InstantorHandler instantorHandler;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String readInstantorStatement(@RequestParam Map<String, String> requestParams) throws InstantorException {

        instantorHandler.saveRequest(requestParams.toString());
        
        InstantorResponse instantorResponse = new InstantorResponse();
        instantorResponse.setSource(requestParams.get("source"));
        instantorResponse.setMsgId(requestParams.get("msg_id"));
        instantorResponse.setAction(requestParams.get("action"));
        instantorResponse.setEncryption(requestParams.get("encryption"));
        instantorResponse.setPayload(requestParams.get("payload"));
        instantorResponse.setTimestamp(requestParams.get("timestamp"));
        instantorResponse.setHash(requestParams.get("hash"));

        instantorHandler.decryptAndPersistResponse(instantorResponse);
        return "OK: " + instantorResponse.getMsgId();
    }
}