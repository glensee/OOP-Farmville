package entity.cityfarmer;

public class Gift {
    private String username;
    private String friendUsername;
    private String giftItem;
    private String timestamp;


    public Gift(String username, String friendUsername, String giftItem, String timestamp){
        this.username = username;
        this.friendUsername = friendUsername;
        this.giftItem = giftItem;
        this.timestamp = timestamp;
    }

    public String getUsername(){
        return this.username;
    }

    public String getFriendUsername(){
        return this.friendUsername;
    }

    public String getGiftItem(){
        return this.giftItem;
    }

    public String getTimestamp(){
        return this.timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Gift) {
            Gift gift = (Gift) obj;

            if (!(this.username.equals(gift.getUsername()) ||
            this.friendUsername.equals(gift.getFriendUsername()) ||
            this.giftItem == gift.getGiftItem())) {
                return false;
            }


        } else {
            return false;
        }

        return true;
    }
}



