package functional;

import data.DataStore;
import model.Member;

public class MemberService
{
    public static Member registerMember(String name, String email, String phone, String plan)
    {
        if (findByEmail(email) != null)
        {
            return null;
        }

        int nextId = 1001;
        for (Member m : DataStore.members)
        {
            if (m.id >= nextId)
            {
                nextId = m.id + 1;
            }
        }

        Member member = new Member(nextId, name, email, phone, plan);
        DataStore.members.add(member);
        DataStore.save();
        return member;
    }

    public static Member findByEmail(String email)
    {
        for (Member m : DataStore.members)
        {
            if (m.email.equalsIgnoreCase(email))
            {
                return m;
            }
        }
        return null;
    }

    public static Member findByName(String name)
    {
        for (Member m : DataStore.members)
        {
            if (m.name.equalsIgnoreCase(name))
            {
                return m;
            }
        }
        return null;
    }

    public static int memberCount()
    {
        return DataStore.members.size();
    }
}
