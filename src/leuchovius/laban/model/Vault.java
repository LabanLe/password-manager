package leuchovius.laban.model;

import java.util.List;
import java.util.ArrayList;

public class Vault {
  private List<Account> accounts = new ArrayList<>();

  //TODO: Make the constructor
  public Vault() {

  }

  public void addAccount(Account account) {
    accounts.add(account);
  }

  public void print() {
    for (Account account : accounts) {
      System.out.println(account);
    }
  }

}