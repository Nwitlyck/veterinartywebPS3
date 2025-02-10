/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ulatina.services;

import edu.ulatina.objects.AppointmentTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nwitlyck
 */
public class ServiceAppointmentTO extends Service {

    public void insert(AppointmentTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("INSERT INTO Appointments(IdUser ,IdCustomer ,IdUnit ,IdSite ,IdAsistant ,AppointmentsDateTime ,Address ,Canton ,Province ,Description ,State) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?);");
        ps.setInt(1, objectTO.getIdUser());
        ps.setInt(2, objectTO.getIdCustomer());
        ps.setString(3, objectTO.getIdUnit());
        if (objectTO.getIdAsistant() == 0) {
            ps.setNull(4, Types.INTEGER);
        } else {
            ps.setInt(4, objectTO.getIdSite());
        }
        if (objectTO.getIdAsistant() == 0) {
            ps.setNull(5, Types.INTEGER);
        } else {
            ps.setInt(5, objectTO.getIdAsistant());
        }
        ps.setTimestamp(6, objectTO.getDate());
        ps.setString(7, objectTO.getAdress());
        ps.setInt(8, objectTO.getCanton());
        ps.setInt(9, objectTO.getProvince());
        ps.setString(10, objectTO.getDescription());
        ps.setInt(11, objectTO.getState());
        ps.executeUpdate();

        close(ps);
        close(conn);
    }

    public void update(AppointmentTO objectTO) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET AppointmentsDateTime = ?, State = ? WHERE Id = ?");

        ps.setTimestamp(1, objectTO.getDate());
        ps.setInt(2, objectTO.getState());
        ps.setInt(3, objectTO.getId());

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
    public void disableVet(int id) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET State = 95 WHERE IdUser = ? AND State = 94 OR State = 93");
        
        ps.setInt(1, id);

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
    public void disableCustomer(int cedula) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET State = 95 WHERE IdCustomer = ? AND State = 94 OR State = 93");

        ps.setInt(1, cedula);

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
    public void disableUnit(String plate) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET State = 95 WHERE IdUnit = ? AND State = 94 OR State = 93");

        ps.setString(1, plate);

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
    public void disableSite(int id) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET State = 95 WHERE IdSite = ? AND State = 94 OR State = 93");

        ps.setInt(1, id);

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    
    public void disableAsis(int id) throws Exception {
        PreparedStatement ps = null;

        ps = getConnection().prepareStatement("UPDATE Appointments SET State = 95 WHERE IdAsistant = ? AND State = 94 OR State = 93");

        ps.setInt(1, id);

        ps.executeUpdate();

        close(ps);
        close(conn);
    }
    

    public List<AppointmentTO> select(int enable, Date fistDate, Date secondDate) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AppointmentTO> objectTOList = new ArrayList<AppointmentTO>();

        ps = getConnection().prepareStatement(
                "SELECT Id ,IdUser ,IdCustomer ,IdUnit ,IdSite ,IdAsistant ,AppointmentsDateTime ,Address ,Canton ,Province ,Description ,State FROM Appointments WHERE State = ? AND AppointmentsDateTime BETWEEN ? AND ?;");
        ps.setInt(1, enable);
        ps.setDate(2, fistDate);
        ps.setDate(3, secondDate);
        rs = ps.executeQuery();

        while (rs.next()) {

            int id = rs.getInt("Id");
            int idUser = rs.getInt("IdUser");
            int idCustomer = rs.getInt("IdCustomer");
            String idUnit = rs.getString("IdUnit");
            int idSite = rs.getInt("IdSite");
            int idAsistant = rs.getInt("IdAsistant");
            Timestamp date = rs.getTimestamp("AppointmentsDateTime");
            String adress = rs.getString("Address");
            int canton = rs.getInt("Canton");
            int province = rs.getInt("Province");
            String description = rs.getString("Description");
            int state = rs.getInt("State");

            objectTOList.add(new AppointmentTO(id, idUser, idCustomer, idUnit, idSite, idAsistant, date, adress, canton, province, description, state));
        }

        close(rs);
        close(ps);
        close(conn);

        return objectTOList;
    }

}
