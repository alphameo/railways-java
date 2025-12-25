package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Carriage;
import com.github.alphameo.railways.domain.repositories.CarriageRepository;
import com.github.alphameo.railways.domain.valueobjects.CarriageContentType;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBCarriageRepository implements CarriageRepository {
    private final Connection connection;

    private static String CREATE_CARRIAGE_SQL = "INSERT INTO carriage (id, number, content_type, capacity) VALUES(?, ?, ?, ?)";
    private static String FIND_CARRIAGE_BY_ID = "SELECT id, number, content_type, capacity FROM carriage WHERE id = ?";
    private static String FIND_CARRIAGE_BY_NUMBER = "SELECT id, number, content_type, capacity FROM carriage WHERE number = ?";
    private static String FIND_ALL_CARRIAGES = "SELECT id, number, content_type, capacity FROM carriage";
    private static String UPDATE_CARRIAGE_SQL = "UPDATE carriage SET number = ?, content_type = ?, capacity = ? WHERE id = ?";
    private static String DELETE_CARRIAGE_BY_ID = "DELETE FROM carriage WHERE id = ?";

    public MariaDBCarriageRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(final Carriage entity) {
        final Id id = entity.getId();
        final var byId = findById(id);
        if (byId.isPresent()) {
            throw new InfrastructureException("Carriage with id already exists: " + id);
        }
        final MachineNumber number = entity.getNumber();
        final var byNumber = findByNumber(number);
        if (byNumber.isPresent()) {
            throw new InfrastructureException("Carriage number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(CREATE_CARRIAGE_SQL)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, number.toString());
            stmt.setString(3, entity.getContentType().toString());
            stmt.setLong(4, entity.getCapacity());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Carriage> findById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_CARRIAGE_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCarriage(rs));
                }
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Carriage> findAll() {
        final List<Carriage> carriages = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_CARRIAGES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                carriages.add(mapResultSetToCarriage(rs));
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return carriages;
    }

    @Override
    public void update(final Carriage entity) {
        final Id id = entity.getId();
        final MachineNumber number = entity.getNumber();
        final var byNumber = findByNumber(number);
        if (byNumber.isPresent() && !byNumber.get().getId().equals(id)) {
            throw new InfrastructureException("Carriage number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_CARRIAGE_SQL)) {
            stmt.setString(1, number.toString());
            stmt.setString(2, entity.getContentType().toString());
            stmt.setLong(3, entity.getCapacity());
            stmt.setString(4, id.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public void deleteById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_CARRIAGE_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Carriage> findByNumber(final MachineNumber number) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_CARRIAGE_BY_NUMBER)) {
            stmt.setString(1, number.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCarriage(rs));
                }
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    private Carriage mapResultSetToCarriage(final ResultSet rs) throws Exception {
        final Id id = Id.fromString(rs.getString("id"));
        final MachineNumber number = new MachineNumber(rs.getString("number"));
        final CarriageContentType contentType = CarriageContentType.create(rs.getString("content_type"));
        final Long capacity = rs.getLong("capacity");
        final Carriage carriage = new Carriage(id, number);
        carriage.changeContentType(contentType);
        carriage.changeCapacity(capacity);
        return carriage;
    }
}
