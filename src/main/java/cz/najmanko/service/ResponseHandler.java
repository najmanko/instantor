package cz.najmanko.service;

import com.instantor.api.InstantorException;
import cz.najmanko.controller.InstantorResponse;
import cz.najmanko.dao.ResponseDao;
import cz.najmanko.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ResponseHandler {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");

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

    private Response generateResponse(InstantorResponse instantorResponse) {

        String json = decryptor.decryptJson(instantorResponse);

        Response response = new Response();
        response.setMsgId(instantorResponse.getMsgId());
        response.setAction(instantorResponse.getAction());
        response.setDelivered(parseDate(instantorResponse.getTimestamp()));
        response.setSaved(new Date());
        response.setJson(json);

        return response;
    }

    private Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}