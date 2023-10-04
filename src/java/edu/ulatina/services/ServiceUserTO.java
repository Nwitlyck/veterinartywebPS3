package edu.ulatina.services;

import edu.ulatina.objects.UserTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nwitlyck
 */
public class ServiceUserTO extends Service implements ICrud<UserTO>{

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
    public List<UserTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UserTO> objectTOList = new ArrayList<UserTO>();

        ps = getConnection().prepareStatement("SELECT Id, Email, Password, Name, Lastname, Role, State FROM Users");
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
    
}
