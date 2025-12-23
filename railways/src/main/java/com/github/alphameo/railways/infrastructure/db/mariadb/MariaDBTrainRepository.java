package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.Train;
import com.github.alphameo.railways.domain.repositories.TrainRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.domain.valueobjects.MachineNumber;
import com.github.alphameo.railways.domain.valueobjects.ScheduleEntry;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBTrainRepository implements TrainRepository {
    private Connection connection;

    private static String CREATE_TRAIN_SQL = "INSERT INTO train (id, train_composition_id, number) VALUES (?, ?, ?)";
    private static String FIND_TRAIN_BY_ID = "SELECT id, train_composition_id, number FROM train WHERE id = ?";
    private static String FIND_ALL_TRAINS = "SELECT id, train_composition_id, number FROM train";
    private static String UPDATE_TRAIN_SQL = "UPDATE train SET train_composition_id = ?, number = ? WHERE id = ?";
    private static String DELETE_TRAIN_BY_ID = "DELETE FROM train WHERE id = ?";
    private static String FIND_TRAIN_BY_NUMBER = "SELECT id, train_composition_id, number FROM train WHERE number = ?";
    private static String FIND_SCHEDULE_ENTRY_IDS_BY_TRAIN_ID = "SELECT id FROM schedule_entry WHERE train_id = ? ORDER BY order_number";
    private static String DELETE_SCHEDULE_ENTRIES_BY_TRAIN_ID = "DELETE FROM schedule_entry WHERE train_id = ?";

    public MariaDBTrainRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Train entity) {
        Id id = entity.getId();
        var byId = findById(id);
        if (byId.isPresent()) {
            throw new InfrastructureException("Carriage with id already exists: " + id);
        }
        MachineNumber number = entity.getNumber();
        var byNumber = findByNumber(number);
        if (byNumber.isPresent()) {
            throw new InfrastructureException("Carriage number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(CREATE_TRAIN_SQL)) {
            stmt.setString(1, id.toString());
            stmt.setString(2, entity.getTrainCompositionId().toString());
            stmt.setString(3, number.toString());
            stmt.executeUpdate();
            createScheduleEntries(entity.getSchedule(), id);
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Train> findById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_TRAIN_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Id trainCompositionId = Id.fromString(rs.getString("train_composition_id"));
                    MachineNumber number = new MachineNumber(rs.getString("number"));
                    List<ScheduleEntry> schedule = findScheduleEntriesByTrainId(id);
                    Train train = new Train(id, number, trainCompositionId);
                    train.updateSchedule(schedule);
                    return Optional.of(train);
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Train> findAll() {
        List<Train> trains = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_TRAINS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Id id = Id.fromString(rs.getString("id"));
                Id trainCompositionId = Id.fromString(rs.getString("train_composition_id"));
                MachineNumber number = new MachineNumber(rs.getString("number"));
                List<ScheduleEntry> schedule = findScheduleEntriesByTrainId(id);
                Train train = new Train(id, number, trainCompositionId);
                train.updateSchedule(schedule);
                trains.add(train);
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return trains;
    }

    @Override
    public void update(Train entity) {
        Id id = entity.getId();
        MachineNumber number = entity.getNumber();
        var byNumber = findByNumber(number);
        if (!byNumber.get().getId().equals(id)) {
            throw new InfrastructureException("Carriage number already exists: " + number);
        }

        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_TRAIN_SQL)) {
            stmt.setString(1, entity.getTrainCompositionId().toString());
            stmt.setString(2, number.toString());
            stmt.setString(3, id.toString());
            stmt.executeUpdate();
            deleteScheduleEntriesByTrainId(id);
            createScheduleEntries(entity.getSchedule(), id);
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public void deleteById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_TRAIN_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<Train> findByNumber(MachineNumber number) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_TRAIN_BY_NUMBER)) {
            stmt.setString(1, number.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Id id = Id.fromString(rs.getString("id"));
                    Id trainCompositionId = Id.fromString(rs.getString("train_composition_id"));
                    List<ScheduleEntry> schedule = findScheduleEntriesByTrainId(id);
                    Train train = new Train(id, number, trainCompositionId);
                    train.updateSchedule(schedule);
                    return Optional.of(train);
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    private List<ScheduleEntry> findScheduleEntriesByTrainId(Id trainId) {
        List<ScheduleEntry> entries = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT station_id, arrival_time, departure_time FROM schedule_entry WHERE train_id = ? ORDER BY order_number")) {
            stmt.setString(1, trainId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Id stationId = Id.fromString(rs.getString("station_id"));
                    Timestamp arrTs = rs.getTimestamp("arrival_time");
                    LocalDateTime arrivalTime = arrTs != null ? arrTs.toLocalDateTime() : null;
                    Timestamp depTs = rs.getTimestamp("departure_time");
                    LocalDateTime departureTime = depTs != null ? depTs.toLocalDateTime() : null;
                    entries.add(new ScheduleEntry(stationId, arrivalTime, departureTime));
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return entries;
    }

    private void createScheduleEntries(List<ScheduleEntry> scheduleEntries, Id trainId) {
        if (!scheduleEntries.isEmpty()) {
            String sql = "INSERT INTO schedule_entry (id, train_id, station_id, arrival_time, departure_time, order_number) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                for (int i = 0; i < scheduleEntries.size(); i++) {
                    ScheduleEntry se = scheduleEntries.get(i);
                    stmt.setString(1, new Id().toString());
                    stmt.setString(2, trainId.toString());
                    stmt.setString(3, se.getStationId().toString());
                    stmt.setTimestamp(4, se.getArrivalTime() != null ? Timestamp.valueOf(se.getArrivalTime()) : null);
                    stmt.setTimestamp(5,
                            se.getDepartureTime() != null ? Timestamp.valueOf(se.getDepartureTime()) : null);
                    stmt.setInt(6, i + 1);
                    stmt.executeUpdate();
                }
            } catch (Exception e) {
                throw new InfrastructureException(e);
            }
        }
    }

    private void deleteScheduleEntriesByTrainId(Id trainId) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_SCHEDULE_ENTRIES_BY_TRAIN_ID)) {
            stmt.setString(1, trainId.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }
}
