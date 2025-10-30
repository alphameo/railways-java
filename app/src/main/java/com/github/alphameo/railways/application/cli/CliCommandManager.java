package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
import com.github.alphameo.railways.application.cli.commands.MessageCommand;
import com.github.alphameo.railways.application.cli.commands.QuitCommand;
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

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class CliCommandManager {

    private static final String CARRIAGE_MODULE_NAME = "carriage";
    private static final String LINE_MODULE_NAME = "line";
    private static final String LOCOMOTIVE_MODULE_NAME = "locomotive";
    private static final String STATION_MODULE_NAME = "station";
    private static final String TRAIN_MODULE_NAME = "train";
    private static final String TRAIN_COMPOSITION_MODULE_NAME = "traincomposition";

    private final List<CliModule> modules = new ArrayList<>();
    private final List<CliCommand> globalCommands = new ArrayList<>();
    private final MessageCommand helpMsgCmd = new MessageCommand("help", "h", "");

    public CliCommandManager(ServiceProvider serviceProvider) {
        CarriageService carriageService = serviceProvider.getCarriageService();
        LineService lineService = serviceProvider.getLineService();
        LocomotiveService locomotiveService = serviceProvider.getLocomotiveService();
        StationService stationService = serviceProvider.getStationService();
        TrainService trainService = serviceProvider.getTrainService();
        TrainCompositionService trainCompositionService = serviceProvider.getTrainCompositionService();

        final var carrMod = new CliModule(CARRIAGE_MODULE_NAME);
        carrMod.addCmd(new RegisterCarriageCommand(carriageService));
        carrMod.addCmd(new ListCarriagesCommand(carriageService));
        carrMod.addCmd(new FindCarriageByIdCommand(carriageService));
        carrMod.addCmd(new FindCarriageByNumberCommand(carriageService));
        carrMod.addCmd(new UnregisterCarriageCommand(carriageService));

        final var lineMod = new CliModule(LINE_MODULE_NAME);
        lineMod.addCmd(new DeclareLineCommand(lineService));
        lineMod.addCmd(new FindLineByIdCommand(lineService));
        lineMod.addCmd(new ListLinesCommand(lineService));
        lineMod.addCmd(new DisbandLineCommand(lineService));
        lineMod.addCmd(new ListLineStationsCommand(lineService));

        final var locoMod = new CliModule(LOCOMOTIVE_MODULE_NAME);
        locoMod.addCmd(new RegisterLocomotiveCommand(locomotiveService));
        locoMod.addCmd(new ListLocomotivesCommand(locomotiveService));
        locoMod.addCmd(new FindLocomotiveByIdCommand(locomotiveService));
        locoMod.addCmd(new FindLocomotiveByNumberCommand(locomotiveService));
        locoMod.addCmd(new UnregisterLocomotiveCommand(locomotiveService));

        final var stationMod = new CliModule(STATION_MODULE_NAME);
        stationMod.addCmd(new RegisterStationCommand(stationService));
        stationMod.addCmd(new ListStationCommand(stationService));
        stationMod.addCmd(new FindStationByIdCommand(stationService));
        stationMod.addCmd(new UnregisterStationCommand(stationService));

        final var trainMod = new CliModule(TRAIN_MODULE_NAME);
        trainMod.addCmd(new RegisterTrainCommand(trainService));
        trainMod.addCmd(new ListTrainsCommand(trainService));
        trainMod.addCmd(new FindTrainByIdCommand(trainService));
        trainMod.addCmd(new FindTrainByNumberCommand(trainService));
        trainMod.addCmd(new UnregisterTrainCommand(trainService));
        trainMod.addCmd(new InsertTrainScheduleEntryCommand(trainService));
        trainMod.addCmd(new RemoveTrainScheduleEntryCommand(trainService));

        final var trainCompoMod = new CliModule(TRAIN_COMPOSITION_MODULE_NAME);
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

        final QuitCommand quitCmd = new QuitCommand();
        this.addGlobalCommand(this.helpMsgCmd);
        this.addGlobalCommand(quitCmd);
    }

    public void addModule(@NonNull final CliModule module) {
        this.modules.add(module);
        this.updateHelpMsg();
    }

    public void addGlobalCommand(@NonNull final CliCommand command) {
        this.globalCommands.add(command);
        this.updateHelpMsg();
    }

    public void dispatch(final String[] args) {
        final var firstArg = args[0];
        if (firstArg.isEmpty()) {
            return;
        }

        for (final var globCmd : globalCommands) {
            if (globCmd.getName().equals(firstArg) || globCmd.getShortName().equals(firstArg)) {
                globCmd.execute();
                return;
            }
        }

        String cmdName = args.length > 1 ? args[1] : "h";
        final String[] cmdArgs;
        if (args.length > 2) {
            cmdArgs = Arrays.copyOfRange(args, 2, args.length);
        } else {
            cmdArgs = new String[0];
        }
        for (final var module : modules) {
            if (module.getName().equals(firstArg)) {
                final var cmdRes = module.getCommand(cmdName);
                if (cmdRes.isEmpty()) {
                    System.out.printf("No such command for %s: %s\n%s\n", firstArg, cmdName, module.getHelpMessage());
                    return;
                }
                final var cmd = cmdRes.get();
                try {
                    cmd.setArgs(cmdArgs);
                    cmd.execute();
                } catch (final Exception e) {
                    System.out.printf("Execution error: %s\n%s\n", e.getMessage(), cmd.getSignature());
                }
                return;
            }
        }
        System.out.printf("No such module/command: %s\n%s\n", firstArg, getHelp());
    }

    public String getHelp() {
        return this.helpMsgCmd.getMessage();
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
        for (final var cmd : globalCommands) {
            sb.append("\t");
            sb.append(cmd.getSignature());
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);

        this.helpMsgCmd.setMessage(sb.toString());
    }
}
