package edu.ulatina.services;

import edu.ulatina.objects.SiteTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceSite extends Service implements ICrud<SiteTO>  {

    @Override
    public void insert(SiteTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Site (Name, Province, Canton, Adress, Phone, State) VALUES (?, ?, ?, ?, ?,1)");
        ps.setString(1, objectTO.getName());
        ps.setInt(2, objectTO.getProvince());
        ps.setInt(3, objectTO.getCanton());
        ps.setString(4, objectTO.getAdress());
        ps.setInt(5, objectTO.getPhone());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    @Override
    public void update(SiteTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Site SET Name = ?, Province = ?, Canton = ?, Adress = ?, Phone= ? WHERE Id = ?");
        
        ps.setString(1, objectTO.getName());
        ps.setInt(2, objectTO.getProvince());
        ps.setInt(3, objectTO.getCanton());
        ps.setString(4, objectTO.getAdress());
        ps.setInt(5, objectTO.getPhone());
        ps.setInt(6, objectTO.getId());
        
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
    public List<SiteTO> select(int enable) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SiteTO> objectTOList = new ArrayList<SiteTO>();

        ps = getConnection().prepareStatement("SELECT Id, Name, Province, Canton, Adress, Phone, State FROM Site Where State = ?");
        ps.setInt(1, enable);
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("Id");
            String name = rs.getString("Name");
            int province = rs.getInt("Province");
            int canton = rs.getInt("Canton");
            String adress = rs.getString("Adress");
            int phone = rs.getInt("Phone");
            int state = rs.getInt("State");

            objectTOList.add(new SiteTO(id,name ,province, canton,adress,phone, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

    @Override
    public void enable(SiteTO objectTO) throws Exception {
       PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Site SET State = 1 WHERE Id = ?");
        ps.setInt(1, objectTO.getId());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }
   
}

