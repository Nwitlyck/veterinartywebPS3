package edu.ulatina.services;

import edu.ulatina.objects.CustomersTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dylan
 */
public class ServiceCustomerTO extends Service implements ICrud<CustomersTO> {

    @Override
    public void insert(CustomersTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Customers (Cedula, Email, Name, Lastname, State) VALUES (?, ?, ?, ?, 1)");
        ps.setInt(1, objectTO.getCedula());
        ps.setString(2, objectTO.getEmail());
        ps.setString(3, objectTO.getName());
        ps.setString(4, objectTO.getLastname());
        ps.setInt(5, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(CustomersTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Customers SET Email = ?, Name = ?, Lastname = ? WHERE Cedula = ?");
        
        ps.setString(1, objectTO.getEmail());
        ps.setString(2, objectTO.getName());
        ps.setString(3, objectTO.getLastname());
        ps.setInt(4, objectTO.getCedula());
        
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(CustomersTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Customers SET State = 0 WHERE Cedula = ?");
        ps.setInt(1, objectTO.getCedula());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<CustomersTO> select(int enable) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CustomersTO> objectTOList = new ArrayList<CustomersTO>();

        ps = getConnection().prepareStatement("SELECT Cedula, Email, Name, Lastname, State FROM Customers Where State = ?");
        ps.setInt(1, enable);
        rs = ps.executeQuery();

        while (rs.next()) {
            int cedula = rs.getInt("Cedula");
            String email = rs.getString("Email");
            String name = rs.getString("Name");
            String lastname = rs.getString("Lastname");
            int state = rs.getInt("State");

            objectTOList.add(new CustomersTO(cedula,email ,name, lastname, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public void enable(CustomersTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Customers SET State = 1 WHERE Cedula = ?");
        ps.setInt(1, objectTO.getCedula());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
}
