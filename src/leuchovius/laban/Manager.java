package leuchovius.laban;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
          String service = promptChoice(vault.byService());
          String name = promptChoice(vault.namesForService(service));
          List<String> passwords = new ArrayList<>(vault.passesForServiceName(service, name));
          displayPasswords(passwords, service, name);
          break;
        case "quit":
          System.out.println("Quitting...");
          break;
        default:
          System.out.printf("Unknown command '%s'. Please try again. %n%n", choice);
          break;
      }
    } while (!choice.equals("quit"));
  }

  private void displayPasswords(List passwords, String service, String name) {
    System.out.printf("%nPasswords for service '%s' and username '%s': %n", service, name);
    displayList(passwords);
    System.console().readPassword("");  //Wait for user pressing enter
  }

  private void displayList(List list) {
    System.out.println();
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%d.)  %s %n", i + 1, list.get(i));
    }
  }

  private String promptChoice(Set<String> options) {
    List<String> optionList = new ArrayList<>(options);
    int index = 0;
    boolean isValidIndex = false;
    do {
      displayList(optionList);
      try {
        index = promptIndex(optionList);
        isValidIndex = true;
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number. Try again.");
      } catch (IndexOutOfBoundsException iobe) {
        System.out.println(iobe.getMessage() + ". Please try again.");
      }
    } while (!isValidIndex);
    return optionList.get(index);
  }

  private int promptIndex(List list) throws NumberFormatException, IndexOutOfBoundsException {
    System.out.print("Enter your choice: ");
    int indexInput = Integer.parseInt(scanner.nextLine());
    if (indexInput < 1 || indexInput > list.size()) {
      throw new IndexOutOfBoundsException("There is no option corresponding to the index '" + indexInput + "', it is either too large or too small");
    }
    return indexInput - 1;
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