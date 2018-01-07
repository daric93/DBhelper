package cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import logic.Decomposition;
import logic.FD;
import logic.Relations;
import logic.Table;
import org.apache.commons.cli.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Client {
    private static void initOptions(Options options) {
        options.addOption("i", "input", true, "input file");
        options.addOption("o", "output", true, "output file");
        options.addOption("c", "canonicalCover", false, "compute canonical cover from given input");
        options.addOption("t", "thirdNF", false, "compute third normal form");
        options.addOption("b", "bcNF", false, "compute Boyce-Codd normal form");
        options.addOption("h", "help", false, "print help");
    }

    static Set<FD> readFile(File file) throws IOException {
        Set<FD> fds = new HashSet<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] strings = line.split("-");
                String[] left = strings[0].trim().split("\\s*,\\s*");
                String[] right = strings[1].trim().split("\\s*,\\s*");
                fds.add(new FD(newHashSet(left), newHashSet(right)));
            }
        }
        return fds;
    }

    @SuppressWarnings("unchecked")
    static void writeFile(File file, Set<FD> fds) throws IOException {
        Type type = new TypeToken<Set<FD>>() {
        }.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(fds, type));
            writer.flush();
        }
    }
    static void writeFile(File file, Decomposition fds) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(gson.toJson(fds, Decomposition.class));
            writer.flush();
        }
    }

    static void parseInput(String input) {


    }

    public static void main(String[] args) throws IOException {
        String inFile;
        String outFile;
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        initOptions(options);
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("help")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("help", options);
                return;
            }
            if (cmd.hasOption("i")) {
                inFile = cmd.getOptionValue("input");
            } else {
                throw new MissingArgumentException("Missed input file");
            }
            if (cmd.hasOption("o")) {
                outFile = cmd.getOptionValue("output");
            } else {
                throw new MissingArgumentException("Missed output file");
            }

            if (cmd.hasOption("c")) {
                Set<FD> fdSet = readFile(new File(inFile));
                Set<FD> canonicalCover = Relations.canonicalCover(fdSet);
                writeFile(new File(outFile), canonicalCover);
            }
            if (cmd.hasOption("t")) {
                Set<FD> fdSet = readFile(new File(inFile));
                Decomposition thirdNF = Relations.thirdNF(fdSet);
                writeFile(new File(outFile), thirdNF);
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }
}
