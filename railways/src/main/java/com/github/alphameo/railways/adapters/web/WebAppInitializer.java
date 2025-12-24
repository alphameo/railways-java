package com.github.alphameo.railways.adapters.web;

import java.io.File;

import com.github.alphameo.railways.application.services.ServiceProvider;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class WebAppInitializer {

    private final ServiceProvider serviceProvider;
    private final int port;
    private Tomcat tomcat;

    public WebAppInitializer(ServiceProvider serviceProvider, int port) {
        this.serviceProvider = serviceProvider;
        this.port = port;
    }

    public void start() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();
        String contextPath = "";
        String docBase = new File("railways/src/main/webapp").getAbsolutePath();

        Context ctx = tomcat.addWebapp(contextPath, docBase);

        WebResourceRoot resources = new StandardRoot(ctx);

        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                new File("railways/bin/main").getAbsolutePath(), "/"));
        ctx.setResources(resources);

        addServlets(ctx);

        tomcat.start();
        System.out.println("Web server started on http://localhost:" + port);
        System.out.println("Visit: http://localhost:" + port);

        tomcat.getServer().await();
    }

    public void stop() throws LifecycleException {
        if (tomcat != null) {
            tomcat.stop();
        }
    }

    private void addServlets(Context ctx) {
        Tomcat.addServlet(ctx, "carriageServlet", new CarriageControllerServlet(serviceProvider.getCarriageService()));
        ctx.addServletMappingDecoded("/api/carriages/*", "carriageServlet");
        ctx.addServletMappingDecoded("/carriages/*", "carriageServlet");

        Tomcat.addServlet(ctx, "stationServlet", new StationControllerServlet(serviceProvider.getStationService()));
        ctx.addServletMappingDecoded("/api/stations/*", "stationServlet");
        ctx.addServletMappingDecoded("/stations/*", "stationServlet");

        Tomcat.addServlet(ctx, "locomotiveServlet",
                new LocomotiveControllerServlet(serviceProvider.getLocomotiveService()));
        ctx.addServletMappingDecoded("/api/locomotives/*", "locomotiveServlet");
        ctx.addServletMappingDecoded("/locomotives/*", "locomotiveServlet");

        Tomcat.addServlet(ctx, "trainServlet", new TrainControllerServlet(serviceProvider.getTrainService()));
        ctx.addServletMappingDecoded("/api/trains/*", "trainServlet");
        ctx.addServletMappingDecoded("/trains/*", "trainServlet");

        Tomcat.addServlet(ctx, "lineServlet", new LineControllerServlet(serviceProvider.getLineService()));
        ctx.addServletMappingDecoded("/api/lines/*", "lineServlet");
        ctx.addServletMappingDecoded("/lines/*", "lineServlet");

        Tomcat.addServlet(ctx, "trainCompositionServlet", new TrainCompositionControllerServlet(serviceProvider.getTrainCompositionService()));
        ctx.addServletMappingDecoded("/api/train-compositions/*", "trainCompositionServlet");
        ctx.addServletMappingDecoded("/train-compositions/*", "trainCompositionServlet");
    }
}
