package edu.ulatina.services;

import edu.ulatina.objects.UserTO;
import java.sql.PreparedStatement;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(UserTO objectTO) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserTO> select() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
