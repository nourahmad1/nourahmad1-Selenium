package Checkout;

public class Config {
    private static String userType = "login"; 

    public static String getUserType() {
        return userType;
    }

    public static void setUserType(String type) {
        if (type != null && !type.isEmpty()) {
            userType = type;
        }
    }
}
