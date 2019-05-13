package leuchovius.laban.model;

import java.util.HashSet;
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

  public Set<String> namesForService(String service) {
    Set<String> names = new HashSet<>();
    for (Account account : accounts) {
      if (account.getService().equals(service)) {
        names.add(account.getName());
      }
    }
    return names;
  }

  public Set<String> passesForServiceName(String service, String name) {
    Set<String> passwords = new HashSet<>();
    for (Account account : accounts) {
      if (account.getService().equals(service) && account.getName().equals(name)) {
        passwords.add(account.getPassword());
      }
    }
    return passwords;
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

  public boolean contains(Account account) {
    return accounts.contains(account);
  }

}