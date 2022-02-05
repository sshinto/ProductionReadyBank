package com.hb.bank;

import com.hb.bank.service.customer.CustomerServiceImpl;

public class FactoryService {

  public Object getBean(String beanType){
    if(beanType.equals("AccountService")) return new AccountServiceProxy();
    if(beanType.equals("CustomerService")) return new CustomerServiceImpl();
    return null;
   }
}
