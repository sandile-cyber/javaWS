package za.co.yakka.customException;

public class CannotUpdateUser extends RuntimeException {

    public CannotUpdateUser(String message){

        super(message);

    }

}

