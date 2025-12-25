package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Line;
import com.github.alphameo.railways.domain.repositories.LineRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.ObjectName;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBLineRepository implements LineRepository {
    private final Connection connection;

    private static String CREATE_LINE_SQL = "INSERT INTO line (id, name) VALUES (?, ?)";
    private static String FIND_LINE_BY_ID = "SELECT id, name FROM line WHERE id = ?";
    private static String FIND_ALL_LINES = "SELECT id, name FROM line";
    private static String UPDATE_LINE_SQL = "UPDATE line SET name = ? WHERE id = ?";
    private static String DELETE_LINE_BY_ID = "DELETE FROM line WHERE id = ?";
    private static String FIND_STATION_IDS_BY_LINE_ID = "SELECT station_id FROM line_station WHERE line_id = ? ORDER BY position";
    private static String DETACH_STATIONS_SQL = "DELETE FROM line_station WHERE line_id = ?";
    private static String ATTACH_STATION_SQL = "INSERT INTO line_station (line_id, station_id, position) VALUES (?, ?, ?)";

    public MariaDBLineRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(final Line entity) {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_LINE_SQL)) {
            stmt.setString(1, entity.getId().toString());
            stmt.setString(2, entity.getName().getValue());
            stmt.executeUpdate();
            attachStations(entity.getId(), entity.getStationIdOrder());
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Line> findById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_LINE_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    final ObjectName name = new ObjectName(rs.getString("name"));
                    final List<Id> stationIds = findStationIdsByLineId(id);
                    return Optional.of(new Line(id, name, stationIds));
                }
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Line> findAll() {
        final List<Line> lines = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_LINES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                final Id id = Id.fromString(rs.getString("id"));
                final ObjectName name = new ObjectName(rs.getString("name"));
                final List<Id> stationIds = findStationIdsByLineId(id);
                lines.add(new Line(id, name, stationIds));
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return lines;
    }

    @Override
    public void update(final Line entity) {
        final Id id = entity.getId();
        detachStations(id);
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_LINE_SQL)) {
            stmt.setString(1, entity.getName().getValue());
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        attachStations(id, entity.getStationIdOrder());
    }

    @Override
    public void deleteById(final Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_LINE_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    private List<Id> findStationIdsByLineId(final Id lineId) {
        final List<Id> stationIds = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_STATION_IDS_BY_LINE_ID)) {
            stmt.setString(1, lineId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stationIds.add(Id.fromString(rs.getString("station_id")));
                }
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
        return stationIds;
    }

    private void detachStations(final Id lineId) {
        try (PreparedStatement stmt = connection.prepareStatement(DETACH_STATIONS_SQL)) {
            stmt.setString(1, lineId.toString());
            stmt.executeUpdate();
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }

    private void attachStations(final Id lineId, final List<Id> stationIds) {
        try (PreparedStatement stmt = connection.prepareStatement(ATTACH_STATION_SQL)) {
            for (int i = 0; i < stationIds.size(); i++) {
                stmt.setString(1, lineId.toString());
                stmt.setString(2, stationIds.get(i).toString());
                stmt.setInt(3, i + 1);
                stmt.executeUpdate();
            }
        } catch (final Exception e) {
            throw new InfrastructureException(e);
        }
    }
}
