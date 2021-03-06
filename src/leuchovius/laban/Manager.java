package leuchovius.laban;

import leuchovius.laban.model.Account;
import leuchovius.laban.model.Vault;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
  private Map<String, String> menu;
  private Scanner scanner;
  private Vault vault;
  private boolean isBrowseEnabled = true;

  public Manager(Vault vault) {
    this.vault = vault;
    menu = new HashMap<>();
    scanner = new Scanner(System.in);
    menu.put("add", "Add a password to your vault");
    menu.put("remove OR rm", "Remove a password from your vault");
    menu.put("view", "View passwords and account details added to your vault");
    menu.put("edit", "Browse accounts and edit the details of the chosen account");
    menu.put("toggle", "Toggles between browsing passwords by service and name and just viewing the passwords in a list");
    menu.put("quit OR exit", "Exit Password Manager");
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
          System.out.printf("%nAccount information related to your password: "
              + "%nTip! If you don't want a certain piece of info to accompany your password, "
              + "skip it by just pressing enter. %n");
          promptAddAccount(promptNewAccount());
          break;
        case "remove": case "rm":
          try {
            promptRemoveAccount(choose());
          } catch (IllegalArgumentException iae) {
            System.out.printf("%s. Please add a password to your vault. %n%n", iae.getMessage());
          }
          break;
        case "view":
          try {
            view();
          } catch (IllegalArgumentException iae) {
            System.out.printf("%s. Please add a password to your vault. %n%n", iae.getMessage());
          }
          break;
        case "edit":
          try {
            promptEditAccount(choose());
          } catch (IllegalArgumentException iae) {
            System.out.printf("%s. Please add a password to your vault. %n%n", iae.getMessage());
          }
          break;
        case "toggle":
          boolean isEnabled = toggleBrowse();
          if (isEnabled) {
            System.out.println("Browsing enabled. You can now browse passwords and account details by name and service.");
          } else {
            System.out.println("Browsing disabled. You can now view passwords and account details in one list.");
          }
          break;
        case "quit":
        case "exit":
          System.out.println("Quitting...");
          break;
        default:
          System.out.printf("Unknown command '%s'. Please try again. %n%n", choice);
          break;
      }
    } while (!choice.equals("quit") && !choice.equals("exit"));
  }

  private boolean toggleBrowse() {
    if (isBrowseEnabled) {
      isBrowseEnabled = false;
    } else {
      isBrowseEnabled = true;
    }
    return isBrowseEnabled;
  }

  private void promptRemoveAccount(Account account) {
    boolean tryAgain = true;
    do {
      System.out.printf("This password with its associated account details will be permanently deleted from your vault: %n%n%s %nAre you sure? [y/n]   ", account);
      switch (scanner.nextLine().trim()) {
        case "yes":
        case "y":
          vault.removeAccount(account);
          System.out.println("Account was deleted.");
          tryAgain = false;
          break;
        case "no":
        case "n":
          System.out.println("Account was not deleted.");
          tryAgain = false;
          break;
        default:
          System.out.println("Please input either 'yes' or 'no'. Try again.");
          break;
      }
    } while (tryAgain);
  }

  private void promptEditAccount(Account account) {
    System.out.printf("Edit account: %n%n%s %n%n", account);
    Account editedAccount = promptNewAccount();
    boolean tryAgain = true;
    if (!vault.contains(editedAccount)) {
      do {
        System.out.printf("Original account: %s %n%nEdited account: %s %n%nDo you want to save your changes? [y/n]   ", account, editedAccount);
        switch (scanner.nextLine().trim()) {
          case "yes":
          case "y":
            vault.removeAccount(account);
            vault.addAccount(editedAccount);
            System.out.println("Your changes were saved.");
            tryAgain = false;
            break;
          case "no":
          case "n":
            System.out.println("Your changes were not saved.");
            tryAgain = false;
            break;
          default:
            System.out.println("Please input either 'yes' or 'no'. Try again.");
            break;
        }
      } while (tryAgain);
    } else {
      System.out.printf("%n%nAn account with the same details already exists: %n%s %n%nChanges were therefore not saved.", editedAccount);
    }
  }

  private Account choose() throws IllegalArgumentException {
    if (isBrowseEnabled) {
      String service = promptChoice(vault.byService());
      String name = promptChoice(vault.namesForService(service));
      String password = promptChoice(vault.passesForServiceName(service, name));
      return vault.getAccount(service, name, password);
    } else {
      return vault.getAccount(promptChoice(vault.getAll()));
    }
  }

  //TODO: Make browse and choose shorter and use the same base method instead of two different but similar methods
  private void view() throws IllegalArgumentException {
    if (isBrowseEnabled) {
      String service = promptChoice(vault.byService());
      String name = promptChoice(vault.namesForService(service));
      List<String> passwords = new ArrayList<>(vault.passesForServiceName(service, name));
      displayPasswords(passwords, service, name);
    } else {
      displayList(new ArrayList(vault.getAll()));
      System.console().readPassword("");  //Wait for user pressing enter
    }
  }

  private void displayPasswords(List passwords, String service, String name) throws IllegalArgumentException {
    System.out.printf("%nPasswords for service '%s' and username '%s': %n%n", service, name);
    displayList(passwords);
    System.console().readPassword("");  //Wait for user pressing enter
  }

  private void displayList(List list) throws IllegalArgumentException {
    if (list.isEmpty()) {
      throw new IllegalArgumentException("Nothing provided to display");
    }
    for (int i = 0; i < list.size(); i++) {
      System.out.printf("%d.)  %s %n", i + 1, list.get(i));
    }
  }

  private String promptChoice(Set options) throws IllegalArgumentException {
    return promptChoice(new ArrayList<>(options));
  }

  private String promptChoice(List options) throws IllegalArgumentException {
    int index = 0;
    if (options.isEmpty()) {
      throw new IllegalArgumentException("No options were provided to choose from");
    }
    boolean isValidIndex = false;
    do {
      displayList(options);
      try {
        index = promptIndex(options);
        isValidIndex = true;
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number. Try again.");
      } catch (IndexOutOfBoundsException iobe) {
        System.out.println(iobe.getMessage() + ". Please try again.");
      }
    } while (!isValidIndex);
    return options.get(index).toString();
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
    if (!vault.contains(account)) {
      do {
        System.out.printf("This account will be added to your vault: %n%n%s %n%nAre you sure? [y/n]   ", account);
        String input = scanner.nextLine().trim();
        switch (input) {
          case "yes":
          case "y":
            vault.addAccount(account);
            System.out.println("Account added.");
            tryAgain = false;
            break;
          case "no":
          case "n":
            System.out.println("Account was not added.");
            tryAgain = false;
            break;
          default:
            System.out.println("Please input either 'yes' or 'no'. Try again.");
            break;
        }
      } while (tryAgain);
    } else {
      System.out.printf("%n%nThis account already exists in your vault: %n%n%s %n%nAccount was not added.", account);
    }
  }

  private Account promptNewAccount() {
    Account account = null;
    Boolean isValid = false;
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