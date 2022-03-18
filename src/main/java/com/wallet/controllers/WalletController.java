package com.wallet.controllers;

import com.wallet.dto.WalletDTO;
import com.wallet.mapper.WalletMapper;
import com.wallet.services.WalletService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Giovani Cassiano (gjcassiano@gmail.com)
 */


@Api(tags = "Wallet Controller")
@Lazy
@CrossOrigin
@RestController
@RequestMapping("/api/v1/wallets")
@Profile("wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @ApiOperation(value = "Get informations from Logged User")
    @GetMapping("/my-wallet")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<WalletDTO> deposit() {
        WalletDTO walletDTO = WalletMapper.INSTANCE.toDTO(walletService.loggedWallet());
        return ResponseEntity.ok(walletDTO);
    }

    @ApiOperation(value = "Deposit money to logged user")
    @PostMapping("/deposit/{money}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<WalletDTO> deposit(@PathVariable("money") Long money) {
        WalletDTO walletDTO = WalletMapper.INSTANCE.toDTO(walletService.deposit(money));
        return ResponseEntity.ok(walletDTO);
    }


    @ApiOperation(value = "Withdraw money to logged user")
    @PostMapping("/withdraw/{money}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<WalletDTO> withdraw(@PathVariable("money") Long money) {
        WalletDTO walletDTO = WalletMapper.INSTANCE.toDTO(walletService.withdraw(money));
        return ResponseEntity.ok(walletDTO);
    }


    @ApiOperation(value = "Transfer money from logged user to other user by Key")
    @PostMapping("/transfer/{toKey}/money/{money}")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<WalletDTO> transfer(@PathVariable("toKey") String toKey, @PathVariable("money") Long money) {
        WalletDTO walletDTO = WalletMapper.INSTANCE.toDTO(walletService.transfer(toKey, money));
        return ResponseEntity.ok(walletDTO);
    }

}
