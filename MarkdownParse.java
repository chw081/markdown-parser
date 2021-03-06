//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if (openBracket > -1 && closeBracket > -1 && openParen == closeBracket + 1 && closeParen > -1) {
                //int manyLines = markdown.substring(openBracket, closeBracket).indexOf("\n\n");
                if (openBracket == 0 || !markdown.substring(openBracket -1, openBracket).equals("!")) {
                    if (markdown.substring(openBracket, closeBracket).indexOf("\n\n") == -1) {
                        if (markdown.substring(openParen, closeParen).indexOf("\n\n") == -1) {
                            if (markdown.substring(openParen, closeParen).indexOf(" ") == -1) {
                                //toReturn.add(markdown.substring(openParen + 1, closeParen));
                                String link = markdown.substring(openParen + 1, closeParen);
                                boolean valid = true;
                                while (link.indexOf("\n") > -1) { 
                                    if (link.indexOf("\n") == openParen + 1 || link.indexOf("\n") == closeParen - 1) {
                                        link = link.replace("\n", ""); 
                                    }
                                    else {
                                        valid = false;
                                        currentIndex ++;
                                        break;
                                    }
                                }
                                if (valid) {
                                    toReturn.add(link);
                                    currentIndex = closeParen + 1;
                                }
                            } else { currentIndex ++; }
                        } else { currentIndex ++; }
                    } else { currentIndex ++; }
                } else { currentIndex ++; }
            } else { currentIndex ++;}
        }

        return toReturn;
    }

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}


/*
    int openTicks = 
    if ()
*/