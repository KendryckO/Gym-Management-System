package test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import data.DataStore;
import ui.LoginScreen;

// automated walk-through of the full user journey:
// login -> dashboard -> add member -> plan -> billing -> receipt -> dashboard -> logout
// clicks the real buttons, checks each screen opens, and saves screenshots (needs a display)
public class UiJourneyTest
{
    static int passed = 0;
    static int failed = 0;
    static Robot robot;
    static File shotDir = new File("screenshots");

    public static void main(String[] args) throws Exception
    {
        shotDir.mkdirs();
        robot = new Robot();
        DataStore.folder = "testdata";
        DataStore.clear();
        new File("testdata/members.txt").delete();
        new File("testdata/payments.txt").delete();

        SwingUtilities.invokeAndWait(() -> LoginScreen.main(new String[0]));
        pause(600);
        JFrame login = frameByTitle("gym login");
        check("UI-01 login screen opens on start", login != null);

        // empty fields first
        clickAndCloseDialog(buttonByText(login, "login"), "dlg-login-empty");
        check("UI-02 empty login fields are blocked",
                frameByTitle("gym login") != null && frameByTitle("gym dashboard") == null);

        // wrong password
        login = frameByTitle("gym login");
        setText(login, 0, "admin");
        setPassword(login, "badpass");
        clickAndCloseDialog(buttonByText(login, "login"), "dlg-invalid-login");
        check("UI-03 wrong password shows error and stays on login",
                frameByTitle("gym login") != null && frameByTitle("gym dashboard") == null);

        // valid login
        login = frameByTitle("gym login");
        setText(login, 0, "admin");
        setPassword(login, "admin123");
        shot(login, "01-login");
        click(buttonByText(login, "login"));
        pause(600);
        JFrame dash = frameByTitle("gym dashboard");
        check("UI-04 valid login opens dashboard", dash != null);
        shot(dash, "02-dashboard");

        click(buttonByText(dash, "add member"));
        pause(600);
        JFrame reg = frameByTitle("member registration");
        check("UI-05 add member opens registration screen", reg != null);

        // empty submit blocked
        clickAndCloseDialog(buttonByText(reg, "continue"), "dlg-empty-fields");
        check("UI-06 empty registration is blocked", frameByTitle("member registration") != null);

        // invalid email blocked
        reg = frameByTitle("member registration");
        setText(reg, 0, "ken bautista");
        setText(reg, 1, "kenmail.com");
        setText(reg, 2, "0123456789");
        clickAndCloseDialog(buttonByText(reg, "continue"), "dlg-bad-email");
        check("UI-07 invalid email is blocked", frameByTitle("member registration") != null);

        // invalid phone blocked
        reg = frameByTitle("member registration");
        setText(reg, 1, "ken@mail.com");
        setText(reg, 2, "12ab56");
        clickAndCloseDialog(buttonByText(reg, "continue"), "dlg-bad-phone");
        check("UI-08 invalid phone is blocked", frameByTitle("member registration") != null);

        // valid details
        reg = frameByTitle("member registration");
        setText(reg, 2, "0123456789");
        shot(reg, "03-registration");
        click(buttonByText(reg, "continue"));
        pause(600);
        JFrame plan = frameByTitle("membership plan");
        check("UI-09 valid details open membership plan screen", plan != null);
        setCombo(plan, 1); // standard - $30
        shot(plan, "04-plan");

        clickAndCloseDialog(buttonByText(plan, "confirm"), "dlg-registered");
        pause(600);
        JFrame bill = frameByTitle("billing");
        check("UI-10 confirming plan opens billing prefilled",
                bill != null && textAt(bill, 0).equals("ken bautista") && textAt(bill, 1).equals("30.0"));
        shot(bill, "05-billing");

        clickAndCloseDialog(buttonByText(bill, "pay"), "dlg-receipt");
        pause(600);
        dash = frameByTitle("gym dashboard");
        check("UI-11 payment returns to dashboard (journey complete)", dash != null);

        clickAndCloseDialog(buttonByText(dash, "reports"), "dlg-reports");
        dash = frameByTitle("gym dashboard");
        click(buttonByText(dash, "logout"));
        pause(600);
        check("UI-12 logout returns to login screen", frameByTitle("gym login") != null);

        System.out.println("----------------------------------------");
        System.out.println("total: " + (passed + failed) + "  passed: " + passed + "  failed: " + failed);
        System.exit(failed > 0 ? 1 : 0);
    }

    // ---------- helpers ----------

    static void check(String name, boolean ok)
    {
        if (ok) { passed++; System.out.println("PASS  " + name); }
        else    { failed++; System.out.println("FAIL  " + name); }
    }

    static void pause(int ms)
    {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    static JFrame frameByTitle(String title)
    {
        for (Frame f : Frame.getFrames())
        {
            if (f.isShowing() && title.equals(f.getTitle()))
            {
                return (JFrame) f;
            }
        }
        return null;
    }

    static void collect(Container c, Class<?> cls, ArrayList<Component> out)
    {
        for (Component comp : c.getComponents())
        {
            if (cls.isInstance(comp)) out.add(comp);
            if (comp instanceof Container) collect((Container) comp, cls, out);
        }
    }

    static JButton buttonByText(Container c, String text)
    {
        ArrayList<Component> list = new ArrayList<>();
        collect(c, JButton.class, list);
        for (Component comp : list)
        {
            if (((JButton) comp).getText().equals(text)) return (JButton) comp;
        }
        return null;
    }

    static void setText(Container c, int index, String value)
    {
        ArrayList<Component> list = new ArrayList<>();
        collect(c, JTextField.class, list);
        int i = 0;
        for (Component comp : list)
        {
            if (comp instanceof JPasswordField) continue;
            if (i == index) { ((JTextField) comp).setText(value); return; }
            i++;
        }
    }

    static String textAt(Container c, int index)
    {
        ArrayList<Component> list = new ArrayList<>();
        collect(c, JTextField.class, list);
        int i = 0;
        for (Component comp : list)
        {
            if (comp instanceof JPasswordField) continue;
            if (i == index) return ((JTextField) comp).getText();
            i++;
        }
        return "";
    }

    static void setPassword(Container c, String value)
    {
        ArrayList<Component> list = new ArrayList<>();
        collect(c, JPasswordField.class, list);
        if (!list.isEmpty()) ((JPasswordField) list.get(0)).setText(value);
    }

    static void setCombo(Container c, int index)
    {
        ArrayList<Component> list = new ArrayList<>();
        collect(c, JComboBox.class, list);
        if (!list.isEmpty())
        {
            JComboBox<?> box = (JComboBox<?>) list.get(0);
            try { SwingUtilities.invokeAndWait(() -> box.setSelectedIndex(index)); } catch (Exception ignored) {}
        }
    }

    static void click(JButton b)
    {
        try { SwingUtilities.invokeAndWait(b::doClick); } catch (Exception ignored) {}
    }

    // clicks a button that pops a modal dialog, screenshots the dialog, then closes it
    static void clickAndCloseDialog(JButton b, String shotName)
    {
        Thread closer = new Thread(() ->
        {
            long end = System.currentTimeMillis() + 4000;
            while (System.currentTimeMillis() < end)
            {
                for (Window w : Window.getWindows())
                {
                    if (w instanceof JDialog && w.isShowing())
                    {
                        pause(350);
                        shot(w, shotName);
                        SwingUtilities.invokeLater(w::dispose);
                        return;
                    }
                }
                pause(80);
            }
        });
        closer.start();
        click(b);
        try { closer.join(); } catch (Exception ignored) {}
        pause(300);
    }

    static void shot(Window w, String name)
    {
        try
        {
            Toolkit.getDefaultToolkit().sync();
            pause(250);
            Rectangle r = w.getBounds();
            BufferedImage img = robot.createScreenCapture(r);
            ImageIO.write(img, "png", new File(shotDir, name + ".png"));
        }
        catch (Exception e)
        {
            System.out.println("screenshot failed for " + name + ": " + e.getMessage());
        }
    }
}
