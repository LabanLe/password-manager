package leuchovius.laban.model;

public class Account {
  private String service;
  private String name;
  private String password;

  public Account(String service, String name, String password) {
    if (service.trim().isEmpty()) {
      service =  "not specified";
    }
    if (name.trim().isEmpty()) {
      name = "not specified";
    }
    this.service = service;
    this.name = name;
    this.password = password;
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