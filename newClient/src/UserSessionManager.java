import java.io.Serializable;
import java.util.ArrayList;

public class UserSessionManager implements Serializable {

    private ArrayList<ClientManager> usersList;

    UserSessionManager(ArrayList<ClientManager> usersList) {
        setUsersList(usersList);
    }

    public void setUsersList(ArrayList<ClientManager> usersList) { this.usersList = usersList; }
    public ArrayList getUsersList() { return this.usersList; }

}
