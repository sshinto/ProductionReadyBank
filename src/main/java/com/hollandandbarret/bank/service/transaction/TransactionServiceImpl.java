package com.hollandandbarret.bank.service.transaction;

import com.hollandandbarret.bank.domain.model.Transaction;
import com.hollandandbarret.bank.domain.repository.TransactionRepository;
import com.hollandandbarret.bank.dto.AccountDto;
import com.hollandandbarret.bank.dto.BalanceDto;
import com.hollandandbarret.bank.dto.CustomerDto;
import com.hollandandbarret.bank.dto.TransactionDetails;
import com.hollandandbarret.bank.errors.BankErrors;
import com.hollandandbarret.bank.exception.BankException;
import com.hollandandbarret.bank.service.account.AccountService;
import com.hollandandbarret.bank.service.customer.CustomerService;
import com.hollandandbarret.bank.utils.TransactionType;
import com.hollandandbarret.bank.validations.TransactionValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Created by Savitha Shinto.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CustomerService customerService;
    private final TransactionValidation transactionValidation;
    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService, CustomerService customerService, TransactionValidation transactionValidation) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionValidation = transactionValidation;
    }

    @Override
    public TransactionDetails create(TransactionDetails transactionDetails) throws BankException {
        logger.info("Create Transactions");
        transactionValidation.validateInputFields(transactionDetails);
        try {
            Transaction transaction =  transactionRepository.save(mapDtotoEntity(transactionDetails));
            return getTransactionDto(transaction);
        } catch (Exception ex) {
            throw new BankException(BankErrors.UNKNOWN_ERROR);
        }
    }


    @Override
    public List<BalanceDto> getTransactions(long customerNumber, LocalDateTime from, LocalDateTime to) throws BankException {
        logger.info("Get transactions");
        List<BalanceDto> balanceDtoList = new ArrayList<>();
        CustomerDto customer = customerService.getCustomer(customerNumber);
        List<AccountDto> accounts = accountService.getAllAccountByCustomerId(customerNumber);
        for(AccountDto accountDto: accounts) {
            List<Transaction> transactions = transactionRepository.findTransactionByAccountNumberAndDate(accountDto.getAccountNumber(), from, to);
            List<TransactionDetails> transactionDetails = getListOfTransactionsDto(transactions);
            BalanceDto balanceDto = new BalanceDto();
            balanceDto.setAccountType(accountDto.getAccountType());
            balanceDto.setAccountNumber(accountDto.getAccountNumber());
            balanceDto.setTotalBalance(getBalance(transactionDetails));
            balanceDto.setTransactionFromDate(from);
            balanceDto.setTransactionToDate(to);
            balanceDto.setAmountCredited(getTransactionTypeBalnce(transactionDetails, TransactionType.CREDIT));
            balanceDto.setAmountDebited(getTransactionTypeBalnce(transactionDetails, TransactionType.DEBIT));
            balanceDto.setTransactionDetails(transactionDetails);
            balanceDtoList.add(balanceDto);
        }

        return balanceDtoList;
    }

    private double getBalance(List<TransactionDetails> transactionDetails) {
        double balance = 0;
        for(TransactionDetails transactionDetail : transactionDetails) {
            if (transactionDetail.getTransactionType() == TransactionType.CREDIT) {
                balance = balance + transactionDetail.getAmount();
            } else {
                balance = balance - transactionDetail.getAmount();
            }
        }
        return balance;

    }
    private double getTransactionTypeBalnce(List<TransactionDetails> transactionDetails, TransactionType transactionType ) {
        double balance = 0;
        for(TransactionDetails transactionDetail : transactionDetails) {
            if (transactionDetail.getTransactionType() == transactionType ) {
                balance = balance + transactionDetail.getAmount();
            }
        }
        return balance;

    }


    //
    private Transaction mapDtotoEntity(TransactionDetails transactionDetails) throws BankException {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDetails.getAccountNumber());
        transaction.setAmount(transactionDetails.getAmount());
        transaction.setTransactionDate(transactionDetails.getTransactionDate());
        transaction.setDescription(transactionDetails.getDescription());
        transaction.setTransactionType(transactionDetails.getTransactionType().getValue());
        return transaction;
    }

    private List<TransactionDetails> getListOfTransactionsDto(List<Transaction> transactions) throws BankException {
        List<TransactionDetails> transactionDetails = new ArrayList<>();
        if(transactions !=null) {
            for (Transaction rec : transactions) {
                transactionDetails.add(getTransactionDto(rec));
            }
        }
        return transactionDetails;
    }

    private TransactionDetails getTransactionDto(Transaction transaction) throws BankException {
        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setAccountNumber(transaction.getAccountNumber());
        transactionDetails.setTransactionId(transaction.getTransactionId());
        transactionDetails.setAmount(transaction.getAmount());
        transactionDetails.setTransactionDate(transaction.getTransactionDate());
        transactionDetails.setDescription(transaction.getDescription());
        transactionDetails.setTransactionType(TransactionType.valueOf(transaction.getTransactionType()));
        return transactionDetails;

    }

    @Override
    public String callStarlingBankApi( String accountUid) {
        HttpHeaders headers = new HttpHeaders();
        String url
                = "https://api-sandbox.starlingbank.com/api/v2/accounts/"+ accountUid+"/balance";
        String token = "Bearer eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_31Sy5LbIBD8lZTOO1vWW9Ytt_xAPmCAkU0ZgQrQbrZS-fcggSzLceVGd8-rh_mdSeeyPsNJgqDRvDuPVkl9Yahv79yM2VvmZhYiGFVFIwRC2Z0IqqIT0LGihaEmVuXYVUy0IZh-TVmfN2VRFnXbdG-ZRB-JojyXC4Gcm1n7H0YJsj-lCLUbQaei7GpAYiVUQ91A1wgGvG7OTV3WHW8x1PbmRjpm5OdznbeDgNOJn6BqWQ1nKnLgQ5tXNa-64lyFjGDrO-fkXMxatK48t0BU5iELObCWNVC2ZT6wU53ntHhw3Ey0LCVOClwZR6K3hOLbxl3X8UHjSC8F_zU9CVKQ9nKQZI-8ks4fmASEsGHwnoT0dxAV75FfR7pH7vjTSk_fcPZXY6UL3whSC_khxYwqBjNUqHkajaMVwI321qjYaGGSZvQg7YheGg1mgGHWwt0ld---gdiaz86bcbNII8pUWFEYRF96nCb1dUdr1IhaoKdekKJQYoNJszfyi5HJ0kCWwuzuf1IcI2qTQk5hA54udvXxmPivmFLJ8itu7kbyGKbBnge4qgmvpib8ItqkCJKJCPYgkCNekqeo7U_wFrVDvk8YaGCzuvXbT9JO7d0i3htGvBVY7iHc1ij9XlMZHu7gocJKgFkO4plNWdYMUm3jRz8Hao2yxElO_gDcUYrLdfgRPszBxexzHLjk5sCtdR6ZuLDw5a9K7OKLWrsYi_IriVmRgLSwRJP3weA8JehxvVYwVjz0PLJbsyP7Ih_Mp77zntZv4u7jmZrEkKiZOW7DCpfr2Lo8cmvU4wmtv_R8U9mfv5URVpzrBQAA.Leck65qA39xTO6ukYOzrMSbSFNI7Sztp044QcqHaXmdgghYtGOpHjnhXeAyKWP7Hf7yU_K5Jz2xnAXNSm4vqz__2kQbB2_ICOk_DKnmMJV3qwJ98ahZBF7366HgWN60QITHAI6neq1Ug3TYnpQaMaONrZ4AswTCxIognEX3_BQz_q_1ghRpHsk83LN7jCTty0vv5RTyte7yEifhbzO4BPj1etu7uWE7zbOMgktGtNEWf_ZA5w0Z3tYVL6h7f_E-KgNc-jVENYltemOjG5gS3A_Gd-eVRStr59R0tan7-DfwvfEwxHWUZiVZxMjFO8ljDKwAAloBd0WXFZN2uCIDo7kYZlgoMcP9Zm1Uf_J840jLcjr8ade-x9L-H-pAnknBnIlxbUIYgL9wwd3df7KBa0m-CYzJOrYoywDjRzvr6MVvSnFvVOFnuIYvY7E3h8hFdTp5rOkoAJJC-tI5-ayB6a__LmaQQ2UfH6LYR5lvGAiA6qNJHJalKklu7suqQRvG4-QGAtlrA2n3E7kXxshe1aIguK8RIOT7R9d_CHajCyJNWih_WyAk7F2f-f7qVO6MzjZydWaGN4jWN9W0u_UrdNfggfqEl15qwaO5DUxFYREmY1rNgEqD8VOb-ZiA0GXEDPFImeK1khk_jHzj1SBdr9KQLmcRT4kLMyyXK9TCZXKQ";
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // create request
        HttpEntity request = new HttpEntity(headers);

        // make a request
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        // get JSON response
        return response.getBody();
    }
}
