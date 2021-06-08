package software_projekt;



/**
 *
 * @author beyzaesra
 */
public class ModelTable {
    
    int id;
    String  username, e_mail, bauteil_id;

    public ModelTable(int id, String username, String e_mail, String bauteil_id) {
        this.id = id;
        this.username = username;
        this.e_mail = e_mail;
        this.bauteil_id = bauteil_id;
    }

    

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public void setBauteil_id(String bauteil_id) {
        this.bauteil_id = bauteil_id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getBauteil_id() {
        return bauteil_id;
    }


    


    

}