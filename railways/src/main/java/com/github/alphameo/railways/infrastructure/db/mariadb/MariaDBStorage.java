package com.github.alphameo.railways.infrastructure.db.mariadb;

import java.sql.Connection;

import com.github.alphameo.railways.infrastructure.Storage;

import lombok.Getter;

@Getter
public class MariaDBStorage implements Storage {

    private static MariaDBStorage INSTANCE;

    private static Connection connection;

    private final MariaDBCarriageRepository carriageRepository;
    private final MariaDBLineRepository lineRepository;
    private final MariaDBLocomotiveRepository locomotiveRepository;
    private final MariaDBStationRepository stationRepository;
    private final MariaDBTrainRepository trainRepository;
    private final MariaDBTrainCompositionRepository trainCompositionRepository;

    private MariaDBStorage() {
        this.carriageRepository = new MariaDBCarriageRepository(connection);
        this.lineRepository = new MariaDBLineRepository(connection);
        this.locomotiveRepository = new MariaDBLocomotiveRepository(connection);
        this.stationRepository = new MariaDBStationRepository(connection);
        this.trainRepository = new MariaDBTrainRepository(connection);
        this.trainCompositionRepository = new MariaDBTrainCompositionRepository(connection);
    }

    public static void setupConnection(Connection con) {
        if (con == null) {

        }
        connection = con;
    }

    /**
     * Call {@link MariaDBStorage.setupConnection} before if first use
     * 
     * @return singleton instance of MariaDBStorage
     */
    public static MariaDBStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MariaDBStorage();
        }

        return INSTANCE;
    }
}
