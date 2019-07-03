package za.co.yakka.customException;

public class CannotAddUser extends RuntimeException {

    public CannotAddUser(String message){
        super(message);
    }

}
