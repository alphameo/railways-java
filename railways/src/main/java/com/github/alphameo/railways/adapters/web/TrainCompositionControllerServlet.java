package com.github.alphameo.railways.adapters.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.dto.LocomotiveDto;
import com.github.alphameo.railways.application.dto.TrainCompositionDto;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.TrainCompositionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainCompositionControllerServlet extends HttpServlet {

    private final TrainCompositionService trainCompositionService;
    private final CarriageService carriageService;
    private final LocomotiveService locomotiveService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrainCompositionControllerServlet(TrainCompositionService trainCompositionService, CarriageService carriageService, LocomotiveService locomotiveService) {
        this.trainCompositionService = trainCompositionService;
        this.carriageService = carriageService;
        this.locomotiveService = locomotiveService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");

        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            handleListTrainCompositions(request, response, isApiRequest);
        } else if (pathInfo.equals("/create")) {
            handleCreateForm(request, response);
        } else if (pathInfo.endsWith("/edit")) {
            String id = pathInfo.substring(1, pathInfo.length() - "/edit".length());
            handleEditForm(request, response, id);
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
            handleUpdateTrainComposition(request, response, isApiRequest);
        } else {
            handleAssembleTrainComposition(request, response, isApiRequest);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        handleUpdateTrainComposition(request, response, isApiRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String id = pathInfo.substring(1);
            handleDisassembleTrainComposition(request, response, id, isApiRequest);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleListTrainCompositions(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            List<TrainCompositionDto> trainCompositions = trainCompositionService.listAllTrainCompositions();

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), trainCompositions);
            } else {
                List<Map<String, Object>> enrichedCompositions = new ArrayList<>();
                for (TrainCompositionDto comp : trainCompositions) {
                    Map<String, Object> enriched = new HashMap<>();
                    enriched.put("id", comp.id());
                    enriched.put("locomotiveId", comp.locomotiveId());
                    enriched.put("carriageIds", comp.carriageIds());
                    String locoInfo = "N/A";
                    try {
                        LocomotiveDto loco = locomotiveService.findLocomotiveById(comp.locomotiveId());
                        locoInfo = loco.number();
                    } catch (Exception e) {
                    }
                    enriched.put("locomotiveInfo", locoInfo);
                    List<String> carriageNumbers = new ArrayList<>();
                    for (String carriageId : comp.carriageIds()) {
                        try {
                            CarriageDto carriage = carriageService.findCarriageById(carriageId);
                            carriageNumbers.add(carriage.number());
                        } catch (Exception e) {
                            carriageNumbers.add("N/A");
                        }
                    }
                    String carriagesInfo = String.join(", ", carriageNumbers);
                    enriched.put("carriagesInfo", carriagesInfo);
                    enrichedCompositions.add(enriched);
                }
                request.setAttribute("enrichedCompositions", enrichedCompositions);
                request.getRequestDispatcher("/jsp/train-compositions/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleFindById(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            TrainCompositionDto trainComposition = trainCompositionService.findTrainCompositionById(id);

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), trainComposition);
            } else {
                request.setAttribute("trainComposition", trainComposition);
                request.getRequestDispatcher("/jsp/train-compositions/detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleAssembleTrainComposition(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String locomotiveId = request.getParameter("locomotiveId");
            List<String> carriageIds = new ArrayList<>();
            for (int i = 0; ; i++) {
                String carriageId = request.getParameter("position" + i);
                if (carriageId == null || carriageId.isEmpty()) {
                    break;
                }
                carriageIds.add(carriageId);
            }

            TrainCompositionDto trainCompositionDto = new TrainCompositionDto(null, locomotiveId, carriageIds);
            trainCompositionService.assembleTrainComposition(trainCompositionDto);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                TrainCompositionDto created = trainCompositionService.findTrainCompositionById(trainCompositionDto.id());
                objectMapper.writeValue(response.getWriter(), created);
            } else {
                response.sendRedirect(request.getContextPath() + "/train-compositions");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CarriageDto> carriages = carriageService.listAllCarriages();
        List<LocomotiveDto> locomotives = locomotiveService.listAllLocomotives();
        request.setAttribute("carriages", carriages);
        request.setAttribute("locomotives", locomotives);
        request.getRequestDispatcher("/jsp/train-compositions/create.jsp").forward(request, response);
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException {
        try {
            TrainCompositionDto trainComposition = trainCompositionService.findTrainCompositionById(id);
            List<CarriageDto> carriages = carriageService.listAllCarriages();
            List<LocomotiveDto> locomotives = locomotiveService.listAllLocomotives();
            request.setAttribute("trainComposition", trainComposition);
            request.setAttribute("carriages", carriages);
            request.setAttribute("locomotives", locomotives);
            request.getRequestDispatcher("/jsp/train-compositions/edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleUpdateTrainComposition(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            String locomotiveId = request.getParameter("locomotiveId");
            List<String> carriageIds = new ArrayList<>();
            for (int i = 0; ; i++) {
                String carriageId = request.getParameter("position" + i);
                if (carriageId == null || carriageId.isEmpty()) {
                    break;
                }
                carriageIds.add(carriageId);
            }

            TrainCompositionDto trainCompositionDto = new TrainCompositionDto(id, locomotiveId, carriageIds);
            trainCompositionService.updateTrainComposition(trainCompositionDto);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), trainCompositionDto);
            } else {
                response.sendRedirect(request.getContextPath() + "/train-compositions");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleDisassembleTrainComposition(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            trainCompositionService.disassembleTrainComposition(id);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendRedirect(request.getContextPath() + "/train-compositions");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
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
