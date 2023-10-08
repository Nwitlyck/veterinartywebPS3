package edu.ulatina.services;

import edu.ulatina.objects.CustomersTO;
import edu.ulatina.objects.SiteTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dylan
 */
public class ServiceSite extends Service implements ICrud<SiteTO>  {

    @Override
    public void insert(SiteTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Site (id, Name, Province, Canton, Adress, Phone) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setInt(1, objectTO.getId());
        ps.setString(2, objectTO.getName());
        ps.setString(3, objectTO.getProvince());
        ps.setString(4, objectTO.getCanton());
        ps.setString(5, objectTO.getAdress());
        ps.setInt(5, objectTO.getPhone());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(SiteTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Site SET Name = ?, Province = ?, Canton = ?, Adress = ?, Phone= ?, State= ? WHERE Id = ?");
        
        ps.setString(1, objectTO.getName());
        ps.setString(2, objectTO.getProvince());
        ps.setString(3, objectTO.getCanton());
        ps.setString(4, objectTO.getAdress());
        ps.setInt(5, objectTO.getPhone());
        ps.setString(6, objectTO.getState());
        ps.setInt(7, objectTO.getId());
        
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void delete(SiteTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Site SET State = 0 WHERE Id = ?");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public List<SiteTO> select() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SiteTO> objectTOList = new ArrayList<SiteTO>();

        ps = getConnection().prepareStatement("SELECT Id, Name, Province, Canton, Adress, Phone, State FROM Site");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Id");
            String name = rs.getString("Name");
            String province = rs.getString("Province");
            String canton = rs.getString("Canton");
            String adress = rs.getString("Adress");
            int phone = rs.getInt("Phone");
            String state = rs.getString("State");

            objectTOList.add(new SiteTO(id,name ,province, canton,adress,phone, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }
   
}

