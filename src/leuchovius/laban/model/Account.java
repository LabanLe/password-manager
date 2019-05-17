package leuchovius.laban.model;

import java.util.Objects;

public class Account {
  private String service;
  private String name;
  private String password;

  public Account(String service, String name, String password) throws IllegalArgumentException {
    this.service = normalizeInput(service);
    this.name = normalizeInput(name);
    this.password = validatePassword(password);
  }

  private final String validatePassword(String password) throws IllegalArgumentException {
    if (password.length() < 1) {
      throw new IllegalArgumentException("Password can not be shorter than 1 character");
    }
    return password;
  }

  private final String normalizeInput(String input) throws IllegalArgumentException {
    input = input.trim();
    if (input.length() > 20) {
      throw new IllegalArgumentException("Input can not be longer than 20 characters");
    } else if (input.isEmpty()) {
      input = "[not specified]";
    }
    return input;
  }

  //TODO: See if you actually need setters. I'll keep them for now
  public void setService(String service) { this.service = service; }
  public void setName(String name) { this.name = name; }
  public void setPassword(String password) { this.password = password; }

  public String getService() { return service; }
  public String getName() { return name; }
  public String getPassword() { return password; }

  @Override
  public String toString() {
    return String.format("Account: Username %s on service %s using password %s", name, service, password);
  }

  @Override
  public final int hashCode() {
    return Objects.hash(service, name, password);
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (o == null) {
      return false;
    } else if (!(o instanceof Account)) {
      return false;
    }
    Account other = (Account) o;
    return service.equals(other.getService())
        && name.equals(other.getName())
        && password.equals(other.getPassword());
  }
}