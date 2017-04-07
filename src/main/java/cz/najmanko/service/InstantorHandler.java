package cz.najmanko.service;

import com.instantor.api.InstantorException;
import cz.najmanko.controller.InstantorResponse;
import cz.najmanko.dao.ResponseDao;
import cz.najmanko.model.Request;
import cz.najmanko.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class InstantorHandler {

    @Autowired
    private ResponseDao responseDao;

    @Autowired
    private Decryptor decryptor;

    public void decryptAndPersistResponse(InstantorResponse instantorResponse) throws InstantorException {
        if (!instantorResponse.getSource().equalsIgnoreCase(Decryptor.SOURCE)) {
            throw new InstantorException("Instantor source could not be matched!",
                    new IllegalArgumentException(instantorResponse.getSource()));
        }
        Response response = generateResponse(instantorResponse);

        responseDao.saveResponseJson(response);
    }

    public void saveRequest(String instantorRequest) throws InstantorException {
        Request request = new Request();
        request.setRequest(instantorRequest);

        responseDao.saveRequest(request);
    }

    private Response generateResponse(InstantorResponse instantorResponse) {

        String json = decryptor.decryptJson(instantorResponse);

        Response response = new Response();
        response.setMsgId(instantorResponse.getMsgId());
        response.setAction(instantorResponse.getAction());
        response.setEncryption(instantorResponse.getEncryption());
        response.setPayload(instantorResponse.getPayload());
        response.setHash(instantorResponse.getHash());
        response.setDelivered(parseDate(instantorResponse.getTimestamp()));
        response.setSaved(new Date());
        response.setJson(json);

        return response;
    }

    private Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        try {
            return Decryptor.DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
