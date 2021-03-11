package sk.kosickaakademia.lenart;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PresidentController {
    String SVK = "Slovakia, Zuzana Caputova";
    String CZE = "Czechia, Milos Zeman";
    String USA = "United States of America, Joe Biden";
    String FRA = "France, Emmanuel Macron";
    String HUN = "Hungary, Janos Ader";
    String POL = "Poland, Andrzej Duda";
    String AUT = "Austria, Alexander Van der Bellen";
    String UKR = "Ukraine, Volodymyr Oleksandrovyc Zelenskyj";
    String FIN = "Finland, Sauli Niinisto";
    String GER = "Germany, Frank-Walter Steinmeier";
    List list = new ArrayList<>();
    private Object String;

    public PresidentController() {
        list.add(SVK);
        list.add(CZE);
        list.add(USA);
        list.add(FRA);
        list.add(HUN);
        list.add(POL);
        list.add(AUT);
        list.add(UKR);
        list.add(FIN);
        list.add(GER);
    }

    @GetMapping("list/{president}")
    public ResponseEntity<String> selectPresident(@PathVariable String president){
        JSONObject object = new JSONObject();
        int status;
        if(String != list.toString()) {
            object.put("error","President not found!");
            status = 404;
        }else{
            object.put("president", list.get(Integer.parseInt(president)));
            status = 200;
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON).body(object.toJSONString());
    }
}
