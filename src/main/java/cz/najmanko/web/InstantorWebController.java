package cz.najmanko.web;

import com.instantor.api.InstantorException;
import cz.najmanko.dao.ResponseDao;
import cz.najmanko.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class InstantorWebController {
    
    private static final String NEW_LINE = System.getProperty("line.separator");

    @Autowired
    private ResponseDao responseDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String readInstantorStatement(@RequestParam Map<String,String> requestParams) throws InstantorException {
        List<Response> responses = responseDao.loadRepositoryList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<xmp>");
        responses.stream().forEach( e -> {
            stringBuilder.append("Saved: " + e.getSaved());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Delivered: " + e.getDelivered());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append("Response JSON:");
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(e.getJson());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(NEW_LINE);
        });
        stringBuilder.append("</xmp>");
        
        return stringBuilder.toString();
    }

    @RequestMapping(value = "/responses", method = RequestMethod.GET)
    @ResponseBody
    public String responses(@RequestParam Map<String,String> requestParams) throws InstantorException {
        return readInstantorStatement(requestParams);
    }
}