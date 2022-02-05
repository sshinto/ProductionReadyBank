package com.hb.bank;

import com.hb.bank.service.account.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountServiceProxy extends AccountServiceImpl {

  public String getAccountName() {
    log.info("========START===============");
    String accountName = super.getAccountName();
    log.info(accountName);
    log.info("========END===============");
    return accountName;
  }

}
