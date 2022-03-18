package com.wallet.controllers;

import com.wallet.dto.TransactionDTO;
import com.wallet.mapper.TransactionMapper;
import com.wallet.services.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */


@Api(tags = "Transaction Controller")
@Lazy
@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions")
@Profile("transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @ApiOperation(value = "Get transactions from Logged User")
    @GetMapping("/my-transactions")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<TransactionDTO>> myExtract() {
        List<TransactionDTO> transactionDTO = TransactionMapper.INSTANCE.toDTOs(transactionService.loggedExtract());
        return ResponseEntity.ok(transactionDTO);
    }

    @ApiOperation(value = "Get pretty transactions from Logged User")
    @GetMapping("/my-pretty-transactions")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<List<String>> myPrettyExtract() {
        return ResponseEntity.ok(transactionService.loggedPrettyExtract());
    }

}
