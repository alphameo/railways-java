package com.github.alphameo.railways.adapters.web;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alphameo.railways.application.dto.CarriageDto;
import com.github.alphameo.railways.application.services.CarriageService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CarriageControllerServlet extends HttpServlet {

    private final CarriageService carriageService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CarriageControllerServlet(CarriageService carriageService) {
        this.carriageService = carriageService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");

        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo == null || pathInfo.equals("/")) {
            handleListCarriages(request, response, isApiRequest);
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
            handleUpdateCarriage(request, response, isApiRequest);
        } else {
            handleRegisterCarriage(request, response, isApiRequest);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        handleUpdateCarriage(request, response, isApiRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String accept = request.getHeader("Accept");
        boolean isApiRequest = accept != null && accept.contains("application/json");

        if (pathInfo != null && !pathInfo.equals("/")) {
            String id = pathInfo.substring(1);
            handleUnregisterCarriage(request, response, id, isApiRequest);
            // response.sendRedirect(request.getContextPath() + "/carriages");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleListCarriages(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            List<CarriageDto> carriages = carriageService.listAllCarriages();

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), carriages);
            } else {
                request.setAttribute("carriages", carriages);
                request.getRequestDispatcher("/jsp/carriages/list.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleFindById(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            CarriageDto carriage = carriageService.findCarriageById(id);

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), carriage);
            } else {
                request.setAttribute("carriage", carriage);
                request.getRequestDispatcher("/jsp/carriages/detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleFindByNumber(HttpServletRequest request, HttpServletResponse response, String number,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            CarriageDto carriage = carriageService.findCarriageByNumber(number);

            if (isApiRequest) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), carriage);
            } else {
                request.setAttribute("carriage", carriage);
                request.getRequestDispatcher("/jsp/carriages/detail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleRegisterCarriage(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String number = request.getParameter("number");
            String contentType = request.getParameter("contentType");

            var capStr = request.getParameter("capacity");
            Long capacity = capStr.isBlank() ? null : Long.parseLong(capStr);

            CarriageDto carriageDto = new CarriageDto(null, number, contentType, capacity);
            carriageService.registerCarriage(carriageDto);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                CarriageDto created = carriageService.findCarriageByNumber(number);
                objectMapper.writeValue(response.getWriter(), created);
            } else {
                response.sendRedirect(request.getContextPath() + "/carriages");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/carriages/create.jsp").forward(request, response);
    }

    private void handleEditForm(HttpServletRequest request, HttpServletResponse response, String id)
            throws ServletException, IOException {
        try {
            CarriageDto carriage = carriageService.findCarriageById(id);
            request.setAttribute("carriage", carriage);
            request.getRequestDispatcher("/jsp/carriages/edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void handleUpdateCarriage(HttpServletRequest request, HttpServletResponse response, boolean isApiRequest)
            throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            String number = request.getParameter("number");
            String contentType = request.getParameter("contentType");

            var capStr = request.getParameter("capacity");
            Long capacity = capStr.isBlank() ? null : Long.parseLong(capStr);

            CarriageDto carriageDto = new CarriageDto(id, number, contentType, capacity);
            carriageService.updateCarriage(carriageDto);
            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                objectMapper.writeValue(response.getWriter(), carriageDto);
            } else {
                response.sendRedirect(request.getContextPath() + "/carriages");
            }
        } catch (Exception e) {
            handleError(response, e, isApiRequest);
        }
    }

    private void handleUnregisterCarriage(HttpServletRequest request, HttpServletResponse response, String id,
            boolean isApiRequest)
            throws ServletException, IOException {

        try {
            carriageService.unregisterCarriage(id);

            if (isApiRequest) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.sendRedirect(request.getContextPath() + "/carriages");
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
