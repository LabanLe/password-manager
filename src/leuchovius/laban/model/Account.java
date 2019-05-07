package leuchovius.laban.model;

public class Account {
  private String service;
  private String name;
  private String password;

  public Account(String service, String name, String password) throws IllegalArgumentException {
    this.service = normalizeInput(service);
    this.name = normalizeInput(name);
    this.password = password;
  }

  private String normalizeInput(String input) throws IllegalArgumentException {
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
}