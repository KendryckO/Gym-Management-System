package data;

import model.Member;
import model.Payment;
import java.io.*;
import java.util.ArrayList;

public class DataStore
{
    public static String folder = "gymdata";
    public static ArrayList<Member> members = new ArrayList<>();
    public static ArrayList<Payment> payments = new ArrayList<>();

    public static void load()
    {
        members.clear();
        payments.clear();
        try
        {
            File m = new File(folder, "members.txt");
            if (m.exists())
            {
                BufferedReader r = new BufferedReader(new FileReader(m));
                String line;
                while ((line = r.readLine()) != null)
                {
                    String[] p = line.split("\\|");
                    if (p.length == 5)
                    {
                        members.add(new Member(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4]));
                    }
                }
                r.close();
            }

            File f = new File(folder, "payments.txt");
            if (f.exists())
            {
                BufferedReader r = new BufferedReader(new FileReader(f));
                String line;
                while ((line = r.readLine()) != null)
                {
                    String[] p = line.split("\\|");
                    if (p.length == 4)
                    {
                        payments.add(new Payment(p[0], p[1], p[2], Double.parseDouble(p[3])));
                    }
                }
                r.close();
            }
        }
        catch (Exception e)
        {
            System.out.println("load error: " + e.getMessage());
        }
    }

    public static void save()
    {
        try
        {
            new File(folder).mkdirs();

            PrintWriter w = new PrintWriter(new FileWriter(new File(folder, "members.txt")));
            for (Member m : members)
            {
                w.println(m.id + "|" + m.name + "|" + m.email + "|" + m.phone + "|" + m.plan);
            }
            w.close();

            PrintWriter v = new PrintWriter(new FileWriter(new File(folder, "payments.txt")));
            for (Payment p : payments)
            {
                v.println(p.id + "|" + p.memberName + "|" + p.plan + "|" + p.amount);
            }
            v.close();
        }
        catch (Exception e)
        {
            System.out.println("save error: " + e.getMessage());
        }
    }

    public static void clear()
    {
        members.clear();
        payments.clear();
    }
}
