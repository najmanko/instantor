package cz.najmanko.dao;

import cz.najmanko.model.Response;
import cz.najmanko.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ResponseDao {
    
    @Autowired
    private ResponseRepository repository;
    
    public List<Response> loadRepositoryList() {
        return repository.findAll();
    }

    @Transactional
    public void saveResponseJson(Response response) {
        repository.save(response);
    }
}