package com.ssu.strangerws.formallang.utils;

import com.ssu.strangerws.formallang.automation.impl.NFA;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {

    private static Set<String> masks;

    public static NFA parseRegex(String regex) {
        int index = 0;
        List<NFA> expressions = new ArrayList<>();
        List<NFA> concat = new ArrayList<>();
        masks = new TreeSet<>();

        if (regex.trim().isEmpty()) {
            return AutomationGenerator.createEmpty();
        }

        cycle:
        while (index < regex.length()) {
            char symbol = regex.charAt(index);

            if (Character.isWhitespace(symbol)) {
                index++;
            } else if (symbol == '\\') {
                index++;
                symbol = regex.charAt(index);
                switch (symbol) {
                    case '\\':
                        concat.add(AutomationGenerator.createSingle("\\"));
                        break;
                    case 's':
                        concat.add(AutomationGenerator.createSingle("\\s"));
                        masks.add("\\s");
                        break;
                    case 'w':
                        concat.add(AutomationGenerator.createSingle("\\w"));
                        masks.add("\\w");
                        break;
                    case 'd':
                        concat.add(AutomationGenerator.createSingle("\\d"));
                        masks.add("\\d");
                        break;
                    case '|':
                        concat.add(AutomationGenerator.createSingle("|"));
                        break;
                    case '?':
                        concat.add(AutomationGenerator.createSingle("\\?"));
                        masks.add("\\?");
                        break;
                    case '*':
                        concat.add(AutomationGenerator.createSingle("*"));
                        break;
                    case '(':
                        concat.add(AutomationGenerator.createSingle("("));
                        break;
                    case ')':
                        concat.add(AutomationGenerator.createSingle(")"));
                        break;
                    case ' ':
                        concat.add(AutomationGenerator.createSingle(" "));
                        break;
                    case 't':
                        concat.add(AutomationGenerator.createSingle("\t"));
                        break;
                    case 'r':
                        concat.add(AutomationGenerator.createSingle("\r"));
                        break;
                    case 'n':
                        concat.add(AutomationGenerator.createSingle("\n"));
                        break;
                    case '.':
                        concat.add(AutomationGenerator.createSingle("\\."));
                        masks.add("\\.");
                        break;
                    default:
                        index++;
                        continue;
                }
                index++;
            } else if (symbol == '|' && !concat.isEmpty()) {
                expressions.add(createConcat(concat));
                index++;
            } else if (symbol == '(') {
                Deque<Character> queue = new ArrayDeque<>();
                queue.push('(');
                int currentIndex = index;
                index++;

                while (!queue.isEmpty()) {
                    if (index >= regex.length()) {
                        index++;
                        continue cycle;
                    }
                    symbol = regex.charAt(index);
                    if (symbol == '(') {
                        queue.push(symbol);
                    } else if (symbol == ')') {
                        queue.pop();
                    } else if (symbol == '\\') {
                        index++;
                    }
                    index++;
                }
                int startIndex = currentIndex + 1;
                int endIndex = index - 1;

                String innerRegex = regex.substring(startIndex, endIndex);
                concat.add(parseRegex(innerRegex));
            } else if (symbol == '*') {
                if (!concat.isEmpty()) {
                    NFA automation = concat.get(concat.size() - 1);
                    concat.remove(concat.size() - 1);
                    concat.add(AutomationGenerator.iter(automation));
                }
                index++;
            } else if (symbol == ')') {
                index++;
            } else {
                concat.add(AutomationGenerator.createSingle(String.valueOf(symbol)));
                index++;
            }
        }

        if (!concat.isEmpty()) {
            expressions.add(createConcat(concat));
        }

        NFA automation = createUnion(expressions);
        automation.setMasks(masks.toArray(new String[masks.size()]));
        return automation;
    }

    private static NFA createConcat(List<NFA> automations) {
        int index = 0;
        NFA result;
        if (automations.isEmpty()) {
            result = AutomationGenerator.createSingle("\\?");
            result.setMasks(new String[]{"\\?"});
            return result;
        } else if (automations.size() == 1) {
            result = automations.get(index);
            automations.clear();
            return result;
        }

        result = automations.get(index);
        index++;
        while (index < automations.size()) {
            NFA automation = automations.get(index);
            result = AutomationGenerator.concat(result, automation);
            index++;
        }
        automations.clear();

        return result;
    }

    private static NFA createUnion(List<NFA> automations) {
        NFA result;
        if (automations.isEmpty()) {
            result = AutomationGenerator.createSingle("\\?");
            result.setMasks(new String[]{"\\?"});
            return result;
        } else if (automations.size() == 1) {
            return automations.get(0);
        }

        result = AutomationGenerator.union(automations.get(0), automations.get(1));
        for (int i = 2; i < automations.size(); i++) {
            result = AutomationGenerator.union(result, automations.get(i));
        }

        return result;
    }

    public static List<String[]> readLexemes(String fileName) throws IOException {
        List<String[]> lexemes = new ArrayList<>();
        Files.lines(Paths.get(fileName)).forEach((str) -> {
            String[] toAdd = new String[3];
            String[] arr = str.split(":");
            toAdd[0] = arr[0].trim();
            toAdd[1] = arr[1].trim();

            StringBuilder regex = new StringBuilder();
            for (int i = 2; i < arr.length; i++) {
                regex.append(arr[i].trim());
                regex.append(":");
            }
            regex.deleteCharAt(regex.length() - 1);

            toAdd[2] = regex.toString();

            lexemes.add(toAdd);
        });

        return lexemes;
    }

    public static NFA[] createAnalyzer(List<String[]> lexemes) {
        NFA[] automations = new NFA[lexemes.size()];

        for (int i = 0; i < lexemes.size(); i++) {
            NFA tmp = parseRegex(lexemes.get(i)[2]);
            tmp.setName(lexemes.get(i)[0]);
            tmp.setFileName("generated");
            tmp.setPriority(Integer.parseInt(lexemes.get(i)[1]));
            tmp.setMasks(masks.toArray(new String[masks.size()]));
            masks = null;
            automations[i] = tmp;
        }

        return automations;
    }
}
