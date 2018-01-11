package cli;

import app.SQLGen;
import logic.*;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.tuple.Pair;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Client {
    private static void initOptions(Options options) {
        options.addOption("i", "input", true, "input file");
        options.addOption("o", "output", true, "output file");
        options.addOption("c", "canonicalCover", false, "compute canonical cover from given input");
        options.addOption("t", "thirdNF", false, "compute third normal form");
        options.addOption("b", "bcNF", false, "compute Boyce-Codd normal form");
        options.addOption("h", "help", false, "print help");
        options.addOption("s", "script", false, "generate script");

    }

    static InputData readFile(File file) throws IOException {
        Map<String, String> attrs = new HashMap<>();
        List<Pair<List<String>, List<String>>> dep = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            JsonNode root = mapper.readTree(reader);
            JsonNode attributes = root.path("attributes");
            for (JsonNode node : attributes) {
                attrs.put(
                        node.path("name").getTextValue(),
                        node.path("type").getTextValue());
            }

            for (JsonNode node : root.path("funcDep")) {
                List<String> left = new ArrayList<>();
                for (JsonNode jsonNode : node.path("left")) {
                    left.add(jsonNode.getTextValue());
                }

                List<String> right = new ArrayList<>();
                for (JsonNode jsonNode : node.path("right")) {
                    right.add(jsonNode.getTextValue());
                }

                dep.add(Pair.of(left, right));
            }
        }

        return new InputData(
                attrs.entrySet().stream()
                        .map(e -> new Attribute(e.getKey(), e.getValue()))
                        .collect(Collectors.toSet()),
                dep.stream().map(pair -> new FD(
                        pair.getLeft().stream()
                                .map(str -> new Attribute(str, attrs.get(str)))
                                .collect(Collectors.toSet()),
                        pair.getRight().stream()
                                .map(str -> new Attribute(str, attrs.get(str)))
                                .collect(Collectors.toSet())
                )).collect(Collectors.toSet())
        );
    }

    static void writeFile(File file, Decomposition fds) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();

        for (Table table : fds.getTables()) {
            ObjectNode tableObj = mapper.createObjectNode();
            tableObj.put("name", table.hashCode());

            ArrayNode attributesObj = mapper.createArrayNode();
            for (Attribute attribute : table.getAttributes()) {
                ObjectNode attrObj = mapper.createObjectNode();
                attrObj.put("name", attribute.getName());
                attrObj.put("type", attribute.getType());
                attributesObj.add(attrObj);
            }
            tableObj.put("attributes", attributesObj);

            ArrayNode primaryKeyObj = mapper.createArrayNode();
            for (Attribute attribute : table.getPrimaryKey()) {
                primaryKeyObj.add(attribute.getName());
            }
            tableObj.put("primary key", primaryKeyObj);

            ArrayNode fksArray = mapper.createArrayNode();
            for (Map.Entry<Set<Attribute>, Table> fk : table.getForeignKeys().entrySet()) {
                ObjectNode fkObj = mapper.createObjectNode();
                ArrayNode attrArray = mapper.createArrayNode();
                fk.getKey().forEach(a -> attrArray.add(a.getName()));

                fkObj.put("key", attrArray);
                fkObj.put("references", fk.getValue().hashCode());
                fksArray.add(fkObj);
            }

            tableObj.put("foreign keys", fksArray);
            root.add(tableObj);
        }

        mapper.defaultPrettyPrintingWriter().writeValue(file, root);
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

            if (cmd.hasOption("t")) {
                InputData data = readFile(new File(inFile));
                Decomposition thirdNF = Relations.thirdNF(data.getFuncDep());
                writeFile(new File(outFile), thirdNF);
            }
            if (cmd.hasOption("s")) {
                InputData data = readFile(new File(inFile));
                Decomposition thirdNF = Relations.thirdNF(data.getFuncDep());
                String script = SQLGen.createScript(thirdNF);
                try (FileWriter writer = new FileWriter(new File(outFile))) {
                    writer.write(script);
                }
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }
}
