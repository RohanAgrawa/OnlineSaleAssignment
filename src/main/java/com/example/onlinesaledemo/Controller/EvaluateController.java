package com.example.onlinesaledemo.Controller;

import com.example.onlinesaledemo.Component.ThreadPooling;
import com.example.onlinesaledemo.ResponseDTO.RequestDTO;
import com.example.onlinesaledemo.ResponseDTO.Response;
import com.example.onlinesaledemo.ResponseDTO.ResponseDTO;
import com.example.onlinesaledemo.Services.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class EvaluateController {

    private final ThreadPooling threadPooling;
    private EvaluateService evaluateService;

    @Autowired
    public EvaluateController(ThreadPooling threadPooling, EvaluateService evaluateService){
        this.threadPooling = threadPooling;
        this.evaluateService = evaluateService;
    }

    @PostMapping("/Evaluate/Expression/")
    public ResponseDTO expressionEvaluate(@RequestBody() RequestDTO expression){

        ExecutorService es = threadPooling.getEs();


        evaluateService.setExpression(expression.getExpression());

        Future<Integer> promisedResult = es.submit(evaluateService);

        ResponseDTO response = new ResponseDTO();

        try {
            Integer result = promisedResult.get();

            response.setResponse(Response.Success);
            response.setAnswer(result);
            response.setMessage("Expression Evaluated Successfully");
        }
        catch (Exception e){

            response.setResponse(Response.Failure);
            response.setMessage(e.getMessage());
            response.setAnswer(0);
        }

        return response;

    }
}
