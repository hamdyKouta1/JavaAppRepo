package com.mycompany.guiproject;

import org.kohsuke.github.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GitHubFileUpdater {

    public static void uploadXML(String myXML) {
        String username = "hamdyKouta1";
        String token = "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";
        String repositoryOwner = "hamdyKouta1";
        String repositoryName = "xml";
        String filePath = "callling.xml";
        String newContent = "helllllllooo";
        String newXmlContent = myXML;

        try {
            GitHub github = new GitHubBuilder().withOAuthToken(token).build();

            GHRepository repository = github.getRepository(repositoryOwner + "/" + repositoryName);
            GHContent content = repository.getFileContent(filePath);

            // Read the existing XML content
            InputStream inputStream = content.read();
            String currentXmlContent = convertStreamToString(inputStream);

            // Update the XML content
            String updatedXmlContent = updateXmlContent(currentXmlContent, newXmlContent);

            // Commit the changes
            content.update(updatedXmlContent.getBytes(), "Updating XML file");

            System.out.println("XML file updated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String updateXmlContent(String currentXmlContent, String newXmlContent) {
        // Implement your logic to update the XML content here
        // For simplicity, this example just replaces the existing content with the new content
        return newXmlContent;
    }

    private static String convertStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
