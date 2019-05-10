package leuchovius.laban.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Vault {
  private List<Account> accounts = new ArrayList<>();

  //TODO: Make the constructor
  public Vault() {

  }

  public void addAccount(Account account) {
    accounts.add(account);
  }

  public Set<String> byService() {
    Set<String> accountsByService = new HashSet<>();
    for (Account account : accounts) {
      accountsByService.add(account.getService());
    }
    return accountsByService;
  }

  public void print() {
    for (Account account : accounts) {
      System.out.println(account);
    }
  }

}