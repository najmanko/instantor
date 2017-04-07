package cz.najmanko.service;

import com.instantor.api.InstantorException;
import com.instantor.api.InstantorParams;
import cz.najmanko.controller.InstantorResponse;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class Decryptor {

    public static final String SOURCE = "moneta.cz";

    private static final String API_KEY = "'V)Av'/hW+BH.qXea,s2926E";
    //FOR TEST:
    //private static final String API_KEY = "test_API_key";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    public String decryptJson(InstantorResponse response) {
        try {
            return InstantorParams.loadResponse(
                    SOURCE,
                    API_KEY,
                    response.getMsgId(),
                    response.getAction(),
                    response.getEncryption(),
                    response.getPayload(),
                    getTimestamp(response.getTimestamp()),
                    response.getHash());
            
        } catch (final InstantorException e) {
            System.out.println("An error has occured!");
            e.printStackTrace();
            return e.getCause().getMessage();
        }
    }
    
    private String getTimestamp(String timestamp) {
        return timestamp != null ? timestamp : DATE_FORMAT.format(new Date());
    }
}
