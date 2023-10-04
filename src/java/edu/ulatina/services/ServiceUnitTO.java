
package edu.ulatina.services;

import edu.ulatina.objects.CustomersTO;
import edu.ulatina.objects.UnitTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ServiceUnitTO extends Service implements ICrud<UnitTO>{

    @Override
    public void insert(UnitTO objectTO) throws Exception {
                PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Units (Plate, Name, State) VALUES (?, ?, 1)");
        ps.setInt(1, objectTO.getPlate());
        ps.setString(2, objectTO.getName());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(UnitTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Units SET Name = ? WHERE Plate = ?");
        
        ps.setString(1, objectTO.getName());
        ps.setInt(2, objectTO.getPlate());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(UnitTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Units SET State = 0 WHERE Plate = ?");
        ps.setInt(1, objectTO.getPlate());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<UnitTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UnitTO> objectTOList = new ArrayList<UnitTO>();

        ps = getConnection().prepareStatement("SELECT Plate, Name, State FROM Units");
        rs = ps.executeQuery();

        while (rs.next()) {
            int plate = rs.getInt("Plate");
            String name = rs.getString("Name");
            int state = rs.getInt("State");

            objectTOList.add(new UnitTO(plate, name, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
    
}
