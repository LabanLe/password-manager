package leuchovius.laban;

import java.util.ArrayList;
import java.util.List;
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
    menu.put("browse", "Browse passwords and account details by service and username");
    menu.put("quit", "Exit Password Manager");
  }

  //TODO: Finish run method
  public void run() {
    String choice;
    do {
      System.out.printf("%n%n%n");
      for (Map.Entry entry : menu.entrySet()) {
        System.out.printf("%s - %s %n", entry.getKey(), entry.getValue());
      }
      System.out.printf("%nPlease enter your choice: ");
      choice = scanner.nextLine();
      switch (choice) {
        case "add":
          promptAddAccount(promptNewAccount());
          break;
        case "browse":
          //TODO: Add browse option
          displayService();
          break;
        case "quit":
          System.out.println("Quitting...");
          break;
        default:
          System.out.printf("Unknown command '%s'. Please try again. %n%n");
          break;
      }
    } while (!choice.equals("quit"));
  }

  private void displayService() {
    List<String> byService = new ArrayList<>(vault.byService());
    for (int i = 0; i < byService.size(); i++) {
      System.out.printf("%d.)  %s %n", i + 1, byService.get(i));
    }
  }

  private void promptAddAccount(Account account) {
    boolean tryAgain = true;
    do {
      System.out.printf("This account will be added to your vault: %n%n%s %n%nAre you sure? [y/n]   ", account);
      String input = scanner.nextLine().trim();
      switch (input) {
        case "yes": case "y":
          vault.addAccount(account);
          System.out.println("Account added.");
          tryAgain = false;
          break;
        case "no": case "n":
          System.out.println("Account was not added.");
          tryAgain = false;
          break;
        default:
          System.out.println("Please input either 'yes' or 'no'. Try again.");
          break;
      }
    } while (tryAgain);
  }

  //TODO: Finish promptNewAccount method
  private Account promptNewAccount() {
    Account account = null;
    Boolean isValid = false;
    System.out.printf("%nAccount information related to your password: "
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