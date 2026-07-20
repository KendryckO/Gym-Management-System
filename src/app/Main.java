package app;

import data.DataStore;
import ui.LoginScreen;

public class Main
{
    public static void main(String[] args)
    {
        DataStore.load();
        LoginScreen.main(args);
    }
}
