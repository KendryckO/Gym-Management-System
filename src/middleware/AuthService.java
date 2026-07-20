package middleware;

import java.util.HashMap;

public class AuthService
{
    private static HashMap<String, String> users = new HashMap<>();
    private static String currentUser = null;

    static
    {
        users.put("admin", "admin123");
        users.put("staff", "staff123");
    }

    public static boolean login(String username, String password)
    {
        if (username == null || password == null)
        {
            return false;
        }
        String stored = users.get(username);
        if (stored != null && stored.equals(password))
        {
            currentUser = username;
            return true;
        }
        return false;
    }

    public static void logout()
    {
        currentUser = null;
    }

    public static String getCurrentUser()
    {
        return currentUser;
    }

    public static boolean isLoggedIn()
    {
        return currentUser != null;
    }
}
