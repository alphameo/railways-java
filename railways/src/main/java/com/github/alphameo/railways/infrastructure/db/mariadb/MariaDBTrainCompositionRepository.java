package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.alphameo.railways.domain.entities.TrainComposition;
import com.github.alphameo.railways.domain.repositories.TrainCompositionRepository;
import com.github.alphameo.railways.domain.valueobjects.Id;
import com.github.alphameo.railways.exceptions.infrastructure.InfrastructureException;

public class MariaDBTrainCompositionRepository implements TrainCompositionRepository {
    private Connection connection;

    private static String CREATE_TRAIN_COMPOSITION_SQL = "INSERT INTO train_composition (id, locomotive_id) VALUES (?, ?)";
    private static String FIND_TRAIN_COMPOSITION_BY_ID = "SELECT id, locomotive_id FROM train_composition WHERE id = ?";
    private static String FIND_ALL_TRAIN_COMPOSITIONS = "SELECT id, locomotive_id FROM train_composition";
    private static String UPDATE_TRAIN_COMPOSITION_SQL = "UPDATE train_composition SET locomotive_id = ? WHERE id = ?";
    private static String DELETE_TRAIN_COMPOSITION_BY_ID = "DELETE FROM train_composition WHERE id = ?";
    private static String FIND_CARRIAGE_IDS_BY_TRAIN_COMPOSITION_ID = "SELECT id FROM carriage WHERE train_composition_id = ? ORDER BY position";
    private static String DETACH_CARRIAGES_SQL = "UPDATE carriage SET train_composition_id = NULL, position = NULL WHERE train_composition_id = ?";
    private static String ATTACH_CARRIAGE_SQL = "UPDATE carriage SET train_composition_id = ?, position = ? WHERE id = ?";

    public MariaDBTrainCompositionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(TrainComposition entity) {
        try (PreparedStatement stmt = connection.prepareStatement(CREATE_TRAIN_COMPOSITION_SQL)) {
            stmt.setString(1, entity.getId().toString());
            stmt.setString(2, entity.getLocomotiveId().toString());
            stmt.executeUpdate();
            attachCarriages(entity.getId(), entity.getCarriageIds());
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    @Override
    public Optional<TrainComposition> findById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(FIND_TRAIN_COMPOSITION_BY_ID)) {
            stmt.setString(1, id.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Id locomotiveId = Id.fromString(rs.getString("locomotive_id"));
                    List<Id> carriageIds = findCarriageIdsByTrainCompositionId(id);
                    return Optional.of(new TrainComposition(id, locomotiveId, carriageIds));
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<TrainComposition> findAll() {
        List<TrainComposition> compositions = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_ALL_TRAIN_COMPOSITIONS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Id id = Id.fromString(rs.getString("id"));
                Id locomotiveId = Id.fromString(rs.getString("locomotive_id"));
                List<Id> carriageIds = findCarriageIdsByTrainCompositionId(id);
                compositions.add(new TrainComposition(id, locomotiveId, carriageIds));
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return compositions;
    }

    @Override
    public void update(TrainComposition entity) {
        Id id = entity.getId();
        detachCarriages(id);
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_TRAIN_COMPOSITION_SQL)) {
            stmt.setString(1, entity.getLocomotiveId().toString());
            stmt.setString(2, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        attachCarriages(id, entity.getCarriageIds());
    }

    @Override
    public void deleteById(Id id) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_TRAIN_COMPOSITION_BY_ID)) {
            stmt.setString(1, id.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    private List<Id> findCarriageIdsByTrainCompositionId(Id trainCompositionId) {
        List<Id> carriageIds = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(FIND_CARRIAGE_IDS_BY_TRAIN_COMPOSITION_ID)) {
            stmt.setString(1, trainCompositionId.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    carriageIds.add(Id.fromString(rs.getString("id")));
                }
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
        return carriageIds;
    }

    private void detachCarriages(Id trainCompositionId) {
        try (PreparedStatement stmt = connection.prepareStatement(DETACH_CARRIAGES_SQL)) {
            stmt.setString(1, trainCompositionId.toString());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }

    private void attachCarriages(Id trainCompositionId, List<Id> carriageIds) {
        try (PreparedStatement stmt = connection.prepareStatement(ATTACH_CARRIAGE_SQL)) {
            for (int i = 0; i < carriageIds.size(); i++) {
                stmt.setString(1, trainCompositionId.toString());
                stmt.setInt(2, i + 1);
                stmt.setString(3, carriageIds.get(i).toString());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new InfrastructureException(e);
        }
    }
}
