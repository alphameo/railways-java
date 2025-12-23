package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Locomotive;
import com.github.alphameo.railways.domain.repositories.LocomotiveRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.LocomotiveModel;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBLocomotiveRepository implements LocomotiveRepository {
    private Connection connection;

    private static String CREATE_LOCOMOTIVE_SQL = "INSERT INTO locomotive (id, number, model) VALUES (?, ?, ?)";
    private static String FIND_LOCOMOTIVE_BY_ID = "SELECT id, number, model FROM locomotive WHERE id = ?";
    private static String FIND_ALL_LOCOMOTIVES = "SELECT id, number, model FROM locomotive";
    private static String UPDATE_LOCOMOTIVE_SQL = "UPDATE locomotive SET number = ?, model = ? WHERE id = ?";
    private static String DELETE_LOCOMOTIVE_BY_ID = "DELETE FROM locomotive WHERE id = ?";
    private static String FIND_LOCOMOTIVE_BY_NUMBER = "SELECT id, number, model FROM locomotive WHERE number = ?";

    public MariaDBLocomotiveRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Locomotive entity) {
        Id id = entity.getId();
        var byId = findById(id);
        if (byId.isPresent()) {
            throw new InfrastructureException("Locomotive with id already exists: " + id);
        }
        MachineNumber number = entity.getNumber();
        var byNumber = findByNumber(number);
        if (byNumber.isPresent()) {
            throw new InfrastructureException("Locomotive number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(CREATE_LOCOMOTIVE_SQL)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, number.toString());
            stmt.setString(3, entity.getModel().toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Locomotive> findById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_LOCOMOTIVE_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLocomotive(rs));
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Locomotive> findAll() {
        List<Locomotive> locomotives = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_LOCOMOTIVES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                locomotives.add(mapResultSetToLocomotive(rs));
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return locomotives;
    }

    @Override
    public void update(Locomotive entity) {
        Id id = entity.getId();
        MachineNumber number = entity.getNumber();
        var byNumber = findByNumber(number);
        if (!byNumber.get().getId().equals(id)) {
            throw new InfrastructureException("Locomotive number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_LOCOMOTIVE_SQL)) {
            stmt.setString(1, number.toString());
            stmt.setString(2, entity.getModel().toString());
            stmt.setString(3, id.toString());
            stmt.executeUpdate();
        } catch (

        Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public void deleteById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_LOCOMOTIVE_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Locomotive> findByNumber(MachineNumber number) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_LOCOMOTIVE_BY_NUMBER)) {
            stmt.setString(1, number.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToLocomotive(rs));
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    private Locomotive mapResultSetToLocomotive(ResultSet rs) throws Exception {
        Id id = Id.fromString(rs.getString("id"));
        MachineNumber number = new MachineNumber(rs.getString("number"));
        LocomotiveModel model = new LocomotiveModel(rs.getString("model"));
        return new Locomotive(id, number, model);
    }
}
