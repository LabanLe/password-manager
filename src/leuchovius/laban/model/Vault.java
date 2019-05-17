package leuchovius.laban.model;

import java.util.HashSet;
import java.util.Set;

public class Vault {
  private Set<Account> accounts = new HashSet<>();

  //TODO: Make the constructor
  public Vault() {

  }

  public void addAccount(Account account) {
    accounts.add(account);
  }
  public boolean removeAccount(Account account) { return accounts.remove(account); }
  public Set<Account> getAll() { return accounts; }

  /*
  Gets an account from the vault which fields are equal to the method arguments,
  if no accounts are found it returns 'null'
  */
  public Account getAccount(String service, String name, String password) {
    for (Account account : accounts) {
      if (account.getService().equals(service)
          && account.getName().equals(name)
          && account.getPassword().equals(password)) {
        return account;
      }
    }
    return null;
  }

  public Account getAccount(String accountDesc) {
    for (Account account : accounts) {
      if (account.toString().equals(accountDesc)) {
        return account;
      }
    }
    return null;
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