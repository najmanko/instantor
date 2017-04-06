package cz.najmanko.controller;

import com.instantor.api.InstantorException;
import com.instantor.api.InstantorParams;
import cz.najmanko.dao.ResponseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class InstantorController {

    private static final String SOURCE = "test_source";
    
    private static final String API_KEY = "'V)Av'/hW+BH.qXea,s2926E";
    //FOR TEST:
//    private static final String API_KEY = "test_API_key";
    
    @Autowired
    private ResponseDao responseDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public String readInstantorStatement(@RequestParam Map<String,String> requestParams) throws InstantorException {

        InstantorResponse instantorResponse = new InstantorResponse();
        instantorResponse.setSource(requestParams.get("source"));
        instantorResponse.setMsgId(requestParams.get("msg_id"));
        instantorResponse.setAction(requestParams.get("action"));
        instantorResponse.setEncryption(requestParams.get("encryption"));
        instantorResponse.setPayload(requestParams.get("payload"));
        instantorResponse.setTimestamp(requestParams.get("timestamp"));
        instantorResponse.setHash(requestParams.get("hash"));

        encryptAndPersistResponse(instantorResponse);
        return "OK: " + instantorResponse.getMsgId();
    }
    
    private void encryptAndPersistResponse(InstantorResponse response) throws InstantorException {
        if (!response.getSource().equalsIgnoreCase(SOURCE)) {
            throw new InstantorException("Instantor source could not be matched!",
                    new IllegalArgumentException(response.getSource()));
        }
        String json = encryptJson(response);

        responseDao.saveResponseJson(json, response.getTimestamp());
    }

    private String encryptJson(InstantorResponse response) {
        try {
            return InstantorParams.loadResponse(
                    SOURCE,
                    API_KEY,
                    response.getMsgId(),
                    response.getAction(),
                    response.getEncryption(),
                    response.getPayload(),
                    response.getTimestamp(),
                    response.getHash());

            
        } catch (final InstantorException e) {
            System.out.println("An error has occured!");
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }
}