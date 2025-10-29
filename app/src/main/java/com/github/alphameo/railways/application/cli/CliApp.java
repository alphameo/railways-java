package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.cli.commands.MessageCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.FindCarriageByIdCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.FindCarriageByNumberCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.ListCarriagesCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.RegisterCarriageCommand;
import com.github.alphameo.railways.application.cli.commands.carriage.UnregisterCarriageCommand;
import com.github.alphameo.railways.application.cli.commands.line.DeclareLineCommand;
import com.github.alphameo.railways.application.cli.commands.line.DisbandLineCommand;
import com.github.alphameo.railways.application.cli.commands.line.FindLineByIdCommand;
import com.github.alphameo.railways.application.cli.commands.line.ListLineStationsCommand;
import com.github.alphameo.railways.application.cli.commands.line.ListLinesCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.FindLocomotiveByIdCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.FindLocomotiveByNumberCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.ListLocomotivesCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.RegisterLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.locomotive.UnregisterLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.station.FindStationByIdCommand;
import com.github.alphameo.railways.application.cli.commands.station.ListStationCommand;
import com.github.alphameo.railways.application.cli.commands.station.RegisterStationCommand;
import com.github.alphameo.railways.application.cli.commands.station.UnregisterStationCommand;
import com.github.alphameo.railways.application.cli.commands.train.FindTrainByIdCommand;
import com.github.alphameo.railways.application.cli.commands.train.FindTrainByNumberCommand;
import com.github.alphameo.railways.application.cli.commands.train.InsertTrainScheduleEntryCommand;
import com.github.alphameo.railways.application.cli.commands.train.ListTrainsCommand;
import com.github.alphameo.railways.application.cli.commands.train.RegisterTrainCommand;
import com.github.alphameo.railways.application.cli.commands.train.RemoveTrainScheduleEntryCommand;
import com.github.alphameo.railways.application.cli.commands.train.UnregisterTrainCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.AssembleLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.DisassembleTrainCompositionCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.FindTrainCompositionByIdCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.ListTrainCompositionsCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.ServiceProvider;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.application.services.TrainService;

import lombok.NonNull;

public class CliApp {

    private final List<CliModule> modules = new ArrayList<>();
    private final MessageCommand helpMsgCmd = new MessageCommand("help", "h", "");
    private final MessageCommand quitMsgCmd = new MessageCommand("quit", "q", "App closed.");

    public CliApp(ServiceProvider servFact) {
        CarriageService carriageService = servFact.getCarriageService();
        LineService lineService = servFact.getLineService();
        LocomotiveService locomotiveService = servFact.getLocomotiveService();
        StationService stationService = servFact.getStationService();
        TrainService trainService = servFact.getTrainService();
        TrainCompositionService trainCompositionService = servFact.getTrainCompositionService();

        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carriageService));
        carrMod.addCmd(new ListCarriagesCommand(carriageService));
        carrMod.addCmd(new FindCarriageByIdCommand(carriageService));
        carrMod.addCmd(new FindCarriageByNumberCommand(carriageService));
        carrMod.addCmd(new UnregisterCarriageCommand(carriageService));

        final var lineMod = new CliModule("line");
        lineMod.addCmd(new DeclareLineCommand(lineService));
        lineMod.addCmd(new FindLineByIdCommand(lineService));
        lineMod.addCmd(new ListLinesCommand(lineService));
        lineMod.addCmd(new DisbandLineCommand(lineService));
        lineMod.addCmd(new ListLineStationsCommand(lineService));

        final var locoMod = new CliModule("locomotive");
        locoMod.addCmd(new RegisterLocomotiveCommand(locomotiveService));
        locoMod.addCmd(new ListLocomotivesCommand(locomotiveService));
        locoMod.addCmd(new FindLocomotiveByIdCommand(locomotiveService));
        locoMod.addCmd(new FindLocomotiveByNumberCommand(locomotiveService));
        locoMod.addCmd(new UnregisterLocomotiveCommand(locomotiveService));

        final var stationMod = new CliModule("station");
        stationMod.addCmd(new RegisterStationCommand(stationService));
        stationMod.addCmd(new ListStationCommand(stationService));
        stationMod.addCmd(new FindStationByIdCommand(stationService));
        stationMod.addCmd(new UnregisterStationCommand(stationService));

        final var trainMod = new CliModule("train");
        trainMod.addCmd(new RegisterTrainCommand(trainService));
        trainMod.addCmd(new ListTrainsCommand(trainService));
        trainMod.addCmd(new FindTrainByIdCommand(trainService));
        trainMod.addCmd(new FindTrainByNumberCommand(trainService));
        trainMod.addCmd(new UnregisterTrainCommand(trainService));
        trainMod.addCmd(new InsertTrainScheduleEntryCommand(trainService));
        trainMod.addCmd(new RemoveTrainScheduleEntryCommand(trainService));

        final var trainCompoMod = new CliModule("traincomposition");
        trainCompoMod.addCmd(new AssembleLocomotiveCommand(trainCompositionService));
        trainCompoMod.addCmd(new ListTrainCompositionsCommand(trainCompositionService));
        trainCompoMod.addCmd(new FindTrainCompositionByIdCommand(trainCompositionService));
        trainCompoMod.addCmd(new DisassembleTrainCompositionCommand(trainCompositionService));

        this.addModule(carrMod);
        this.addModule(lineMod);
        this.addModule(locoMod);
        this.addModule(stationMod);
        this.addModule(trainMod);
        this.addModule(trainCompoMod);
    }

    public void addModule(@NonNull final CliModule module) {
        this.modules.add(module);
        this.updateHelpMsg();
    }

    public void run() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(Renderer.PROMPT);
                final var inp = scanner.nextLine();
                final String[] args = inp.strip().split(" +");
                final var firstArg = args[0];
                if (quitMsgCmd.getName().equals(firstArg) || quitMsgCmd.getShortName().equals(firstArg)) {
                    quitMsgCmd.execute();
                    break;
                }
                if (helpMsgCmd.getName().equals(firstArg) || helpMsgCmd.getShortName().equals(firstArg)) {
                    helpMsgCmd.execute();
                    continue;
                }
                if (firstArg.equals("")) {
                    continue;
                }

                String cmdName;
                final String[] cmdArgs;
                if (args.length > 1) {
                    cmdName = args[1];
                    cmdArgs = Arrays.copyOfRange(args, 2, args.length);
                } else {
                    cmdName = "";
                    cmdArgs = new String[0];
                }
                CliCommand cmd;
                var isCmdFound = false;
                for (final var module : modules) {
                    if (module.getName().equals(firstArg)) {
                        cmd = module.getCmd(cmdName, cmdArgs);
                        try {
                            cmd.execute();
                        } catch (final Exception e) {
                            System.out.printf("Execution error: %s\n%s\n", e.getMessage(), cmd.getSignature());
                        }
                        isCmdFound = true;
                        continue;
                    }
                }
                if (!isCmdFound) {
                    System.out.printf("No such module: %s\n%s\n", firstArg, getHelp());
                }
            } catch (final Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public String getHelp() {
        return this.helpMsgCmd.getMsg();
    }

    public void updateHelpMsg() {
        final var sb = new StringBuilder();

        sb.append("Available modules:\n");
        for (final var module : modules) {
            sb.append("\t");
            sb.append(module.getName());
            sb.append("\n");
        }

        sb.append("Available commands:\n");
        sb.append("\t");
        sb.append(helpMsgCmd.getSignature());
        sb.append("\n");
        sb.append("\t");
        sb.append(quitMsgCmd.getSignature());
        sb.append("\n");

        this.helpMsgCmd.setMsg(sb.toString());
    }
}
