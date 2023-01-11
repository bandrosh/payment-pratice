package com.pismo.paymentpractice.controller.v1;

import com.pismo.paymentpractice.controller.dto.AccountDTO;
import com.pismo.paymentpractice.controller.dto.AccountRequestDTO;
import com.pismo.paymentpractice.service.AccountService;
import com.pismo.paymentpractice.service.PaymentPracticeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;


@RestController
@RequestMapping("/api/v1")
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class.getName());
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountRequestDTO accountRequestDTO) {
        logger.debug(() ->
                format("Trying create user account with document number %s", accountRequestDTO.documentNumber()));
        var generatedAccountId = PaymentPracticeUtil.generateAccountId();
        logger.debug(() -> format("Generate account id for new user. Id: %s", generatedAccountId));
        var account = accountService.saveAccount(accountRequestDTO.toAccount(generatedAccountId));
        return ResponseEntity.ok(new AccountDTO(account.id(), account.documentNumber()));
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable String accountId) {
        logger.debug(() -> format("Trying get user account with id %s.", accountId));
        var account = accountService.getUserAccountById(accountId);
        return ResponseEntity.ok(new AccountDTO(account.id(), account.documentNumber()));
    }
}
