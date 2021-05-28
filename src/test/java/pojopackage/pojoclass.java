package pojopackage;


public class pojoclass
{
    private String stream;  //every field has 2 methods 1. Set 2. Get

    private String firstname;

    private String lastname;

    public void setStream(String stream){
        this.stream = stream;
    }
    public String getStream(){
        return this.stream;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public String getLastname(){
        return this.lastname;
    }
}
