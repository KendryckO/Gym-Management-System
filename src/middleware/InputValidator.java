package middleware;

public class InputValidator
{
    public static boolean notEmpty(String s)
    {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidEmail(String email)
    {
        if (email == null)
        {
            return false;
        }
        return email.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.-]+$");
    }

    public static boolean isValidPhone(String phone)
    {
        if (phone == null)
        {
            return false;
        }
        return phone.matches("^\\d{7,15}$");
    }

    public static boolean isValidAmount(String amount)
    {
        try
        {
            return Double.parseDouble(amount) > 0;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
