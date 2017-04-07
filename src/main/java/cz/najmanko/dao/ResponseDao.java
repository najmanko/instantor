package cz.najmanko.dao;

import cz.najmanko.model.Request;
import cz.najmanko.model.Response;
import cz.najmanko.repository.RequestRepository;
import cz.najmanko.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ResponseDao {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private RequestRepository requestRepository;
    
    public List<Response> loadRepositoryList() {
        return responseRepository.findAll();
    }

    @Transactional
    public void saveResponseJson(Response response) {
        responseRepository.save(response);
    }

    @Transactional
    public void saveRequest(Request request) {
        requestRepository.save(request);
    }
}