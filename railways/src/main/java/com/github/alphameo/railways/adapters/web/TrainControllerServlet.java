package com.github.alphameo.railways.adapters.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.dto.ScheduleEntryDto;
import com.github.alphameo.railways.application.dto.StationDto;
import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.application.services.TrainService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainControllerServlet extends HttpServlet {

    private final TrainService trainService;
    private final TrainCompositionService trainCompositionService;
    private final LocomotiveService locomotiveService;
    private final CarriageService carriageService;
    private final StationService stationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrainControllerServlet(ServiceProvider serviceProvider) {
        this.trainService = serviceProvider.getTrainService();
        this.trainCompositionService = serviceProvider.getTrainCompositionService();
        this.locomotiveService = serviceProvider.getLocomotiveService();
        this.carriageService = serviceProvider.getCarriageService();
        this.stationService = serviceProvider.getStationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");

        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            handleListTrains(request, response, isApiRequest);
        } else if (pathInfo.equals("/create")) {
            handleCreateForm(request, response);
        } else if (pathInfo.endsWith("/edit")) {
            String id = pathInfo.substring(1, pathInfo.length() - "/edit".length());
            handleEditForm(request, response, id);
        } else if (pathInfo.startsWith("/number/")) {
            String number = pathInfo.substring("/number/".length());
            handleFindByNumber(request, response, number, isApiRequest);
        } else {
            String id = pathInfo.substring(1); 
            handleFindById(request, response, id, isApiRequest);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        String id = request.getParameter("id");
        if (id != null && !id.isEmpty()) {
            handleUpdateTrain(request, response, isApiRequest);
        } else {
            handleRegisterTrain(request, response, isApiRequest);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        handleUpdateTrain(request, response, isApiRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String id = pathInfo.substring(1);
            handleUnregisterTrain(request, response, id, isApiRequest);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleListTrains(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            List<TrainDto> trains = trainService.listAllTrains();

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), trains);
            } else {
                List<Map<String, Object>> enrichedTrains = new ArrayList<>();
                for (TrainDto train : trains) {
                    Map<String, Object> trainMap = new HashMap<>();
                    trainMap.put("id", train.id());
                    trainMap.put("number", train.number());
                    try {
                        TrainCompositionDto composition = trainCompositionService.findTrainCompositionById(train.trainCompositionId());
                        try {
                            LocomotiveDto loco = locomotiveService.findLocomotiveById(composition.locomotiveId());
                            trainMap.put("locomotiveNumber", loco.number());
                        } catch (Exception e) {
                            trainMap.put("locomotiveNumber", "N/A");
                        }
                        List<String> carriageNumbers = new ArrayList<>();
                        for (String carriageId : composition.carriageIds()) {
                            try {
                                CarriageDto carriage = carriageService.findCarriageById(carriageId);
                                carriageNumbers.add(carriage.number());
                            } catch (Exception e) {
                                carriageNumbers.add("N/A");
                            }
                        }
                        trainMap.put("carriageNumbers", String.join(", ", carriageNumbers));
                    } catch (Exception e) {
                        trainMap.put("locomotiveNumber", "N/A");
                        trainMap.put("carriageNumbers", "N/A");
                    }
                    enrichedTrains.add(trainMap);
                }
                request.setAttribute("trains", enrichedTrains);
                request.getRequestDispatcher("/jsp/trains/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleFindById(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            TrainDto train = trainService.findTrainById(id);

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), train);
            } else {
                request.setAttribute("train", train);
                try {
                    TrainCompositionDto composition = trainCompositionService.findTrainCompositionById(train.trainCompositionId());
                    request.setAttribute("composition", composition);
                } catch (Exception e) {
                    // Composition not found, leave attribute unset
                }
                List<ScheduleEntryDto> schedule = trainService.getScheduleForTrain(id);
                List<Map<String, Object>> enrichedSchedule = new ArrayList<>();
                int order = 1;
                for (ScheduleEntryDto entry : schedule) {
                    Map<String, Object> enriched = new HashMap<>();
                    enriched.put("order", order++);
                    enriched.put("stationId", entry.stationId());
                    enriched.put("stationName", "Unknown"); // default
                    try {
                        StationDto station = stationService.findStationById(entry.stationId());
                        enriched.put("station", entry.stationId() + " - " + station.name());
                        enriched.put("stationName", station.name());
                    } catch (Exception e) {
                        enriched.put("station", entry.stationId() + " - Unknown");
                    }
                    enriched.put("arrival", entry.arrivalTime() != null ? entry.arrivalTime().toString() : "N/A");
                    enriched.put("departure", entry.departureTime() != null ? entry.departureTime().toString() : "N/A");
                    enrichedSchedule.add(enriched);
                }
                request.setAttribute("schedule", enrichedSchedule);
                request.getRequestDispatcher("/jsp/trains/detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleFindByNumber(HttpServletRequest request, HttpServletResponse response, String number,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            TrainDto train = trainService.findTrainByNumber(number);

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), train);
            } else {
                request.setAttribute("train", train);
                request.getRequestDispatcher("/jsp/trains/detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleRegisterTrain(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String number = request.getParameter("number");
            String trainCompositionId = request.getParameter("trainCompositionId");

            TrainDto trainDto = new TrainDto(null, number, trainCompositionId, null); 
            trainService.registerTrain(trainDto);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                TrainDto created = trainService.findTrainByNumber(number);
                objectMapper.writeValue(response.getWriter(), created);
            } else {
                response.sendRedirect(request.getContextPath() + "/trains");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String, Object>> enrichedCompositions = enrichCompositions();
        request.setAttribute("compositions", enrichedCompositions);
        request.getRequestDispatcher("/jsp/trains/create.jsp").forward(request, response);
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException {
        try {
            TrainDto train = trainService.findTrainById(id);
            request.setAttribute("train", train);
            List<Map<String, Object>> enrichedCompositions = enrichCompositions();
            request.setAttribute("compositions", enrichedCompositions);
            request.getRequestDispatcher("/jsp/trains/edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleUpdateTrain(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            String number = request.getParameter("number");
            String trainCompositionId = request.getParameter("trainCompositionId");

            TrainDto trainDto = new TrainDto(id, number, trainCompositionId, null); 
            trainService.updateTrain(trainDto);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), trainDto);
            } else {
                response.sendRedirect(request.getContextPath() + "/trains");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleUnregisterTrain(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            trainService.unregisterTrain(id);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendRedirect(request.getContextPath() + "/trains");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private List<Map<String, Object>> enrichCompositions() {
        List<TrainCompositionDto> compositions = trainCompositionService.listAllTrainCompositions();
        List<Map<String, Object>> enrichedCompositions = new ArrayList<>();
        for (TrainCompositionDto comp : compositions) {
            Map<String, Object> compMap = new HashMap<>();
            compMap.put("id", comp.id());
            String locoSummary = "N/A";
            try {
                LocomotiveDto loco = locomotiveService.findLocomotiveById(comp.locomotiveId());
                locoSummary = String.format("%s (%s)", loco.number(), comp.locomotiveId());
            } catch (Exception e) {
                // Handle missing locomotive
            }
            List<String> carriageSummaries = new ArrayList<>();
            for (String carriageId : comp.carriageIds()) {
                try {
                    CarriageDto carriage = carriageService.findCarriageById(carriageId);
                    carriageSummaries.add(carriage.number());
                } catch (Exception e) {
                    carriageSummaries.add("N/A");
                }
            }
            String summary = "<strong>Locomotive:</strong> " + locoSummary + "<br><strong>Carriages:</strong> " + String.join(", ", carriageSummaries);
            compMap.put("summary", summary);
            enrichedCompositions.add(compMap);
        }
        return enrichedCompositions;
    }

    private void handleError(HttpServletResponse response, Exception e, boolean isApiRequest)
            throws IOException {

        if (isApiRequest) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
