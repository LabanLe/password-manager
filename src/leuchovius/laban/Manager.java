package leuchovius.laban;

import leuchovius.laban.model.Account;
import leuchovius.laban.model.Vault;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
  private Map<String, String> menu;
  private Scanner scanner;
  private Vault vault;

  public Manager(Vault vault) {
    this.vault = vault;
    menu = new HashMap<>();
    scanner = new Scanner(System.in);
    menu.put("add", "Add a password to your vault");
    menu.put("quit", "Exit Password Manager");
  }

  //TODO: Finish run method
  public void run() {
    String choice;
    do {
      for (Map.Entry entry : menu.entrySet()) {
        System.out.printf("%s - %s %n", entry.getKey(), entry.getValue());
      }
      System.out.print("Please enter your choice: ");
      choice = scanner.nextLine();
      switch (choice) {
        case "add":
          vault.addAccount(promptNewAccount());
          break;
        case "quit":
          System.out.println("Quitting...");
          break;
      }
    } while (!choice.equals("quit"));
  }

  //TODO: Finish promptNewAccount method
  private Account promptNewAccount() {
    Account account = null;
    Boolean isValid = false;
    System.out.printf("Account information related to your password: "
        + "%nTip! If you don't want a certain piece of info to accompany your password, "
        + "skip it by just pressing enter. %n");
    do {
      System.out.print("Service (where you use your account): ");
      String service = scanner.nextLine();
      System.out.print("Account name: ");
      String name = scanner.nextLine();
      System.out.print("Password: ");
      String password = scanner.nextLine();
      try {
        account = new Account(service, name, password);
        isValid = true;
      } catch (IllegalArgumentException iae) {
        System.out.printf("%s. Please try again. %n", iae.getMessage());
      }
    } while (!isValid);
    return account;
  }
}