package edu.ulatina.services;

import edu.ulatina.objects.UserTO;
import edu.ulatina.security.AESEncryptionDecryption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceUserTO extends Service implements ICrud<UserTO> {

    @Override
    public void insert(UserTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Users (Email, Password, Name, Lastname, Role, State) VALUES (?, ?, ?, ?, ?, 1)");
        ps.setString(1, objectTO.getEmail());
        ps.setString(2, objectTO.getPassword());
        ps.setString(3, objectTO.getName());
        ps.setString(4, objectTO.getLastname());
        ps.setInt(5, objectTO.getRole());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(UserTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Users SET Email = ?, Password = ?, Name = ?, Lastname = ?, Role = ?, State = ? WHERE Id = ?");
        ps.setString(1, objectTO.getEmail());
        ps.setString(2, objectTO.getPassword());
        ps.setString(3, objectTO.getName());
        ps.setString(4, objectTO.getLastname());
        ps.setInt(5, objectTO.getRole());
        ps.setInt(6, objectTO.getState());
        ps.setInt(7, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(UserTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Users SET State = 0 WHERE Id = ?");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void enable(UserTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Users SET State = 1 WHERE Id = ?");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<UserTO> select(int enable) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UserTO> objectTOList = new ArrayList<UserTO>();

        ps = getConnection().prepareStatement("SELECT Id, Email, Password, Name, Lastname, Role, State FROM Users Where State = ?");
        ps.setInt(1, enable);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Id");
            String email = rs.getString("Email");
            String password = rs.getString("Password");
            String name = rs.getString("Name");
            String lastname = rs.getString("Lastname");
            int role = rs.getInt("Role");
            int state = rs.getInt("State");

            objectTOList.add(new UserTO(id, email, password, name, lastname, role, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    public List<UserTO> selectByRole(int enableI, int roleI) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UserTO> objectTOList = new ArrayList<UserTO>();

        ps = getConnection().prepareStatement("SELECT Id, Email, Password, Name, Lastname, Role, State FROM Users Where State = ? AND Role = ?");
        ps.setInt(1, enableI);
        ps.setInt(2, roleI);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Id");
            String email = rs.getString("Email");
            String password = rs.getString("Password");
            String name = rs.getString("Name");
            String lastname = rs.getString("Lastname");
            int role = rs.getInt("Role");
            int state = rs.getInt("State");

            objectTOList.add(new UserTO(id, email, password, name, lastname, role, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    public boolean selectByEmailAndPassword(String email, String password) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = getConnection().prepareStatement("select count(*) from Users Where Email = ? AND Password = ? AND Role = 1");
        ps.setString(1, email);
        ps.setString(2, new AESEncryptionDecryption().encrypt(password));
        rs = ps.executeQuery();

        if (rs.next()) {
            if (rs.getInt(1) == 1) {
                return true;
            } else {
                return false;
            }
        }

        close(rs);
        close(ps);
        close(conn);

        return false;
    }
}
