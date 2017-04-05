package cz.najmanko.dao;

import cz.najmanko.model.Response;
import cz.najmanko.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class ResponseDao {
    
    @Autowired
    private ResponseRepository repository;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    
    public List<Response> loadRepositoryList() {
        return repository.findAll();
    }

    @Transactional
    public void saveResponseJson(String json, String date) {
        Response response = new Response();
        response.setDelivered(parseDate(date));
        response.setSaved(new Date());
        response.setJson(json);
        
        repository.save(response);
    }
    
    private Date parseDate(String date) {
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
