package entity.requests;

/**
 * Requests Entity Class
 */
public class Requests {
    private String username;
    private String otherParty;

    /**
     * General Constructor for Requests
     * @param username username of the Request Receiver
     * @param otherParty username of the Request Sender
     */
    public Requests(String username, String otherParty){
        this.username = username;
        this.otherParty = otherParty;
    }

    public String getUsername(){
        return this.username;
    }

    public String getOtherParty(){
        return this.otherParty;
    }

    /**
     * Overrides equals(Object obj) method from {@link Object} class
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = true;

        if (obj instanceof Requests) {
            Requests request = (Requests) obj;

            if (!(this.username.equals(request.getUsername()) || this.otherParty.equals(request.getOtherParty()))) {
                flag = false;
            } 
        } else {
            flag = false;
        }

        return flag;
    }

    @Override
    public String toString() {
        return this.username + " " + this.otherParty;
    }
}