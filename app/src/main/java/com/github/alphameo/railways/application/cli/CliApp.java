package com.github.alphameo.railways.application.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.github.alphameo.railways.application.cli.commands.CliCommand;
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
import com.github.alphameo.railways.application.cli.commands.train.RemoveTrainScheduleEntry;
import com.github.alphameo.railways.application.cli.commands.train.UnregisterTrainCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.AssembleLocomotiveCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.DisassembleTrainCompositionCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.FindTrainCompositionByIdCommand;
import com.github.alphameo.railways.application.cli.commands.traincomposition.ListTrainCompositionsCommand;
import com.github.alphameo.railways.application.services.CarriageService;
import com.github.alphameo.railways.application.services.LineService;
import com.github.alphameo.railways.application.services.LocomotiveService;
import com.github.alphameo.railways.application.services.StationService;
import com.github.alphameo.railways.application.services.TrainCompositionService;
import com.github.alphameo.railways.application.services.TrainService;

import lombok.NonNull;

public class CliApp {

    private final List<CliModule> modules = new ArrayList<>();

    private String helpMsg = "";

    public CliApp(
            CarriageService carServ,
            LineService lineServ,
            LocomotiveService locoServ,
            StationService stationServ,
            TrainService trainServ,
            TrainCompositionService trainCompoServ) {
        final var carrMod = new CliModule("carriage");
        carrMod.addCmd(new RegisterCarriageCommand(carServ));
        carrMod.addCmd(new ListCarriagesCommand(carServ));
        carrMod.addCmd(new FindCarriageByIdCommand(carServ));
        carrMod.addCmd(new FindCarriageByNumberCommand(carServ));
        carrMod.addCmd(new UnregisterCarriageCommand(carServ));

        final var lineMod = new CliModule("line");
        lineMod.addCmd(new DeclareLineCommand(lineServ));
        lineMod.addCmd(new FindLineByIdCommand(lineServ));
        lineMod.addCmd(new ListLinesCommand(lineServ));
        lineMod.addCmd(new DisbandLineCommand(lineServ));
        lineMod.addCmd(new ListLineStationsCommand(lineServ));

        final var locoMod = new CliModule("locomotive");
        locoMod.addCmd(new RegisterLocomotiveCommand(locoServ));
        locoMod.addCmd(new ListLocomotivesCommand(locoServ));
        locoMod.addCmd(new FindLocomotiveByIdCommand(locoServ));
        locoMod.addCmd(new FindLocomotiveByNumberCommand(locoServ));
        locoMod.addCmd(new UnregisterLocomotiveCommand(locoServ));

        final var stationMod = new CliModule("station");
        stationMod.addCmd(new RegisterStationCommand(stationServ));
        stationMod.addCmd(new ListStationCommand(stationServ));
        stationMod.addCmd(new FindStationByIdCommand(stationServ));
        stationMod.addCmd(new UnregisterStationCommand(stationServ));

        final var trainMod = new CliModule("train");
        trainMod.addCmd(new RegisterTrainCommand(trainServ));
        trainMod.addCmd(new ListTrainsCommand(trainServ));
        trainMod.addCmd(new FindTrainByIdCommand(trainServ));
        trainMod.addCmd(new FindTrainByNumberCommand(trainServ));
        trainMod.addCmd(new UnregisterTrainCommand(trainServ));
        trainMod.addCmd(new InsertTrainScheduleEntryCommand(trainServ));
        trainMod.addCmd(new RemoveTrainScheduleEntry(trainServ));

        final var trainCompoMod = new CliModule("traincomposition");
        trainCompoMod.addCmd(new AssembleLocomotiveCommand(trainCompoServ));
        trainCompoMod.addCmd(new ListTrainCompositionsCommand(trainCompoServ));
        trainCompoMod.addCmd(new FindTrainCompositionByIdCommand(trainCompoServ));
        trainCompoMod.addCmd(new DisassembleTrainCompositionCommand(trainCompoServ));

        this.addModule(carrMod);
        this.addModule(lineMod);
        this.addModule(locoMod);
        this.addModule(stationMod);
        this.addModule(trainMod);
        this.addModule(trainCompoMod);
    }

    public void addModule(@NonNull final CliModule module) {
        this.modules.add(module);
        this.updateHelp();
    }

    public void run() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(Renderer.PROMPT);
                final var inp = scanner.nextLine();
                final String[] args = inp.split(" ");
                final var modName = args[0];
                if (modName.equals("help") || modName.equals("h")) {
                    System.out.print(getHelp());
                    continue;
                }
                if (modName.equals("quit") || modName.equals("q")) {
                    break;
                }
                if (modName.equals("")) {
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
                    if (module.getName().equals(modName)) {
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
                    System.out.printf("No such module: %s\n%s\n", modName, getHelp());
                }
            } catch (final Exception e) {
                System.out.println("Error: " + e.getCause());
            }
        }
        scanner.close();
    }

    public String getHelp() {
        return this.helpMsg;
    }

    public void updateHelp() {

        final var sb = new StringBuilder();

        sb.append("Available modules:\n");

        for (final var module : modules) {
            sb.append("\t");
            sb.append(module.getName());
            sb.append("\n");
        }
        this.helpMsg = sb.toString();
    }
}
