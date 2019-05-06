import leuchovius.laban.Manager;
import leuchovius.laban.model.Vault;

public class Main {

  public static void main(String[] args) {
    Vault vault = new Vault();
    Manager manager = new Manager(vault);

    manager.run();
    //For debugging purposes only
    vault.print();
  }


}