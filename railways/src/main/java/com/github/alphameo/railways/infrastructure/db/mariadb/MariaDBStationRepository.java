package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Station;
import com.github.alphameo.railways.domain.repositories.StationRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.domain.valueobjects.StationLocation;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBStationRepository implements StationRepository {
    private final Connection connection;

    private static String CREATE_STATION_SQL = "INSERT INTO station (id, name, location) VALUES (?, ?, ?)";
    private static String FIND_STATION_BY_ID = "SELECT id, name, location FROM station WHERE id = ?";
    private static String FIND_ALL_STATIONS = "SELECT id, name, location FROM station";
    private static String UPDATE_STATION_SQL = "UPDATE station SET name = ?, location = ? WHERE id = ?";
    private static String DELETE_STATION_BY_ID = "DELETE FROM station WHERE id = ?";

    public MariaDBStationRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(final Station entity) {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_STATION_SQL)) {
            stmt.setString(1, entity.getId().toString());
            stmt.setString(2, entity.getName().toString());
            stmt.setString(3, entity.getLocation().toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Station> findById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_STATION_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStation(rs));
                }
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Station> findAll() {
        final List<Station> stations = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_STATIONS);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stations.add(mapResultSetToStation(rs));
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return stations;
    }

    @Override
    public void update(final Station entity) {
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_STATION_SQL)) {
            stmt.setString(1, entity.getName().toString());
            stmt.setString(2, entity.getLocation().toString());
            stmt.setString(3, entity.getId().toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public void deleteById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_STATION_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    private Station mapResultSetToStation(final ResultSet rs) throws Exception {
        final Id id = Id.fromString(rs.getString("id"));
        final ObjectName name = new ObjectName(rs.getString("name"));
        final StationLocation location = new StationLocation(rs.getString("location"));
        return new Station(id, name, location);
    }
}
