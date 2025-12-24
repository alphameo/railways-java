package com.github.alphameo.railways.adapters.web;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alphameo.railways.application.dto.TrainDto;
import com.github.alphameo.railways.application.services.TrainService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TrainControllerServlet extends HttpServlet {

    private final TrainService trainService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TrainControllerServlet(TrainService trainService) {
        this.trainService = trainService;
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
                request.setAttribute("trains", trains);
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
        request.getRequestDispatcher("/jsp/trains/create.jsp").forward(request, response);
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException {
        try {
            TrainDto train = trainService.findTrainById(id);
            request.setAttribute("train", train);
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
