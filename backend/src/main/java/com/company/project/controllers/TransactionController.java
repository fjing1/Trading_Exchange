package com.company.project.controllers;

import com.company.project.controllers.param.TransactionInfoParam;
import com.company.project.controllers.param.UserIdentifyParam;
import com.company.project.pojo.Transaction;
import com.company.project.service.TransactionService;
import com.company.project.util.Result;
import com.company.project.util.ResultGenerator;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

// Annotation to indicate that this class is a REST Controller
@RestController
// Swagger API annotation for documentation
@Api
// Mapping this controller to handle requests at the /transaction endpoint
@RequestMapping("/transaction")
public class TransactionController {
    // Autowiring the TransactionService using @Resource annotation
    @Resource private TransactionService transactionService;

    // Endpoint to handle "buy" transactions
    @PostMapping("/buy")
    public Result buy(@RequestBody TransactionInfoParam transactionInfoParam) {
        // Call the buy method of transactionService with the provided parameters
        // If the transaction is successful, return a success result
        // Otherwise, return a failure result
        if (transactionService.buy(transactionInfoParam)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult();
        }
    }

    @PostMapping("/sell")
    public Result sell(@RequestBody TransactionInfoParam transactionInfoParam) {
        if (transactionService.sell(transactionInfoParam)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult();
        }
    }

    @PostMapping("/log")
    public Result<List<Transaction>> log(@RequestBody UserIdentifyParam userIdentifyParam) {
        List<Transaction> transactionList = transactionService.log(userIdentifyParam.getEmail());
        if (transactionList == null) {
            return ResultGenerator.genFailResult();
        } else {
            return ResultGenerator.genSuccessResult(transactionList);
        }
    }
}
