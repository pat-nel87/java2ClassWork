/*
Patrick Nelson 2021
Java Multi-threaded Chat Server w/ GUI
*/
import java.io.Serializable;
import java.util.ArrayList;

public class UserSessionManager implements Serializable {
    /* A serializable class designed
    for sending to the client program
    */
    private ArrayList<String> usersList;

    UserSessionManager(ArrayList<String> usersList) {
        setUsersList(usersList);
    }

    public void setUsersList(ArrayList<String> usersList) { this.usersList = usersList; }
    public ArrayList getUsersList() { return this.usersList; }

}
